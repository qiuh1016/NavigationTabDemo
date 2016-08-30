package com.cetcme.zytyumin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import MyClass.NavigationView;

public class ChangePasswordActivity extends Activity {

    private NavigationView navigationView;
    private EditText oldPasswordEditText;
    private EditText newPasseordEditText_1;
    private EditText newPasswordEditText_2;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initNavigationView();
        initEditText();
        initButton();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_change_password_activity);
        navigationView.setTitle("修改密码");
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

    private void initEditText() {
        oldPasswordEditText = (EditText) findViewById(R.id.old_password_editText_in_change_password_activity);
        newPasseordEditText_1 = (EditText) findViewById(R.id.new_password_1_editText_in_change_password_activity);
        newPasswordEditText_2 = (EditText) findViewById(R.id.new_password_2_editText_in_change_password_activity);
    }

    private void initButton() {
        changePasswordButton = (Button) findViewById(R.id.change_password_button_in_change_password_activity);
    }

}
