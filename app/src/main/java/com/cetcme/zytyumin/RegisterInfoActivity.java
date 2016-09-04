package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.ButtonShack;
import com.cetcme.zytyumin.MyClass.NavigationView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterInfoActivity extends Activity {

    private Spinner spinner;

    private EditText accountEditText;
    private EditText psw1EditText;
    private EditText psw2EditText;
    private EditText nameEditText;
    private EditText idEditText;
    private EditText emailEditText;

    private TextView nameTextView;
    private TextView idTextView;

    private CheckBox checkBox;
    private Button signUpButton;

    private String phone;
    private int accountType = 1;

    private KProgressHUD kProgressHUD;
    private KProgressHUD okHUD;

    private String TAG = "RegisterInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        /**
         * 获取手机号
         */
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");

        initNavigationView();
        initUI();
        initHud();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_register_info_activity);
        navigationView.setTitle("注册");
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
        accountEditText = (EditText) findViewById(R.id.account_editText_in_register_info_activity);
        psw1EditText = (EditText) findViewById(R.id.psw_1_editText_in_register_info_activity);
        psw2EditText = (EditText) findViewById(R.id.psw_2_editText_in_register_info_activity);
        nameEditText = (EditText) findViewById(R.id.name_editText_in_register_info_activity);
        idEditText = (EditText) findViewById(R.id.id_editText_in_register_info_activity);
        emailEditText = (EditText) findViewById(R.id.email_editText_in_register_info_activity);

        nameTextView = (TextView) findViewById(R.id.name_TextView_in_register_info_activity);
        idTextView = (TextView) findViewById(R.id.id_TextView_in_register_info_activity);

        /**
         * 设置为英文键盘
         */
        accountEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        idEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        /**
         * 注册按钮
         */
        signUpButton = (Button) findViewById(R.id.sign_up_button_in_register_info_activity);

        /**
         * spinner
         */
        spinner = (Spinner) findViewById(R.id.spinner_in_register_info_activity);
        final String[] mItems = {"个人帐号", "企业帐号"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("main", "onItemSelected: " + mItems[position]);
                if (position == 1) {
                    nameTextView.setText("企业名称");
                    idTextView.setText("企业注册号");
                    accountType = 2;
                } else {
                    nameTextView.setText("姓名");
                    idTextView.setText("身份证号");
                    accountType = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * 单选框
         */
        checkBox = (CheckBox) findViewById(R.id.checkBox_in_register_info_activity);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                signUpButton.setEnabled(isChecked);
                checkBox.setTextColor(isChecked ? getResources().getColor( R.color.text_clo) : getResources().getColor( R.color.red));
            }
        });

    }

    public void registerButtonTapper(View v) {

        String account = accountEditText.getText().toString();
        String password_1 = psw1EditText.getText().toString();
        String password_2 = psw2EditText.getText().toString();
        String name = nameEditText.getText().toString();
        String id = idEditText.getText().toString();
        String email = emailEditText.getText().toString();

        /**
         * 信息没填全 则返回
         */
        if (account.isEmpty()
                || password_1.isEmpty()
                || password_2.isEmpty()
                || name.isEmpty()
                || id.isEmpty() ) {
            Toast.makeText(getApplicationContext(), "信息未填全", Toast.LENGTH_SHORT).show();
            ButtonShack.run((Button) findViewById(R.id.sign_up_button_in_register_info_activity));
            return;
        }

        /**
         * 密码不一致 则返回
         */
        if (!password_1.equals(password_2)) {
            Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
            psw1EditText.setText("");
            psw2EditText.setText("");
            psw1EditText.requestFocus();
            return;
        }

        /**
         * 进度指示
         */
        kProgressHUD.show();

        /**
         * 注册请求
         */
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("psw", password_2);
        params.put("name", name);
        params.put("id", id);
        params.put("phone", phone);
        params.put("accountType", accountType); //flag：标示1为个人，2为企业
        if (!email.isEmpty()) {
            params.put("email", email);
        }
        String urlBody = getString(R.string.serverIP) + getString(R.string.registerUrl);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(urlBody, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(TAG, "onSuccess: " + response.toString());
                try {
                    boolean flag = response.getBoolean("Flag");
                    String msg = response.getString("Msg");

                    Log.i(TAG, "onSuccess: " + response.toString());
                    if (flag) {
                        /**
                         * 注册成功
                         */
                        kProgressHUD.dismiss();
                        okHUD.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                okHUD.dismiss();
                            }
                        }, 2000);
                        Log.i(TAG, "onSuccess: register success");
                    } else  {
                        kProgressHUD.dismiss();
                        ButtonShack.run((Button) findViewById(R.id.sign_up_button_in_register_info_activity));
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onSuccess: register fail");
                    }
                } catch (JSONException e) {
                    /**
                     * json解析失败
                     */
                    e.printStackTrace();
                    Log.i(TAG, "onSuccess: register fail");
                    kProgressHUD.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
                Log.i(TAG, "onSuccess: register network error");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                kProgressHUD.dismiss();
                Log.i(TAG, "onSuccess: register network error");
            }
        });

    }

    public void userTermsTextViewTapped(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("url", "file:///android_asset/www/terms.html");
        bundle.putString("title", "用户协议");
        intent.putExtras(bundle);
        intent.setClass(this, WebActivity.class);
        startActivity(intent);
    }

    private void initHud() {
        //hudView
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("注册中")
                .setAnimationSpeed(1)
                .setDimAmount(0.3f)
                .setSize(110, 110)
                .setCancellable(false);
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.checkmark);
        okHUD  =  KProgressHUD.create(this)
                .setCustomView(imageView)
                .setLabel("注册成功")
                .setCancellable(false)
                .setSize(110,110)
                .setDimAmount(0.3f);
    }
}
