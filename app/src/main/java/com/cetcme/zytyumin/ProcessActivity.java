package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cetcme.zytyumin.MyClass.NavigationView;
import com.fr.android.activity.LoadAppFromURLActivity;

public class ProcessActivity extends Activity {

    private String TAG = "ProcessActivity";

    private String url_line_1;
    private String url_line_2;
    private String url_line_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        url_line_1 = getString(R.string.serverIP) + getString(R.string.process_line_1_url);
        url_line_2 = getString(R.string.serverIP) + getString(R.string.process_line_2_url);
        url_line_3 = getString(R.string.serverIP) + getString(R.string.process_line_3_url);

        initNavigationView();
        initLineClick();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha, R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_process_activity);
        navigationView.setTitle(getString(R.string.gird_5_in_fragment_homepage));
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
        findViewById(R.id.line_1_in_process_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 1");
                Intent intent = new Intent();
                intent.putExtra("url", url_line_1);
                intent.putExtra("title", getString(R.string.line_1_in_process_activity));
                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.line_2_in_process_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 2");
                Intent intent = new Intent();
                intent.putExtra("url", url_line_2);
                intent.putExtra("title", getString(R.string.line_2_in_process_activity));
                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.line_3_in_process_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 3");
                Intent intent = new Intent();
                intent.putExtra("url", url_line_3);
                intent.putExtra("title", getString(R.string.line_3_in_process_activity));
                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                startActivity(intent);
            }
        });
    }
}
