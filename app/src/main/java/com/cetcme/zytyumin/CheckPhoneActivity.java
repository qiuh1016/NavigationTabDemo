package com.cetcme.zytyumin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cetcme.zytyumin.MyClass.CodeUtils;
import com.cetcme.zytyumin.MyClass.NavigationView;
import com.cetcme.zytyumin.MyClass.PrivateEncode;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckPhoneActivity extends Activity implements View.OnClickListener{

    private EditText phoneEditText;
    private EditText codeUtilsEditText;
    private ImageView codeUtilsImageView;
    private Button getSMSButton;
    private Button nextButton;

    private Bitmap codeUtilsBitmap;
    private String codeUtilsCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone);
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
        codeUtilsImageView = (ImageView) findViewById(R.id.codeUtils_image_in_check_phone_activity);
        getSMSButton = (Button) findViewById(R.id.sms_button_in_check_phone_activity);
        nextButton = (Button) findViewById(R.id.next_button_in_check_phone_activity);

        codeUtilsImageView.setOnClickListener(this);
        getSMSButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        insertPhoneNumber();

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
                checkCodeAndPhone();
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
     * 生成图片验证码
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

    /**
     * 判断手机号和图片验证码是否正确
     * @author qh
     * created at 8/31/16 13:42
     */
    private void checkCodeAndPhone() {
        String inputCode = codeUtilsEditText.getText().toString();
        if (!inputCode.equals(codeUtilsCode)) {
            initCodeUtils();
            return;
        }
        String inputMobileNO = phoneEditText.getText().toString();
        if (isMobileNO(inputMobileNO)) {
            getSMS();
        } else {
            Toast.makeText(this, "手机号错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSMS() {
        //TODO: 让服务器发送sms
        /**
         * 定时器
         */
        getSMSButton.setEnabled(false);
        getSMSButton.setText("60");
        new Thread(new ThreadShow()).start();
    }

    private void next() {
        //TODO:向服务器验证短信

        Bundle bundle = getIntent().getExtras();
        boolean isSignUp = bundle.getBoolean("isSignUp");

        Intent intent = new Intent();
        /**
         * 跳转
         */
        if (isSignUp) {
            intent.setClass(this, RegisterInfoActivity.class);
            Bundle phoneBundle = new Bundle();
            phoneBundle.putString("phone", phoneEditText.getText().toString());
            intent.putExtras(phoneBundle);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
        } else {
            intent.setClass(this, ChangePasswordActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
        }

    }

    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 60; i++) {
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

    public static class LoginActivity extends Activity implements View.OnClickListener{

        private EditText userNameEditText;
        private EditText passwordEditText;
        private Button loginButton;
        private Button forgetButton;
        private Button signUpButton;

        private String TAG = "LoginActivity";

        private KProgressHUD kProgressHUD;
        private KProgressHUD okHUD;

        private String mURL = null;

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
                    Log.v("OKHttp","failed");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                            kProgressHUD.dismiss();
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if(response.isSuccessful()){
                            //The call was successful. print it to the log
                            Log.v("OKHttp",response.body().string());

                            try {
                                JSONObject json = new JSONObject(response.body().string());
                                String msg = json.getString("msg");
                                final String SessionKey = json.getString("SessionKey");

                                /**
                                 * 登录成功
                                 */
                                if (msg.equals("成功")) {
                                    kProgressHUD.dismiss();
                                    okHUD.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            okHUD.dismiss();
                                            SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = user.edit();//获取编辑器
                                            editor.putBoolean("hasLogin", true);
                                            editor.putString("SessionKey",SessionKey);
                                            editor.apply();//提交修改
                                            onBackPressed();
                                        }
                                    },1000);
                                } else {
                                    /**
                                     * 登录失败
                                     */
                                    kProgressHUD.dismiss();
                                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                kProgressHUD.dismiss();
                                Toast.makeText(LoginActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.v("OKHttp","failed");
                            kProgressHUD.dismiss();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });



        }


    }
}
