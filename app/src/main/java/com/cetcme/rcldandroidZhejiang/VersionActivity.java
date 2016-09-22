package com.cetcme.rcldandroidZhejiang;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cetcme.rcldandroidZhejiang.MyClass.CustomDialog;
import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.umeng.analytics.MobclickAgent;

public class VersionActivity extends Activity {

    private NavigationView navigationView;
    private LinearLayout checkUpdateLine;
    private TextView versionTextView;
    private TextView showUpdateTextView;
    private TextView versionInfoTextView;

    private String versionRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        initNavigationView();
        initUI();
        initLineClick();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_version_activity);
        navigationView.setTitle("版本信息");
        navigationView.setBackView(R.drawable.icon_back_button);
        navigationView.setRightView(0);
        navigationView.setClickCallback(new NavigationView.ClickCallback() {

            @Override
            public void onRightClick() {
                Log.i("main","点击了右侧按钮!");
            }

            @Override
            public void onBackClick() {
                Log.i("main","点击了左侧按钮!");
                onBackPressed();
            }
        });
    }

    private String getCurrentVersion() {
        //Display the current version number
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(getApplicationContext().getPackageName(), 0);
//            //保存
//            SharedPreferences system = getSharedPreferences("system", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = system.edit();
//            editor.putString("version", pi.versionName);
//            editor.apply();
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }

    private int getCurrentVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(getApplicationContext().getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void initUI() {
        checkUpdateLine = (LinearLayout) findViewById(R.id.check_update_line_in_version_activity);
        versionTextView = (TextView) findViewById(R.id.version_textView_in_version_activity);
        showUpdateTextView = (TextView) findViewById(R.id.show_update_textView_in_version_activity);
        versionInfoTextView = (TextView) findViewById(R.id.version_info_textView_in_version_activity);
        versionInfoTextView.setVisibility(View.INVISIBLE);

        /**
         * 显示版本号
         */
        versionTextView.setText("版本号：" + getCurrentVersion());


        /**
         * 显示是否有可用更新
         */
        SharedPreferences system = getSharedPreferences("system", Context.MODE_PRIVATE);
        int serverVersionCode = system.getInt("serverVersionCode", 0);
        int currentVersionCode = getCurrentVersionCode();
        if (currentVersionCode < serverVersionCode) {
            showUpdateTextView.setText("有可用更新");
        } else {
            showUpdateTextView.setText("已是最新版");
        }

        /**
         * 版本说明的内容
         */
        versionRemark = getString(R.string.originalVersionRemark);

    }

    private void initLineClick() {
        findViewById(R.id.check_update_line_in_version_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将手动检测flag设置为true
                SharedPreferences system = getSharedPreferences("system", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = system.edit();
                editor.putBoolean("manualCheckUpdate", true);
                editor.apply();

                UpdateAppManager updateManager;
                updateManager = new UpdateAppManager(VersionActivity.this, true);
                updateManager.checkUpdateInfo();
            }
        });

        findViewById(R.id.version_remark_line_in_version_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.Builder builder = new CustomDialog.Builder(VersionActivity.this);
                builder.setTitle("版本说明");
                builder.setMessage(versionRemark);
                builder.setCancelable(false);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }
}
