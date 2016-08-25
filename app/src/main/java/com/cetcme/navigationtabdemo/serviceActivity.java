package com.cetcme.navigationtabdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import navigationView.NavigationView;

public class serviceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Bundle bundle = this.getIntent().getExtras();
        String title = bundle.getString("title");

        initNavigationView(title);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView(String title) {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_map_activity);
        navigationView.setTitle(title);
//        navigationView.setTitleTextColor(Color.WHITE);
        navigationView.setBackView(android.R.drawable.ic_menu_revert);
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
}
