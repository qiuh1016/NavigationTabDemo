package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.NavigationView;
import com.fr.android.activity.LoadAppFromURLActivity;

public class ServiceActivity extends Activity {

    private String TAG = "ServiceActivity";

    private String url_line_1 = "http://61.164.218.155:5008/WebReport/ReportServer?reportlet=apply%2Fchuanjian_1.cpt&op=write&app_account=";
    private String url_line_2 = "http://61.164.218.155:5008/WebReport/ReportServer?reportlet=apply%2Fexamine_apply_phone.cpt&op=write&user=";
    private String url_line_3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        initNavigationView();
        initURL();
        initLineClick();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_service_activity);
        navigationView.setTitle(getString(R.string.gird_1_in_fragment_homepage));
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

    private void initURL() {
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = user.getString("username","");
        url_line_1 += username;
        url_line_2 += username;
        url_line_3 += username;
    }

    private void initLineClick() {
        findViewById(R.id.line_1_in_service_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 1");
                Intent intent = new Intent();
                intent.putExtra("url", url_line_1);
                intent.putExtra("title", getString(R.string.line_1_in_service_activity));
                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.line_2_in_service_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 2");
                Intent intent = new Intent();
                intent.putExtra("url", url_line_2);
                intent.putExtra("title", getString(R.string.line_2_in_service_activity));
                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.line_3_in_service_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 3");
//                Intent intent = new Intent();
//                intent.putExtra("url", url_line_3);
//                intent.putExtra("title", getString(R.string.line_3_in_service_activity));
//                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "即将上线", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
