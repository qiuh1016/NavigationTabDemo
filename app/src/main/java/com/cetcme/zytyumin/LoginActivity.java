package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.PrivateEncode;
import com.kaopiz.kprogresshud.KProgressHUD;


import com.cetcme.zytyumin.MyClass.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button forgetButton;
    private Button signUpButton;

    private String TAG = "LoginActivity";

    private KProgressHUD kProgressHUD;
    private KProgressHUD okHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initNavigationView();
        initUI();
        initHud();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.push_up_out_no_alpha);
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
                login();
                break;
            case R.id.forget_password_button_in_login_activity:
                Log.i(TAG, "onClick: forgetButton");
                openCheckPhoneActivity(false);
                break;
            case R.id.sign_up_password_button_in_login_activity:
                Log.i(TAG, "onClick: signUpButton");
                openCheckPhoneActivity(true);
                break;
        }
    }

    private void openCheckPhoneActivity(boolean isSignUp) {
        Intent checkPhoneIntent = new Intent();
        checkPhoneIntent.setClass(this, CheckPhoneActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSignUp", isSignUp);
        checkPhoneIntent.putExtras(bundle);
        startActivity(checkPhoneIntent);
        overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
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

    private void login() {
        /**
         * 判断用户名和密码是否填写
         */
        String username = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        kProgressHUD.show();

        OkHttpClient client = new OkHttpClient();
        String url = "http://61.164.218.155:8085/Account/login?loginName=" + username + "&password=" + PrivateEncode.getMD5(password) +"&deviceType=0&clientId=1";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: failed");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        kProgressHUD.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        JSONObject json = new JSONObject(response.body().string());
                        String msg = json.getString("msg");
                        final String sessionKey = json.getString("SessionKey");
                        if (msg.equals("成功")) {
                            kProgressHUD.dismiss();
                            okHUD.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                   okHUD.dismiss();
                                    SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = user.edit();
                                    editor.putBoolean("hasLogin", true);
                                    editor.putString("SessionKey", sessionKey);
                                    editor.apply();
                                    onBackPressed();
                                }
                            }, 1000);
                        } else {
                            kProgressHUD.dismiss();
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    kProgressHUD.dismiss();
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    kProgressHUD.dismiss();
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
