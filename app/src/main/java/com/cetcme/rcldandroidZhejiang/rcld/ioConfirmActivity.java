package com.cetcme.rcldandroidZhejiang.rcld;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.rcldandroidZhejiang.MyClass.CustomDialog;
import com.cetcme.rcldandroidZhejiang.MyClass.DensityUtil;
import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import static android.widget.Toast.LENGTH_SHORT;

public class ioConfirmActivity extends Activity {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> dataList = new LinkedList<>();
    private Toast toast;
    private KProgressHUD kProgressHUD;
    private int iofFlag;
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<Integer> uploadOKList;

    private Boolean showBackDialog = true;

    private String shipNo = "";

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io_confirm);

//        /**
//         * 导航栏返回按钮
//         */
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        /**
         * umeng 推送
         */
        PushAgent.getInstance(this).onAppStart();

        Bundle bundle = this.getIntent().getExtras();
        shipNo = bundle.getString("shipNo");
        iofFlag = bundle.getInt("iofFlag");
        if (iofFlag == 1) {
            title = "出港确认";
        } else if (iofFlag == 2) {
            title = "进港确认";
        }

        initNavigationView();
        initListView();

        toast = Toast.makeText(ioConfirmActivity.this, "", LENGTH_SHORT);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.punchListView);
        simpleAdapter = new SimpleAdapter(this, getPunchData(), R.layout.punch_list_cell,
                new String[]{"name", "id", "punchTime", "dataTypeString"},
                new int[]{
                        R.id.nameTextViewInPunchListCell,
                        R.id.idTextViewInPunchListCell,
                        R.id.punchTimeTextViewInPunchListView,
                        R.id.dataTypeTextViewInPunchListView
                });
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog(dataList.get(position), position);
            }
        });
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_io_confirm_activity);
        navigationView.setTitle(title);
        navigationView.setBackView(R.drawable.icon_back_button);
        navigationView.setRightView(0);

        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) navigationView.getRightView().getLayoutParams();
        navigationView.removeView(navigationView.getRightView());

        TextView textView_confirm = new TextView(this);
        textView_confirm.setText("确认");
        textView_confirm.setTextColor(Color.WHITE);
        textView_confirm.setTextSize(14);
        textView_confirm.setGravity(Gravity.CENTER);
        textView_confirm.setLayoutParams(params1);
        textView_confirm.setId(R.id.textView_confirm_in_io_confirm_activity);
        textView_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("main", "onClick: confirm");
                confirm();
            }
        });

        TextView textView_add = new TextView(this);
        textView_add.setText("添加");
        textView_add.setTextColor(Color.WHITE);
        textView_add.setTextSize(14);
        textView_add.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(DensityUtil.dip2px(this, 50), DensityUtil.dip2px(this, 50));
        params2.addRule(RelativeLayout.ALIGN_RIGHT, R.id.textView_confirm_in_io_confirm_activity);
        params2.setMargins(0,0,DensityUtil.dip2px(this, 50),0);
        textView_add.setLayoutParams(params2);
        textView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("main", "onClick: add");
                add();
            }
        });

        navigationView.addView(textView_confirm);
        navigationView.addView(textView_add);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem add = menu.add(0, 0, 0, "添加");
        MenuItem confirm = menu.add(0, 0, 0, "确认");

        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        confirm.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("ids", ids);
                Intent addIntent = new Intent();
                addIntent.setClass(getApplicationContext(), ReasonActivity.class);
                addIntent.putExtras(bundle);
                startActivity(addIntent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                return false;
            }
        });

        confirm.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (dataList.isEmpty()) {
                    return false;
                }

                String punchInfo = "";
                for (Map<String, Object> map: dataList) {
                    punchInfo += "姓    名：" + map.get("name") + ",\n身份证：" + map.get("id") + ";\n";
                }

                punchInfo = punchInfo.substring(0,punchInfo.length() - 2);
                punchInfo += ".";


                CustomDialog.Builder builder = new CustomDialog.Builder(ioConfirmActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认上传？");
                builder.setCancelable(false);
                builder.setPositiveButton("上传", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        upload();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(ioConfirmActivity.this);
//                //builder.setMessage(punchInfo);
//                builder.setTitle("确认上传？"); //("共" + dataList.size() + "人,确认上传?");
//                builder.setNegativeButton("取消", null);
//                builder.setPositiveButton("上传", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        upload();
//                    }
//                });
//                builder.create().show();
                return false;
            }
        });

        return true;
    }

    private void add() {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("ids", ids);
        Intent addIntent = new Intent();
        addIntent.setClass(getApplicationContext(), ReasonActivity.class);
        addIntent.putExtras(bundle);
        startActivity(addIntent);
        overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
    }

    private void confirm() {
        if (dataList.isEmpty()) {
            return;
        }

        String punchInfo = "";
        for (Map<String, Object> map: dataList) {
            punchInfo += "姓    名：" + map.get("name") + ",\n身份证：" + map.get("id") + ";\n";
        }

        punchInfo = punchInfo.substring(0,punchInfo.length() - 2);
        punchInfo += ".";


        CustomDialog.Builder builder = new CustomDialog.Builder(ioConfirmActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确认上传？");
        builder.setCancelable(false);
        builder.setPositiveButton("上传", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                upload();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();


//        AlertDialog.Builder builder = new AlertDialog.Builder(ioConfirmActivity.this);
//        //builder.setMessage(punchInfo);
//        builder.setTitle("确认上传？"); //("共" + dataList.size() + "人,确认上传?");
//        builder.setNegativeButton("取消", null);
//        builder.setPositiveButton("上传", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                upload();
//            }
//        });
//        /**
//         * 设置自定义按钮
//         */
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//        Button btnPositive = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
//        Button btnNegative = alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
//        btnNegative.setTextColor(getResources().getColor(R.color.main_color));
//        btnPositive.setTextColor(getResources().getColor(R.color.main_color));
    }

    public void onBackPressed() {

        if (dataList.size() == 0) {
            showBackDialog = false;
        }

        if (showBackDialog) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(ioConfirmActivity.this);
//            builder.setIcon(android.R.drawable.ic_delete);
//            builder.setTitle("返回将丢失现有操作");
//            builder.setMessage("是否继续？");
//            builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    showBackDialog = false;
//                    onBackPressed();
//                }
//            });
//            builder.setNegativeButton("取消",null);
//            /**
//             * 设置自定义按钮
//             */
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//
//            Button btnPositive = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
//            Button btnNegative = alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
//            btnNegative.setTextColor(getResources().getColor(R.color.main_color));
//            btnPositive.setTextColor(getResources().getColor(R.color.main_color));


            CustomDialog.Builder builder = new CustomDialog.Builder(ioConfirmActivity.this);
            builder.setTitle("提示");
            builder.setMessage("返回将丢失现有操作，是否继续?");
            builder.setCancelable(false);
            builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    showBackDialog = false;
                    onBackPressed();
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();


        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.push_right_in_no_alpha,
                    R.anim.push_right_out_no_alpha);
        }

    }

    @Override
    public void onResume(){
        super.onResume();

        //uMeng
        MobclickAgent.onResume(this);

        //更新adapter 添加之后 toAdd 为true
        String name, id, reason;
        SharedPreferences punchToAdd = getSharedPreferences("punchToAdd", Context.MODE_PRIVATE);
        Boolean toAdd = punchToAdd.getBoolean("toAdd", false);
        if (toAdd) {
            name = punchToAdd.getString("name", "");
            id = punchToAdd.getString("id", "");
            reason = punchToAdd.getString("reason", "");

            Map<String, Object> map = new Hashtable<>();
            map.put("iofFlag",iofFlag);
            map.put("id", id);
            map.put("name", name);
            map.put("dataType", 1);
            map.put("dataTypeString", "手动添加");
            map.put("punchTime", "");
            map.put("reason", reason);
            dataList.add(map);

            SharedPreferences.Editor editor = punchToAdd.edit();
            editor.putBoolean("toAdd",false);
            editor.apply();

            ids.add(id);

            simpleAdapter.notifyDataSetChanged();
        }

    }

    private List<Map<String, Object>> getPunchData() {

        kProgressHUD = KProgressHUD.create(ioConfirmActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("获取中")
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.3f)
                .setSize(110, 110)
                .show();

        //获取保存的用户名和密码
        String username,password;
        SharedPreferences user = getSharedPreferences("user", Activity.MODE_PRIVATE);
        username = user.getString("username","");
        password = user.getString("password","");

        dataList = new ArrayList<>();

        //设置时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd%20HH:mm:ss");
        Date endDate = new Date();
        String endTime = df.format(endDate);
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(endDate);//设置当前日期
        //
        calendar.add(Calendar.HOUR, -5);//小时减5
        String startTime = df.format(calendar.getTime());//输出格式化的日期

        //设置输入参数
        RequestParams params = new RequestParams();
        params.put("userName", "jkxx");
        params.put("password", "xMpCOKC5I4INzFCab3WEmw==");
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("shipNo", shipNo);
        params.put("jkxxUser", username);

        Log.i("main", "getPunchData: "+ params.toString());

        String urlBody = getString(R.string.rcldServerIP)+ getString(R.string.punchGetUrl);
//        String url = urlBody+"?userName="+username+"&password="+password+"&shipNo="+shipNo+"&startTime="+startTime+"&endTime="+endTime;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlBody, params, new JsonHttpResponseHandler("UTF-8"){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i("Main",response.toString());
                try {
                    String msg = response.getString("msg");
                    if (msg.equals("成功")) {

                        JSONArray dataArray = response.getJSONArray("data");

                        for(int i = 0; i < dataArray.length(); i++) {
                            JSONObject punch = (JSONObject) dataArray.get(i);

                            Map<String, Object> map = new Hashtable<>();
                            map.put("iofFlag",iofFlag);
                            map.put("id", punch.getString("sailorIdNo"));
                            map.put("name", punch.getString("sailorName"));
                            map.put("dataType", 0);
                            map.put("dataTypeString", "自动生成");
                            map.put("punchTime", punch.getString("punchTime"));
                            map.put("reason", "");
                            dataList.add(map);
                            //id检测是否重复用
                            ids.add(punch.getString("sailorIdNo"));
                        }
                        simpleAdapter.notifyDataSetChanged();
                    } else {
                        toast.setText(msg);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toast.setText("数据解析失败");
                    toast.show();
                }
                kProgressHUD.dismiss();

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                kProgressHUD.dismiss();
                toast.setText("网络连接失败");
                toast.show();
            }
        });

        return dataList;


//        Map<String, Object> map = new Hashtable<>();
//        map.put("iofFlag",iofFlag);
//        map.put("id", "sailorIdNo");
//        map.put("name", "sailorName");
//        map.put("dataType", 0);
//        map.put("dataTypeString", "自动生成");
//        map.put("punchTime", "punchTime");
//        map.put("reason", "");
//        dataList.add(map);
//        dataList.add(map);
//        dataList.add(map);
//        return dataList;
    }

    private void dialog(final Map<String,Object> map, final int position) {
        CustomDialog.Builder builder = new CustomDialog.Builder(ioConfirmActivity.this);
        builder.setTitle("人员信息");
        builder.setMessage( "姓名：" + map.get("name") + "\n" +
                "身份证：" + map.get("id") + "\n" +
                "出入港标志：" + map.get("iofFlag") + "\n" +
                "数据类型：" + map.get("dataTypeString") + "\n" +
                "打卡时间：" + map.get("punchTime") + "\n"+
                "原因：" + map.get("reason"));
        builder.setCancelable(false);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                int dataType = (int) map.get("dataType");

                //dataType 0: 自动生成 1: 手动添加 2: 手动删除
                if (dataType == 1) {
                    dataList.remove(position);
                    ids.remove(position);
                } else if (dataType == 0) {

                    final EditText et = new EditText(ioConfirmActivity.this);

                    new AlertDialog.Builder(ioConfirmActivity.this).setTitle("删除原因")
                            .setIcon(android.R.drawable.ic_menu_info_details)
                            .setView(et)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String input = et.getText().toString();
                                    if (input.equals("")) {
                                        Toast.makeText(getApplicationContext(), "删除原因不能为空", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        //操作
                                        dataList.get(position).put("reason", input);
                                        dataList.get(position).put("dataType", 2);
                                        dataList.get(position).put("dataTypeString", "手动删除");
                                        simpleAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();



                } else if (dataType == 2) {
                    dataList.get(position).put("reason", "");
                    dataList.get(position).put("dataType", 0);
                    dataList.get(position).put("dataTypeString", "自动生成");
                }

                simpleAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(ioConfirmActivity.this);
//        builder.setMessage(
//                "姓名：" + map.get("name") + "\n" +
//                "身份证：" + map.get("id") + "\n" +
//                "出入港标志：" + map.get("iofFlag") + "\n" +
//                "数据类型：" + map.get("dataTypeString") + "\n" +
//                "打卡时间：" + map.get("punchTime") + "\n"+
//                "原因：" + map.get("reason")
//        );
//        builder.setTitle("人员信息");
//        builder.setPositiveButton("取消", null);
//        //删除
//        builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Log.i("Main","删除");
//
//                int dataType = (int) map.get("dataType");
//
//                //dataType 0: 自动生成 1: 手动添加 2: 手动删除
//                if (dataType == 1) {
//                    dataList.remove(position);
//                    ids.remove(position);
//                } else if (dataType == 0) {
//
//                    final EditText et = new EditText(ioConfirmActivity.this);
//
//                    new AlertDialog.Builder(ioConfirmActivity.this).setTitle("删除原因")
//                            .setIcon(android.R.drawable.ic_menu_info_details)
//                            .setView(et)
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String input = et.getText().toString();
//                                    if (input.equals("")) {
//                                        Toast.makeText(getApplicationContext(), "删除原因不能为空", Toast.LENGTH_SHORT).show();
//                                    }
//                                    else {
//                                        //操作
//                                        dataList.get(position).put("reason", input);
//                                        dataList.get(position).put("dataType", 2);
//                                        dataList.get(position).put("dataTypeString", "手动删除");
//                                        simpleAdapter.notifyDataSetChanged();
//                                    }
//                                }
//                            })
//                            .setNegativeButton("取消", null)
//                            .show();
//
//
//
//                } else if (dataType == 2) {
//                    dataList.get(position).put("reason", "");
//                    dataList.get(position).put("dataType", 0);
//                    dataList.get(position).put("dataTypeString", "自动生成");
//                }
//
//                simpleAdapter.notifyDataSetChanged();
//
//            }
//        });
//        /**
//         * 设置自定义按钮
//         */
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//        Button btnPositive = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
//        Button btnNegative = alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
//        btnNegative.setTextColor(getResources().getColor(R.color.main_color));
//        btnPositive.setTextColor(getResources().getColor(R.color.main_color));
    }

    private void upload() {

        kProgressHUD = KProgressHUD.create(ioConfirmActivity.this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("上传中")
            .setAnimationSpeed(1)
            .setDimAmount(0.3f)
            .setSize(110, 110)
            .setCancellable(false)
            .show();

        uploadOKList = new ArrayList<>();
        Log.i("Main" , "一共" + dataList.size() + "个数据");

        JSONArray sailors = new JSONArray();
        try {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject sailor = new JSONObject();

                sailor.put("sailorIdNo", dataList.get(i).get("id"));
                sailor.put("sailorName", dataList.get(i).get("name"));

                int dataType = (int) dataList.get(i).get("dataType");
                if (dataType != 1) {
                    sailor.put("punchTime", dataList.get(i).get("punchTime"));
                }

                sailor.put("dataType", dataType);

                String reason = (String) dataList.get(i).get("reason");
                if (!reason.isEmpty()) {
                    sailor.put("reason", dataList.get(i).get("reason"));
                }

                sailors.put(sailor);
            }
            Log.i("Main", sailors.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //获取保存的用户名和密码
        String username,password;
        SharedPreferences user = getSharedPreferences("user", Activity.MODE_PRIVATE);
        username = user.getString("username","");
        password = user.getString("password","");

        //设置输入参数
        RequestParams params = new RequestParams();
        params.put("userName", "jkxx");
        params.put("password", "xMpCOKC5I4INzFCab3WEmw==");
        params.put("shipNo", shipNo);
        params.put("iofFlag", iofFlag);
        params.put("sailors", sailors);
        params.put("jkxxUser", username);

        String urlBody = getString(R.string.rcldServerIP)+ getString(R.string.sailorNewUrl);
//        String url = urlBody+"?userName="+shipNumber+"&password="+password+"&startTime="+startTime+"&endTime="+endTime;
        AsyncHttpClient client = new AsyncHttpClient();
        client.setURLEncodingEnabled(true);
        client.post(urlBody, params, new JsonHttpResponseHandler("UTF-8"){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i("Main",response.toString());
                try {
                    int code = response.getInt("code");
                    if (code == 0) {
                        toast.setText("上传成功");
                        toast.show();
                        dataList.clear();
                        ids.clear();
                        simpleAdapter.notifyDataSetChanged();
                    } else {
                        String msg = response.getString("msg");
                        toast.setText(msg);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Main", "error");
                    toast.setText("上传失败");
                    toast.show();
                }

                kProgressHUD.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                kProgressHUD.dismiss();
                toast.setText("网络连接失败");
                toast.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                Log.i("Main", response);
                kProgressHUD.dismiss();
                toast.setText("网络连接失败");
                toast.show();
            }


        });

    }

    public void onDestroy() {
        super.onDestroy();
        toast.cancel();
    }

}
