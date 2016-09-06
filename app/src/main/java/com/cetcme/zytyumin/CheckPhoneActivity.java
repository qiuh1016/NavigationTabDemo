package com.cetcme.zytyumin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cetcme.zytyumin.MyClass.ButtonShack;
import com.cetcme.zytyumin.MyClass.CodeUtils;
import com.cetcme.zytyumin.MyClass.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.steamcrafted.loadtoast.LoadToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckPhoneActivity extends Activity implements View.OnClickListener{

    private EditText phoneEditText;
    private EditText codeUtilsEditText;
    private EditText smsEditText;

    private ImageView codeUtilsImageView;
    private Button getSMSButton;
    private Button nextButton;

    private Bitmap codeUtilsBitmap;
    private String codeUtilsCode;

    private boolean isSignUp;

    private boolean isSendSMS = false;

    private boolean stopCountDown = false;
    private String sendSMSPhoneNumber;

    private String smsCode = "";

    private String TAG = "CheckPhoneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone);

        Bundle bundle = getIntent().getExtras();
        isSignUp = bundle.getBoolean("isSignUp");

        initNavigationView();
        initUI();
        initCodeUtils();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_check_phone_activity);
        navigationView.setTitle("验证手机号");
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

    private void initUI() {
        phoneEditText = (EditText) findViewById(R.id.cellphone_editText_in_check_phone_activity);
        codeUtilsEditText = (EditText) findViewById(R.id.codeUtils_editText_in_check_phone_activity);
        smsEditText = (EditText) findViewById(R.id.sms_editText_in_check_phone_activity);

        codeUtilsImageView = (ImageView) findViewById(R.id.codeUtils_image_in_check_phone_activity);
        getSMSButton = (Button) findViewById(R.id.sms_button_in_check_phone_activity);
        nextButton = (Button) findViewById(R.id.next_button_in_check_phone_activity);

        codeUtilsImageView.setOnClickListener(this);
        getSMSButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

    }

    /**
     * 获取手机号码
     */
    private String getMobilePhoneNumber() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();//获取智能设备唯一编号
        String tel  = tm.getLine1Number();//获取本机号码
        String imei = tm.getSimSerialNumber();//获得SIM卡的序号
        String imsi = tm.getSubscriberId();//得到用户Id
        return tel;
    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    private void insertPhoneNumber () {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                //
            }
        } else {
            phoneEditText.setText(getMobilePhoneNumber());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms_button_in_check_phone_activity:
                getSMSButtonTapped();
                break;
            case R.id.codeUtils_image_in_check_phone_activity:
                initCodeUtils();
                break;
            case R.id.next_button_in_check_phone_activity:
                next();
                break;
        }
    }

    /**
     * 生成图片随机码
     * @author qh
     * created at 8/31/16 13:42
     */
    private void initCodeUtils() {
        codeUtilsEditText.setText("");
        CodeUtils codeUtils = new CodeUtils();
        codeUtilsBitmap = codeUtils.createBitmap();
        codeUtilsCode = codeUtils.getCode();
        codeUtilsImageView.setImageBitmap(codeUtilsBitmap);
        Log.i("main", "code: " + codeUtilsCode);
    }

    private void getSMSButtonTapped() {
        /**
         * 判断手机号是否正确
         */
        final String inputMobileNO = phoneEditText.getText().toString();
        if (!isMobileNO(inputMobileNO)) {
            Toast.makeText(this, "手机号错误", Toast.LENGTH_SHORT).show();
            initCodeUtils();
            phoneEditText.requestFocus();
            return;
        }

        /**
         * 判断图片随机码是否正确
         */
        String inputCode = codeUtilsEditText.getText().toString();
        if (!inputCode.equals(codeUtilsCode)) {
            Toast.makeText(this, "图片随机码错误", Toast.LENGTH_SHORT).show();
            initCodeUtils();
            codeUtilsEditText.requestFocus();
            return;
        }

        /**
         * sms button 显示倒数
         */
        getSMSButton.setEnabled(false);
        getSMSButton.setText("60");
        new Thread(new CountDown()).start();
        smsEditText.requestFocus();

        /**
         * 请求发送短信
         */
        sendSMSPhoneNumber = inputMobileNO;
        sendSMS();

    }

    private void sendSMS() {
        isSendSMS = true;

        RequestParams params = new RequestParams();
        params.put("phoneNo", sendSMSPhoneNumber);
        params.put("flag", isSignUp ? 0 : 1); //flag：标示0为注册，1为忘记密码
        String urlBody = getString(R.string.serverIP) + getString(R.string.sendSMSUrl);

        Log.i(TAG, "sendSMS: " + params.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlBody, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    int flag = response.getInt("Flag");
                    String msg = response.getString("Msg");

                    Log.i(TAG, "onSuccess: " + response.toString());
                    if (flag == 0) {
                        /**
                         * 发送成功
                         */
                        smsCode = response.getString("Code");
                        Log.i(TAG, "onSuccess: sms send success");
                    } else if (flag == 1) {
                        Toast.makeText(getApplicationContext(), "手机号已存在", Toast.LENGTH_LONG).show();
                        isSendSMS = false;
                        ButtonShack.run(nextButton);
                        phoneEditText.requestFocus();
                        initCodeUtils();
                        stopCountDown = true;
                        Log.i(TAG, "onSuccess: phone exist");
                    } else if (flag == 2) {
                        Toast.makeText(getApplicationContext(), "手机号不存在", Toast.LENGTH_LONG).show();
                        isSendSMS = false;
                        ButtonShack.run(nextButton);
                        phoneEditText.requestFocus();
                        initCodeUtils();
                        stopCountDown = true;
                        Log.i(TAG, "onSuccess: phone not exist");
                    }
                } catch (JSONException e) {
                    /**
                     * json解析失败
                     */
                    e.printStackTrace();
                    Log.i(TAG, "onSuccess: sms send json error");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onSuccess: sms send network error");
                stopCountDown = true;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onSuccess: sms send network error");
                stopCountDown = true;
            }
        });
    }

    private void next() {

        String code = smsEditText.getText().toString();

        /**
         * 没发送过sms就return
         */
        if (!isSendSMS) {
            ButtonShack.run(nextButton);
            return;
        }

        /**
         * 如果短信验证码空或长度不为4就return
         */
        if (code.isEmpty() || code.length() != 4) {
            Toast.makeText(getApplicationContext(), "短信验证码错误", Toast.LENGTH_SHORT).show();
            ButtonShack.run(nextButton);
            return;
        }

        /**
         * 验证短信验证码
         */
        if (code.equals(smsCode)) {
            gotoNextActivity();
        } else {
            Toast.makeText(getApplicationContext(), "短信验证码错误", Toast.LENGTH_LONG).show();
            ButtonShack.run(nextButton);
        }

    }

    private void gotoNextActivity() {
        Intent intent = new Intent();
        if (isSignUp) {
            intent.setClass(this, RegisterInfoActivity.class);
            Bundle phoneBundle = new Bundle();
            phoneBundle.putString("phone", sendSMSPhoneNumber);
            intent.putExtras(phoneBundle);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
        } else {
            intent.setClass(this, ChangePasswordActivity.class);
            Bundle phoneBundle = new Bundle();
            phoneBundle.putString("phone", sendSMSPhoneNumber);
            intent.putExtras(phoneBundle);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
        }
    }

    // 线程类
    class CountDown implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 60; i++) {

                if (stopCountDown) {
                    break;
                }

                try {
                    Thread.sleep(1000);
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getSMSButton.setText(String.valueOf(59 - finalI));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getSMSButton.setEnabled(true);
                    getSMSButton.setText("重新获取");
                }
            });

            stopCountDown = false;
        }
    }

    /**
     * 正则表达式判断手机号
     * @author qh
     * created at 8/31/16 13:59
     */
    public static boolean isMobileNO (String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


}
