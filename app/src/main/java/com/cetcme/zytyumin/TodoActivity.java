package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.NavigationView;
import com.fr.android.activity.LoadAppFromURLActivity;

public class TodoActivity extends Activity {

    private NavigationView navigationView;
    private String TAG = "TodoActivity";
    private int[] todoNumber = {0, 0, 0, 0};

    private String url_line_1 = "http://61.164.218.155:5008/WebReport/ReportServer?reportlet=apply%2Fpic_opinion_phone.cpt&op=write&appid=109";
    private String url_line_2 = "";
    private String url_line_3 = "http://61.164.218.155:5008/WebReport/ReportServer?reportlet=apply%2Fchange_opinion_phone.cpt&op=write&appid=225";
    private String url_line_4 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        initNavigationView();
        initNumber();
        initLineClick();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_todo_activity);
        navigationView.setTitle("待办任务");
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

    private void initNumber() {
        TextView number1 = (TextView) findViewById(R.id.numberText1_in_todo_activity);
        TextView number2 = (TextView) findViewById(R.id.numberText2_in_todo_activity);
        TextView number3 = (TextView) findViewById(R.id.numberText3_in_todo_activity);
        TextView number4 = (TextView) findViewById(R.id.numberText4_in_todo_activity);

        number1.setText(String.valueOf(todoNumber[0]));
        number2.setText(String.valueOf(todoNumber[1]));
        number3.setText(String.valueOf(todoNumber[2]));
        number4.setText(String.valueOf(todoNumber[3]));

        number1.setVisibility( todoNumber[0] == 0 ? View.INVISIBLE : View.VISIBLE);
        number2.setVisibility( todoNumber[1] == 0 ? View.INVISIBLE : View.VISIBLE);
        number3.setVisibility( todoNumber[2] == 0 ? View.INVISIBLE : View.VISIBLE);
        number4.setVisibility( todoNumber[3] == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private void initLineClick() {
        findViewById(R.id.line_1_in_todo_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("url", url_line_1);
                intent.putExtra("title", getString(R.string.line_1_in_todo_activity));
                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.line_2_in_todo_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("url", url_line_2);
//                intent.putExtra("title", getString(R.string.line_2_in_todo_activity));
//                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "待完善", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.line_3_in_todo_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("url", url_line_3);
                intent.putExtra("title", getString(R.string.line_3_in_todo_activity));
                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.line_4_in_todo_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("url", url_line_4);
//                intent.putExtra("title", getString(R.string.line_4_in_todo_activity));
//                intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "待完善", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
