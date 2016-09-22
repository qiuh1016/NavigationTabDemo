package com.cetcme.rcldandroidZhejiang;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.cetcme.rcldandroidZhejiang.MyClass.CustomDialog;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;

/**
 * Created by qiuhong on 5/26/16.
 */
public class UpdateAppManager {

    private int currentVersionCode;
    private int serverVersionCode;
    private String serverVersionName;
    private String upgradeTime;
    private String newVersionRemark;

    private KProgressHUD kProgressHUD;

    private String TAG = "UpdateAppManager";

    // 文件分隔符
    private static final String FILE_SEPARATOR = "/";
    // 外存sdcard存放路径
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + FILE_SEPARATOR +"zytyumin" + FILE_SEPARATOR;
    // 下载应用存放全路径
    private static  String FILE_NAME;
    // 更新应用版本标记
    private static final int UPDATE_TOKEN = 0x29;
    // 准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 0x31;

    private Context context;

    // 服务器路径
    private String UPDATE_SERVER_ADDRESS;
    // 下载路径
    private String spec;
    // 版本路径
    private String versionUrl;
    // 下载应用的对话框
    private Dialog dialog;
    // 下载应用的进度条
//    private ProgressBar progressBar;
    private RoundCornerProgressBar progressBar;
    private TextView progressTextView;
    // 进度条的当前刻度值
    private int curProgress;
    // 用户是否取消下载
    private boolean isCancel;
    // 强制更新
    private boolean forceToUpdate;
    // 是否手动检测更新
    private boolean manualCheckUpdate;

    public UpdateAppManager(Context context, boolean manualCheckUpdate) {
        this.context = context;

        SharedPreferences user = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        UPDATE_SERVER_ADDRESS = context.getString(R.string.rcldServerIP);

        //kProgressHUD
        kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("检测中")
                .setAnimationSpeed(1)
                .setDimAmount(0.3f)
                .setSize(110, 110)
                .setCancellable(false);

        // 下载路径
        spec = UPDATE_SERVER_ADDRESS + context.getString(R.string.getDownLoadUrl);

        // 版本路径
        versionUrl = UPDATE_SERVER_ADDRESS + context.getString(R.string.appVersionUrl);

        SharedPreferences system = context.getSharedPreferences("system", Context.MODE_PRIVATE);
        String versionString = system.getString("version","");
        Log.i("Main","currentVersionCode: " + versionString );

        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            currentVersionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            currentVersionCode = 0;
        }

        this.manualCheckUpdate = manualCheckUpdate;

        if (manualCheckUpdate) {
            kProgressHUD.show();
        }


    }

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TOKEN:
                    progressBar.setProgress(curProgress);
                    progressTextView.setText(curProgress + "/100");
                    break;

                case INSTALL_TOKEN:
                    installApp();
                    break;
            }
        }
    };

    /**
     * 检测应用更新信息
     */
    public void checkUpdateInfo() {

        RequestParams params = new RequestParams();
        params.put("code", 1); // 1: 渔民app  2: 安装app

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(versionUrl, params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                kProgressHUD.dismiss();

                Log.i("Main", "versionJSON: " + response.toString());
                try {

                    int code = response.getInt("code");

                    if (code == 0) {
//                        JSONObject apkVersion = response.getJSONObject("ApkVersion");
                        serverVersionCode = response.getInt("verCode");
                        serverVersionName = response.getString("version");
                        upgradeTime = response.getString("date");
//                        upgradeTime = getFormatTime(upgradeTime);
                        newVersionRemark = response.getString("content").replace("\\n", "\n");
                        forceToUpdate = response.getBoolean("force_update");

                        //保存服务器版本 和 版本说明
                        SharedPreferences system = context.getSharedPreferences("system", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = system.edit();

                        if (serverVersionCode > currentVersionCode) {
                            FILE_NAME = FILE_PATH + "zytyumin_V" + serverVersionName +".apk";
                            showNoticeDialog();
                            editor.putInt("serverVersionCode", serverVersionCode);
                            editor.putString("newVersionRemark", newVersionRemark);
                        } else if (serverVersionCode == currentVersionCode) {
                            //手动检测更新
                            if (manualCheckUpdate) {
                                showNoUpdateDialog();
                            }
                        }

                        editor.apply();

                    } else {
                        Log.i(TAG, "onSuccess: flag false");
                    }

                } catch (JSONException e) {
                    Log.i(TAG, "onSuccess: json error");
                    if (manualCheckUpdate) {
                        showNoUpdateDialog();
                    }
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "onFailure: network error");
                kProgressHUD.dismiss();
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "onFailure: network error");
                kProgressHUD.dismiss();
            }
        });

    }

    /**
     * 显示提示更新对话框
     */
    private void showNoticeDialog() {
        String message;
        if (!forceToUpdate) {
            message = "检测到新版本发布(v"+ serverVersionName + ", 发布时间: " + upgradeTime + ")，建议您更新!";
        } else {
            message = "检测到新版本发布(v"+ serverVersionName + ", 发布时间: " + upgradeTime + ")，请您更新！";
        }

        message += "\n\n" + "更新内容:\n"+ newVersionRemark;

        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle("软件版本更新");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });
        if (!forceToUpdate) {
            builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(TAG, "onClick: left");
                    dialog.dismiss();
                }
            });
        }
        builder.create().show();

//
//        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
//        builder.setTitle("软件版本更新")
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton("下载", new OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface custom_dialog, int which) {
//                        custom_dialog.dismiss();
//                        showDownloadDialog();
//                    }
//                });
//        if (!forceToUpdate) {
//            builder.setNegativeButton("以后再说", null);
//        }
//        builder.create().show();
    }

    /**
     * 显示无更新对话框
     */
    private void showNoUpdateDialog() {
//        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
//        builder.setTitle("当前已为最新版本")
//                .setMessage("当前版本：V" + currentVersionCode + "。")
//                .setCancelable(false)
//                .setPositiveButton("好的", null);
//        builder.create().show();

        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("当前已为最新版本, 无需更新。");
        builder.setCancelable(false);
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * 显示下载进度对话框
     */
    private void showDownloadDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.progress_bar, null);
//        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar = (RoundCornerProgressBar) view.findViewById(R.id.progressBar);
        progressTextView = (TextView) view.findViewById(R.id.progressTextView);

        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setTitle("新版本下载中");
        builder.setContentView(view);
        builder.setCancelable(false);
        if (!forceToUpdate) {
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    isCancel = true;
                }
            });
        }


        dialog = builder.create();
        dialog.show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("新版本下载中");
//        builder.setView(view);
//        builder.setCancelable(false);
//        if (!forceToUpdate) {
//            builder.setNegativeButton("取消", new OnClickListener() {
//                @Override
//                public void onClick(DialogInterface custom_dialog, int which) {
//                    isCancel = true;
//                }
//            });
//        }
//        custom_dialog = builder.create();
//        custom_dialog.show();



        downloadApp();

    }

    /**
     * 下载新版本应用
     */
    private void downloadApp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                InputStream in = null;
                FileOutputStream out = null;
                HttpURLConnection conn = null;
                try {
                    url = new URL(spec);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    long fileLength = conn.getContentLength();
                    in = conn.getInputStream();
                    File filePath = new File(FILE_PATH);
                    if(!filePath.exists()) {
                        filePath.mkdir();
                    }
                    out = new FileOutputStream(new File(FILE_NAME));
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    long readedLength = 0L;
                    while((len = in.read(buffer)) != -1) {
                        // 用户点击“取消”按钮，下载中断
                        if(isCancel) {
                            break;
                        }
                        out.write(buffer, 0, len);
                        readedLength += len;
                        curProgress = (int) (((float) readedLength / fileLength) * 100);
                        handler.sendEmptyMessage(UPDATE_TOKEN);
                        if(readedLength >= fileLength) {
                            dialog.dismiss();
                            // 下载完毕，通知安装
                            handler.sendEmptyMessage(INSTALL_TOKEN);
                            break;
                        }
                    }
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 安装新版本应用
     */
    private void installApp() {
        File appFile = new File(FILE_NAME);
        if(!appFile.exists()) {
            return;
        }
        // 跳转到新版本应用安装页面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + appFile.toString()), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private String getFormatTime(String unFormatTime) {
        String time = unFormatTime.substring(6 ,unFormatTime.length() - 2);
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
