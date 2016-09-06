package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.cetcme.zytyumin.MyClass.ButtonShack;
import com.cetcme.zytyumin.MyClass.PrivateEncode;
import com.cetcme.zytyumin.MyClass.Ship;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kaopiz.kprogresshud.KProgressHUD;


import com.cetcme.zytyumin.MyClass.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button forgetButton;
    private Button signUpButton;

    private CheckBox checkBox;

    private SharedPreferences user;

    private String TAG = "LoginActivity";

    private KProgressHUD kProgressHUD;
    private KProgressHUD okHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);

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

        checkBox = (CheckBox) findViewById(R.id.checkBox_in_login_activity);
        checkBox.setChecked(user.getBoolean("rememberPSW", false));
        checkBox.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.tab_text_normal));

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

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlBody, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    Log.i(TAG, "onSuccess: " + response.toString());
                    int code = response.getInt("Code");
                    String msg = response.getString("Msg");
                    String sessionKey = response.getString("SessionKey");

                    /**
                     * 登录成功
                     */
                    if (code == 0) {

                        JSONObject user = response.getJSONObject("LogonUser");
                        String IDCard = user.getString("IDCard");
                        JSONArray shipArray = user.getJSONArray("Ships");
                        dealWhitShipArray(shipArray);

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
                        sendLoginFlagBroadcast(true);
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

    private void saveData(boolean hasLogin, String username, String password, String sessionKey, String IDCard) {
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = user.edit();//获取编辑器
        editor.putBoolean("hasLogin", hasLogin);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("sessionKey", sessionKey);
        editor.putString("IDCard", IDCard);
        editor.putBoolean("rememberPSW", checkBox.isChecked());
        editor.apply();//提交修改
    }

    @Override
    public void onResume() {
        super.onResume();
        String username = user.getString("username", "");
        if (!username.isEmpty()) {
            usernameEditText.setText(username);
        }

        if (user.getBoolean("rememberPSW", false)) {
            passwordEditText.setText(user.getString("password", ""));
        }

        loginButton.requestFocus();

    }

    public void rememberPSWLineTapped(View v) {
        checkBox.setChecked(!checkBox.isChecked());
    }

    private void sendLoginFlagBroadcast(boolean login) {
        Intent intent = new Intent();
        intent.setAction("com.loginFlag");
        intent.putExtra("loginFlag" , login);
        sendBroadcast(intent);
    }

    private void sendShipDataBroadcast(List<Ship> ships) {
        Intent intent = new Intent();
        intent.setAction("com.shipData");
        intent.putExtra("ships" , (Serializable) ships);
        sendBroadcast(intent);
    }

    private void dealWhitShipArray(JSONArray shipArray) {
        List<Ship> ships = new ArrayList<>();

        for (int i = 0; i < shipArray.length(); i++) {
            try {
                JSONObject shipJSON = shipArray.getJSONObject(i);
                String shipName = shipJSON.getString("ShipName");
                String shipNumber = shipJSON.getString("ShipNo");
                boolean deviceInstall = shipJSON.getBoolean("DeviceInstall");

                double latitude = shipJSON.getDouble("Latitude") / 600000.0; //30.0 + i / 2.0;
                double longitude = shipJSON.getDouble("Longitude") / 600000.0; //120.0 + i / 2.0;

                Ship ship = new Ship(shipName, shipNumber, latitude, longitude, deviceInstall);
                Log.i(TAG, "dealWhitShipArray: " + latitude);
                Log.i(TAG, "dealWhitShipArray: " + longitude);
                ships.add(ship);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        //TODO: test
//        if (shipArray.length() == 0) {
//            ships.add(new Ship("浙普渔运18888", "3309222001090011", 30,122, true));
//            ships.add(new Ship("浙路渔81966", "3310811999010012", 32.5,122, false));
//        } else {
//
//        }

        sendShipDataBroadcast(ships);
    }

}
