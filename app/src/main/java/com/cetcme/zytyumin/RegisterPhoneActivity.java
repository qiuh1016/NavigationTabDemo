package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyClass.CodeUtils;
import MyClass.NavigationView;

public class RegisterPhoneActivity extends Activity implements View.OnClickListener{

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
        setContentView(R.layout.activity_register_phone);
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
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_register_phone_activity);
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
        phoneEditText = (EditText) findViewById(R.id.cellphone_editText_in_register_phone_activity);
        codeUtilsEditText = (EditText) findViewById(R.id.codeUtils_editText_in_register_phone_activity);
        codeUtilsImageView = (ImageView) findViewById(R.id.codeUtils_image_in_register_phone_activity);
        getSMSButton = (Button) findViewById(R.id.sms_button_in_register_phone_activity);
        nextButton = (Button) findViewById(R.id.next_button_in_register_phone_activity);

        codeUtilsImageView.setOnClickListener(this);
        getSMSButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        getSMSButton.setHeight(codeUtilsImageView.getHeight());
        getSMSButton.setWidth(codeUtilsImageView.getWidth());

        /**
         * 获取手机号码
         */
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();//获取智能设备唯一编号
        String tel  = tm.getLine1Number();//获取本机号码
        String imei = tm.getSimSerialNumber();//获得SIM卡的序号
        String imsi = tm.getSubscriberId();//得到用户Id
        phoneEditText.setText(tel);
        Log.i("main", "initUI: tel" + tel);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sms_button_in_register_phone_activity:
                checkPhone();
                break;
            case R.id.codeUtils_image_in_register_phone_activity:
                initCodeUtils();
                break;
            case R.id.next_button_in_register_phone_activity:
//                modifyCode();
                Intent intent = new Intent();
                intent.setClass(this, RegisterInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("phone", phoneEditText.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
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
        Log.i("main", "******: " + codeUtilsCode);
    }

    /**
     * 判断手机号和图片验证码是否正确
     * @author qh
     * created at 8/31/16 13:42
     */
    private void modifyCode() {

        String inputCode = codeUtilsEditText.getText().toString();

        if (inputCode.equals(codeUtilsCode)) {
            next();
        } else {
            Toast.makeText(this, "图片验证码错误", Toast.LENGTH_SHORT).show();
            initCodeUtils();
        }
    }

    private void checkPhone() {
        String inputMobileNO = phoneEditText.getText().toString();
        if (isMobileNO(inputMobileNO)) {
           getSMS();
        } else {
            Toast.makeText(this, "手机号错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSMS() {
        //TODO:
    }

    private void next() {
        //TODO:
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
