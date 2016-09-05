package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.ButtonShack;
import com.cetcme.zytyumin.MyClass.NavigationView;
import com.kaopiz.kprogresshud.KProgressHUD;

public class ChangePasswordActivity extends Activity {

    private NavigationView navigationView;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText_1;
    private EditText newPasswordEditText_2;
    private Button changePasswordButton;

    private KProgressHUD kProgressHUD;
    private KProgressHUD okHUD;

    private boolean hasLogin;

    private String TAG = "ChangePasswordActivity";

    private int minPSWLength = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        hasLogin = user.getBoolean("hasLogin", false);

        initNavigationView();
        initEditText();
        initButton();
        initHud();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_change_password_activity);
        /**
         * 未登陆为重置密码，已登陆为修改密码
         */
        if (!hasLogin) {
            navigationView.setTitle("重置密码");
        } else {
            navigationView.setTitle("修改密码");
        }
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
        newPasswordEditText_1 = (EditText) findViewById(R.id.new_password_1_editText_in_change_password_activity);
        newPasswordEditText_2 = (EditText) findViewById(R.id.new_password_2_editText_in_change_password_activity);

        /**
         * 未登陆为重置密码，已登陆为修改密码
         */
        if (!hasLogin) {
            oldPasswordEditText.setEnabled(false);
            oldPasswordEditText.setHint("请设置您的新密码");
            newPasswordEditText_1.requestFocus();
        }
    }

    private void initButton() {
        changePasswordButton = (Button) findViewById(R.id.change_password_button_in_change_password_activity);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 未登陆为重置密码，已登陆为修改密码
                 */
                if (!hasLogin) {
                    resetPSW();
                } else {
                    changePSW();
                }
            }
        });
    }

    private void resetPSW() {
        //TODO
        Log.i(TAG, "resetPSW: ");
    }

    private void changePSW() {
        //TODO
        Log.i(TAG, "changePSW: ");

        String oldPSW = oldPasswordEditText.getText().toString();
        String newPSW1 = newPasswordEditText_1.getText().toString();
        String newPSW2 = newPasswordEditText_2.getText().toString();

        /**
         * 密码未填全
         */
        if (oldPSW.isEmpty() || newPSW1.isEmpty() || newPSW2.isEmpty()) {
            Toast.makeText(getApplicationContext(), "信息未填全", Toast.LENGTH_SHORT).show();
            ButtonShack.run(changePasswordButton);
            return;
        }

        /**
         * 原密码错误
         */
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        String password = user.getString("password", "");
        if (!password.equals(oldPSW)) {
            Toast.makeText(getApplicationContext(), "原密码错误", Toast.LENGTH_SHORT).show();
            ButtonShack.run(changePasswordButton);
            return;
        }

        /**
         * 新密码位数不足
         */
        if (newPSW1.length() < minPSWLength) {
            Toast.makeText(getApplicationContext(), "新密码位数不能少于" + minPSWLength + "位", Toast.LENGTH_SHORT).show();
            ButtonShack.run(changePasswordButton);
            return;
        }

        /**
         * 新密码不一致
         */
        if (!newPSW1.equals(newPSW2)) {
            Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
            ButtonShack.run(changePasswordButton);
            return;
        }

        /**
         * 新密码与原密码一致
         */
        if (newPSW1.equals(oldPSW)) {
            Toast.makeText(getApplicationContext(), "新密码与原密码相同", Toast.LENGTH_SHORT).show();
            ButtonShack.run(changePasswordButton);
            return;
        }

        /**
         * 修改密码请求
         */

        Log.i(TAG, "changePSW: 修改密码网络请求");
        kProgressHUD.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                kProgressHUD.dismiss();
                okHUD.setLabel("测试成功");
                okHUD.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        okHUD.dismiss();
                    }
                }, 1000);
            }
        }, 1000);

    }

    private void initHud() {
        //hudView
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("登陆中")
                .setAnimationSpeed(1)
                .setDimAmount(0.3f)
                .setSize(110, 110)
                .setCancellable(false);
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.checkmark);
        okHUD  =  KProgressHUD.create(this)
                .setCustomView(imageView)
                .setLabel("登陆成功")
                .setCancellable(false)
                .setSize(110,110)
                .setDimAmount(0.3f);
    }

}
