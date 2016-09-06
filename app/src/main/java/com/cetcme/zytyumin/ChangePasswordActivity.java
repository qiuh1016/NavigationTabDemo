package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.cetcme.zytyumin.MyClass.PrivateEncode;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

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
    private int minPSWLength = 5;

    private SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        user = getSharedPreferences("user", Context.MODE_PRIVATE);
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
        Log.i(TAG, "resetPSW: ");

        String newPSW1 = newPasswordEditText_1.getText().toString();
        String newPSW2 = newPasswordEditText_2.getText().toString();

        /**
         * 密码未填全
         */
        if (newPSW1.isEmpty() || newPSW2.isEmpty()) {
            Toast.makeText(getApplicationContext(), "信息未填全", Toast.LENGTH_SHORT).show();
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
         * 忘记密码请求
         */
        Log.i(TAG, "changePSW: 忘记密码网络请求");
        String phone = getIntent().getExtras().getString("phone");
        resetPSWRequest(phone, newPSW1);
    }

    private void changePSW() {
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
        String sessionKey = user.getString("sessionKey", "");
        String account = user.getString("username", "");
        changePSWRequest(sessionKey, account, newPSW1);

    }

    private void changePSWRequest(String sessionKey, String account,String pwd) {
        kProgressHUD.show();

        RequestParams params = new RequestParams();
        params.put("pwd", PrivateEncode.getMD5(pwd));
        params.put("sessionKey", sessionKey);
        params.put("account", account);
        String url = getString(R.string.serverIP) + getString(R.string.changePSWUrl);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    kProgressHUD.dismiss();

                    Log.i(TAG, "onSuccess: changePSW" + response.toString());
                    int code = response.getInt("Code");
                    if (code == 0) {
                        okHUD.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                okHUD.dismiss();
                                onBackPressed();
                            }
                        }, 1000);

                    } else if (code == 2) {
                        Toast.makeText(getApplicationContext(), "登陆信息过期，请重新登陆", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = user.edit();//获取编辑器
                        editor.putBoolean("hasLogin", false);
                        editor.apply();//提交修改
                        startLoginActivity();
                        Log.i(TAG, "onSuccess: 登陆信息过期");
                    } else {
                        kProgressHUD.dismiss();
                        ButtonShack.run(changePasswordButton);
                        Toast.makeText(ChangePasswordActivity.this, "sessionKey错误", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    kProgressHUD.dismiss();
                    ButtonShack.run(changePasswordButton);
                    Toast.makeText(ChangePasswordActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "onFailure: get todo network error");
                kProgressHUD.dismiss();
                ButtonShack.run(changePasswordButton);
                Toast.makeText(ChangePasswordActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "onFailure: get todo network error");
                kProgressHUD.dismiss();
                ButtonShack.run(changePasswordButton);
                Toast.makeText(ChangePasswordActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPSWRequest(String phone, String pwd) {
        kProgressHUD.show();

        RequestParams params = new RequestParams();
        params.put("pwd", PrivateEncode.getMD5(pwd));
        params.put("phone", phone);
        String url = getString(R.string.serverIP) + getString(R.string.forgetPSWUrl);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    kProgressHUD.dismiss();

                    Log.i(TAG, "onSuccess: changePSW" + response.toString());
                    boolean flag = response.getBoolean("Flag");
                    if (flag) {
                        okHUD.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                okHUD.dismiss();
                                onBackPressed();
                            }
                        }, 1000);
                    } else{
                        kProgressHUD.dismiss();
                        ButtonShack.run(changePasswordButton);
                        Toast.makeText(ChangePasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    kProgressHUD.dismiss();
                    ButtonShack.run(changePasswordButton);
                    Toast.makeText(ChangePasswordActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "onFailure: change psw network error");
                kProgressHUD.dismiss();
                ButtonShack.run(changePasswordButton);
                Toast.makeText(ChangePasswordActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "onFailure: change psw network error");
                kProgressHUD.dismiss();
                ButtonShack.run(changePasswordButton);
                Toast.makeText(ChangePasswordActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initHud() {
        //hudView
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("修改中")
                .setAnimationSpeed(1)
                .setDimAmount(0.3f)
                .setSize(110, 110)
                .setCancellable(false);
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.checkmark);
        okHUD  =  KProgressHUD.create(this)
                .setCustomView(imageView)
                .setLabel("修改成功")
                .setCancellable(false)
                .setSize(110,110)
                .setDimAmount(0.3f);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
    }

}
