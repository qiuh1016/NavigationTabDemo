package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cetcme.zytyumin.MyClass.NavigationView;

public class RegisterInfoActivity extends Activity implements View.OnClickListener{

    private Spinner spinner;

    private EditText accountEditText;
    private EditText psw1EditText;
    private EditText psw2EditText;
    private EditText nameEditText;
    private EditText idEditText;
    private EditText phoneEditText;
    private EditText emailEditText;

    private CheckBox checkBox;
    private TextView userTermsTextView;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        initNavigationView();
        initUI();
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
        phoneEditText = (EditText) findViewById(R.id.phone_editText_in_register_info_activity);
        emailEditText = (EditText) findViewById(R.id.email_editText_in_register_info_activity);

        Bundle bundle = getIntent().getExtras();
        String phone = bundle.getString("phone");
        phoneEditText.setText(phone);

        signUpButton = (Button) findViewById(R.id.sign_up_button_in_register_info_activity);
        signUpButton.setOnClickListener(this);

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
                    nameEditText.setHint("企业名称");
                    idEditText.setHint("企业注册号");
                } else {
                    nameEditText.setHint("姓名");
                    idEditText.setHint("身份证号");
                }
                nameEditText.setText("");
                idEditText.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBox = (CheckBox) findViewById(R.id.checkBox_in_register_info_activity);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                signUpButton.setEnabled(isChecked);
                checkBox.setTextColor(isChecked ? getResources().getColor( R.color.text_clo) : getResources().getColor( R.color.red));
            }
        });

        userTermsTextView = (TextView) findViewById(R.id.user_terms_in_register_info_activity);
        userTermsTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_button_in_register_info_activity:
                break;
            case R.id.user_terms_in_register_info_activity:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("url", "file:///android_asset/www/terms.html");
                bundle.putString("title", "用户协议");
                intent.putExtras(bundle);
                intent.setClass(this, WebActivity.class);
                startActivity(intent);
                break;
        }
    }
}
