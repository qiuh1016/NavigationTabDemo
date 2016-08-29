package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import MyClass.NavigationView;

public class RecordActivity extends Activity implements View.OnClickListener{

    private LinearLayout linearLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initNavigationView();
        initLinearLayout();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_record_activity);
        navigationView.setTitle(getString(R.string.gird_2_in_fragment_homepage));
        navigationView.setBackView(R.drawable.back);
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

    private void initLinearLayout() {
        linearLayout1 = (LinearLayout) findViewById(R.id.line_1_in_record_activity);
        linearLayout1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_1_in_record_activity:
                Intent intent = new Intent();
                intent.setClass(this, TableActivity.class);
                startActivity(intent);
        }
    }
}
