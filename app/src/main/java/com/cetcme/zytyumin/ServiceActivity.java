package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cetcme.zytyumin.MyClass.NavigationView;
import com.fr.android.activity.LoadAppFromURLActivity;

public class ServiceActivity extends Activity {

    private String TAG = "ServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initNavigationView();
        initLineClick();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_service_activity);
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

    private void initLineClick() {
        findViewById(R.id.line_1_in_service_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 1");
                String url = "http://61.164.218.155:5008/WebReport/ReportServer?reportlet=apply%2Fchuanjian_1.cpt&op=write&app_account=guy";
                Intent intent = new Intent();
                intent.putExtra("url", url);
                intent.putExtra("title", "My Title1");
                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.line_2_in_service_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 2");
            }
        });

        findViewById(R.id.line_3_in_service_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 3");
            }
        });
    }
}
