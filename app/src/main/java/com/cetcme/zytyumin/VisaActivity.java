package com.cetcme.zytyumin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.cetcme.zytyumin.MyClass.NavigationView;

public class VisaActivity extends Activity {

    private String shipName;
    private String shipNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visa);

        getIntentData();
        initNavigationView();

    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_visa_activity);
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
        shipNo = bundle.getString("shipNo");
    }
}
