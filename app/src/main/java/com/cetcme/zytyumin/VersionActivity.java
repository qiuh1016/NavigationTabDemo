package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import MyClass.NavigationView;

public class VersionActivity extends Activity {

    private NavigationView navigationView;
    private LinearLayout checkUpdateLine;
    private TextView versionTextView;
    private TextView showUpdateTextView;
    private TextView versionInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        initNavigationView();
        initUI();
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
            //保存
            SharedPreferences system = getSharedPreferences("system", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = system.edit();
            editor.putString("version", pi.versionName);
            editor.apply();
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }

    private void initUI() {
        checkUpdateLine = (LinearLayout) findViewById(R.id.check_update_line_in_version_activity);
        versionTextView = (TextView) findViewById(R.id.version_textView_in_version_activity);
        showUpdateTextView = (TextView) findViewById(R.id.show_update_textView_in_version_activity);
        versionInfoTextView = (TextView) findViewById(R.id.version_info_textView_in_version_activity);

        /**
         * 显示版本号
         */
        versionTextView.setText("版本号：" + getCurrentVersion());
    }
}
