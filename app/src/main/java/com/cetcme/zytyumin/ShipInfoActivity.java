package com.cetcme.zytyumin;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.NavigationView;

public class ShipInfoActivity extends Activity {

    private String TAG = "ShipInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_info);

        initNavigationView();


    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_ship_info_activity);
        navigationView.setBackgroundResource(R.drawable.top_select);
        navigationView.setTitle("船名");
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
                finish();
            }
        });
    }

    /**
     * 点击视图外 关闭窗口
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        Log.i(TAG, "onTouchEvent: ");
        return true;
    }

    public void rightButtonTapped(View v) {
        Log.i(TAG, "rightButtonTapped: ");
//        this.finish();
    }

    public void leftButtonTapped(View v) {
        Log.i(TAG, "leftButtonTapped: ");
//        this.finish();

    }
}
