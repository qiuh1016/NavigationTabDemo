package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.cetcme.zytyumin.xia.SearcherClaimActivity;
import com.cetcme.zytyumin.xia.SearcherDrawingCheckActivity;
import com.cetcme.zytyumin.xia.SearcherPayEnsuranceActivity;
import com.cetcme.zytyumin.xia.SearcherShipDetectActivity;
import com.cetcme.zytyumin.xia.SearcherTradeActivity;

import MyClass.NavigationView;

public class RecordActivity extends Activity implements View.OnClickListener{

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

    private void initLinearLayout() {
        findViewById(R.id.line_1_in_record_activity).setOnClickListener(this);
        findViewById(R.id.line_2_in_record_activity).setOnClickListener(this);
        findViewById(R.id.line_3_in_record_activity).setOnClickListener(this);
        findViewById(R.id.line_4_in_record_activity).setOnClickListener(this);
        findViewById(R.id.line_5_in_record_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.line_1_in_record_activity:
                intent.setClass(this, SearcherPayEnsuranceActivity.class);
                break;
            case R.id.line_2_in_record_activity:
                intent.setClass(this, SearcherClaimActivity.class);
                break;
            case R.id.line_3_in_record_activity:
                intent.setClass(this, SearcherTradeActivity.class);
                break;
            case R.id.line_4_in_record_activity:
                intent.setClass(this, SearcherShipDetectActivity.class);
                break;
            case R.id.line_5_in_record_activity:
                intent.setClass(this, SearcherDrawingCheckActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
