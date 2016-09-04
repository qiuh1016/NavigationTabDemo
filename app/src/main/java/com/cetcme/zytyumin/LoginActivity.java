package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.ButtonShack;
import com.cetcme.zytyumin.MyClass.DensityUtil;
import com.cetcme.zytyumin.MyClass.PrivateEncode;
import com.kaopiz.kprogresshud.KProgressHUD;


import com.cetcme.zytyumin.MyClass.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.steamcrafted.loadtoast.LoadToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText usernameEditText;
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

    private void initRequestFocus() {
        if (usernameEditText.getText().toString().isEmpty()) {
            usernameEditText.requestFocus();
        } else {
            if (passwordEditText.getText().toString().isEmpty()) {
                passwordEditText.requestFocus();
            }
        }
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
        usernameEditText = (EditText) findViewById(R.id.username_editText_in_login_activity);
        passwordEditText = (EditText) findViewById(R.id.password_editText_in_login_activity);
        /**
         * 设置为英文键盘
         */
        usernameEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

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
                loginWithASC();
                break;
            case R.id.forget_password_button_in_login_activity:
                Log.i(TAG, "onClick: forgetButton");
                openCheckPhoneActivity(false);
//                Toast.makeText(getApplicationContext(), "待开发", Toast.LENGTH_SHORT).show();
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

    private void loginWithASC () {
        /**
         * 判断用户名和密码是否填写
         */
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            ButtonShack.run(loginButton);
            return;
        }

        kProgressHUD.show();

        RequestParams params = new RequestParams();
        params.put("loginName", username);
        params.put("password", PrivateEncode.getMD5(password));
        params.put("deviceType", "0");
        params.put("clientId", "1");
        String urlBody = getString(R.string.serverIP) + getString(R.string.loginUrl);
        String url = "http://61.164.218.155:8085/Account/login?loginName="+username+"&password="+PrivateEncode.getMD5(password)+"&deviceType=0&clientId=1";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlBody, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    Log.i(TAG, "onSuccess: " + response.toString());
                    int code = response.getInt("Code");
                    String msg = response.getString("Msg");
                    String sessionKey = response.getString("SessionKey");
                    JSONObject user = response.getJSONObject("LogonUser");
                    String IDCard = user.getString("IDCard");

                    /**
                     * 登录成功
                     */
                    if (code == 0) {
                        kProgressHUD.dismiss();
                        okHUD.show();
                        saveData(true, username, password, sessionKey, IDCard);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                okHUD.dismiss();
                                onBackPressed();
                            }
                        },1000);
//                        getTODOCount(sessionKey, username);
                    } else {
                        /**
                         * 登录失败
                         */
                        kProgressHUD.dismiss();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onSuccess: rotation");
                        ButtonShack.run(loginButton);
                    }

                } catch (JSONException e) {
                    /**
                     * json解析失败
                     */
                    e.printStackTrace();
                    Log.i(TAG, "onResponse: json解析错误");
                    kProgressHUD.dismiss();
                    Toast.makeText(getApplicationContext(), "解析错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                kProgressHUD.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接失败1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                kProgressHUD.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接失败2", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getTODOCount(String sessionKey, String account) {
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("sessionKey", sessionKey);
        String urlBody = getString(R.string.serverIP) + getString(R.string.getTodoCountUrl);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlBody, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    Log.i(TAG, "onSuccess: getTODOCount" + response.toString());
                    int code = response.getInt("Code");
                    if (code == 0) {

                        //TODO: 暂时 数量不对
//                        {"Code":0,"Check_Drawing_Examine_Opinion_Count":0,"Check_Detect_Info_Detail_Inspection_Count":1,"Check_Detect_Info_Opinion_Count":0,"msg":null}
                        int Check_Drawing_Examine_Opinion_Count = response.getInt("Check_Drawing_Examine_Opinion_Count");
                        int Check_Detect_Info_Detail_Inspection_Count = response.getInt("Check_Detect_Info_Detail_Inspection_Count");
                        int Check_Detect_Info_Opinion_Count = response.getInt("Check_Detect_Info_Opinion_Count");

                        int todoSum = Check_Drawing_Examine_Opinion_Count + Check_Detect_Info_Detail_Inspection_Count + Check_Detect_Info_Opinion_Count;

                        /**
                         * 广播
                         */
                        Intent intent=new Intent();
                        intent.setAction("com.updateTodoCount");
                        intent.putExtra("todoSum" , todoSum);
                        sendBroadcast(intent);

                    }

                } catch (JSONException e) {
                    /**
                     * json解析失败
                     */
                    e.printStackTrace();
                    Log.i(TAG, "onResponse: json解析错误");
                    kProgressHUD.dismiss();
                    Toast.makeText(getApplicationContext(), "解析错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                kProgressHUD.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接失败1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                kProgressHUD.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接失败2", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveData(boolean hasLogin, String username, String password, String sessionKey, String IDCard) {
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = user.edit();//获取编辑器
        editor.putBoolean("hasLogin", hasLogin);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("sessionKey", sessionKey);
        editor.putString("IDCard", IDCard);
        editor.apply();//提交修改
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = user.getString("username", "");
        if (!username.isEmpty()) {
            usernameEditText.setText(username);
        }

        initRequestFocus();
    }

}
