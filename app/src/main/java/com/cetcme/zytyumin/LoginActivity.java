package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import MyClass.CodeUtils;
import MyClass.NavigationView;

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button forgetButton;
    private Button signUpButton;

    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initNavigationView();
        initUI();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay,
                R.anim.push_up_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_login_activity);
        navigationView.setTitle("登陆");
        navigationView.setBackView(R.drawable.icon_back_button);
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

    private void initUI() {
        userNameEditText = (EditText) findViewById(R.id.username_editText_in_login_activity);
        passwordEditText = (EditText) findViewById(R.id.password_editText_in_login_activity);

        loginButton = (Button) findViewById(R.id.login_button_in_login_activity);
        forgetButton = (Button) findViewById(R.id.forget_password_button_in_login_activity);
        signUpButton = (Button) findViewById(R.id.sign_up_password_button_in_login_activity);

        loginButton.setOnClickListener(this);
        forgetButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_in_login_activity:
                Log.i(TAG, "onClick: loginButton");
                break;
            case R.id.forget_password_button_in_login_activity:
                Log.i(TAG, "onClick: forgetButton");
                break;
            case R.id.sign_up_password_button_in_login_activity:
                Log.i(TAG, "onClick: signUpButton");
                Intent intent = new Intent();
                intent.setClass(this, InputPhoneActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                break;
        }
    }

}
