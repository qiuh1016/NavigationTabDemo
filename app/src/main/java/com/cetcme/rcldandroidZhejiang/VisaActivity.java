package com.cetcme.rcldandroidZhejiang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.rcld.PunchActivity;
import com.cetcme.rcldandroidZhejiang.rcld.ioConfirmActivity;
import com.cetcme.rcldandroidZhejiang.rcld.ioLogActivity;
import com.umeng.analytics.MobclickAgent;

public class VisaActivity extends Activity {

    private String shipName;
    private String shipNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa);

        getIntentData();
        initNavigationView();
        initUI();
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_visa_activity);
        navigationView.setTitle(shipName);
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

    private void getIntentData() {
        Bundle bundle = this.getIntent().getExtras();
        shipName = bundle.getString("shipName");
        shipNo = bundle.getString("shipNumber");
    }

    private void initUI() {
        findViewById(R.id.line_1_in_visa_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("iofFlag", 1);
                bundle.putString("shipNo", shipNo);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ioConfirmActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });

        findViewById(R.id.line_2_in_visa_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("iofFlag", 2);
                bundle.putString("shipNo", shipNo);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ioConfirmActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });

        findViewById(R.id.line_3_in_visa_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("shipNo", shipNo);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PunchActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });

        findViewById(R.id.line_4_in_visa_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("shipNo", shipNo);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ioLogActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });


    }

}
