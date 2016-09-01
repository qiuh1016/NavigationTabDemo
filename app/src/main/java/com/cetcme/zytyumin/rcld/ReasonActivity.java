package com.cetcme.zytyumin.rcld;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.NavigationView;
import com.cetcme.zytyumin.R;
import com.cetcme.zytyumin.MyClass.PrivateEncode;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.util.ArrayList;

public class ReasonActivity extends Activity implements View.OnClickListener{

    private TextView nameTextView;
    private TextView idTextView;
    private TextView reasonTextView;
    private Button addButton;

    private Toast toast;

    private String name;
    private String id;
    private String reason;

    private SharedPreferences user;

    private ArrayList<String> ids = new ArrayList<>();
    private Boolean allowBackPress = true;

    //debug
    private Button fillButton;
    private Button idCheckButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);

        /**
         * umeng 推送
         */
        PushAgent.getInstance(this).onAppStart();

        Bundle bundle = this.getIntent().getExtras();
        ids = bundle.getStringArrayList("ids");
        Log.i("Main",ids.toString());

        toast = Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);

        initNavigationView();
        initUI();

    }

    private void initUI() {
        nameTextView = (TextView) findViewById(R.id.name_in_reason_activity);
        idTextView = (TextView) findViewById(R.id.id_in_reason_activity);
        reasonTextView = (TextView) findViewById(R.id.reason_in_reason_activity);
        addButton = (Button) findViewById(R.id.add_button_in_reason_activity);
        fillButton = (Button) findViewById(R.id.fill_button_in_reason_activity);
        idCheckButton = (Button) findViewById(R.id.id_check_button_in_reason_activity);
        addButton.setOnClickListener(this);
        fillButton.setOnClickListener(this);
        idCheckButton.setOnClickListener(this);

        //文本改变监听
        nameTextView.addTextChangedListener(textChangeWatcher);
        idTextView.addTextChangedListener(textChangeWatcher);
        reasonTextView.addTextChangedListener(textChangeWatcher);

        name = nameTextView.getText().toString();
        id = idTextView.getText().toString();
        reason = reasonTextView.getText().toString();

        changeButtonState(false);

        //debugMode
        user = getSharedPreferences("user", 0);
        fillButton.setVisibility(user.getBoolean("debugMode", false) ? View.VISIBLE : View.INVISIBLE);
        idCheckButton.setVisibility(user.getBoolean("debugMode", false) ? View.VISIBLE : View.INVISIBLE);
        idCheckButton.setText(user.getBoolean("idCheck", true)? "ID CHECK: ON" : "ID CHECK: OFF");
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_reason_activity);
        navigationView.setTitle("添加人员");
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    public void onBackPressed() {
        if (allowBackPress) {
            super.onBackPressed();
            overridePendingTransition(R.anim.push_right_in_no_alpha,R.anim.push_right_out_no_alpha);
        }
    }

    @Override
    public void onClick(View v) {
        Boolean debugModeON = user.getBoolean("debugMode", false);
        Boolean idCheckON   = user.getBoolean("idCheck"  , true );

        switch (v.getId()) {
            case R.id.add_button_in_reason_activity:

                name = nameTextView.getText().toString();
                id = idTextView.getText().toString();
                reason = reasonTextView.getText().toString();

                //身份证不重复
                for (String idNo: ids) {
                    if (id.equals(idNo)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReasonActivity.this);
                        builder.setMessage("身份证有重复");
                        builder.setTitle("错误");
                        builder.setIcon(android.R.drawable.ic_delete);
                        builder.setPositiveButton("好的", null);

                        /**
                         * 设置自定义按钮
                         */
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        Button btnPositive = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
                        btnPositive.setTextColor(getResources().getColor(R.color.main_color));
                        return;
                    }
                }


                //身份证校验
                Boolean needCheck = !(debugModeON && !idCheckON);
                Boolean isCard = needCheck ? new PrivateEncode().isCard(id) : (id.length() == 18);
                if (!isCard) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReasonActivity.this);
                    builder.setMessage("身份证错误");
                    builder.setTitle("错误");
                    builder.setIcon(android.R.drawable.ic_delete);
                    builder.setPositiveButton("好的", null);
                    /**
                     * 设置自定义按钮
                     */
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    Button btnPositive = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
                    btnPositive.setTextColor(getResources().getColor(R.color.main_color));
                    return;
                }

                //增加
                SharedPreferences punchToAdd = getSharedPreferences("punchToAdd", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = punchToAdd.edit();
                editor.putString("name", name);
                editor.putString("id", id);
                editor.putString("reason", reason);
                editor.putBoolean("toAdd", true);
                editor.apply();

                toast.setText("添加成功");
                toast.show();
                changeButtonState(false);

                allowBackPress = false;
                //返回上一页
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        allowBackPress = true;
                        onBackPressed();
                    }
                },800);
                break;

            case R.id.fill_button_in_reason_activity:
                nameTextView.setText("测试人员");
                idTextView.setText("330283198811240134");
                reasonTextView.setText("添加原因");
                break;
            case R.id.id_check_button_in_reason_activity:
                idCheckON = !idCheckON;
                SharedPreferences.Editor userEditor = user.edit();
                userEditor.putBoolean("idCheck", idCheckON);
                userEditor.apply();
                idCheckButton.setText(idCheckON? "ID CHECK: ON" : "ID CHECK: OFF");
                toast.setText(idCheckON? "ID CHECK: ON" : "ID CHECK: OFF");
                toast.show();

        }
    }

    private void changeButtonState(Boolean state) {
        addButton.setEnabled(state);
//        addButton.setBackgroundColor(state ? 0xFF3562BD : 0x552884EF);
    }

    private TextWatcher textChangeWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            name = nameTextView.getText().toString();
            id = idTextView.getText().toString();
            reason = reasonTextView.getText().toString();

            if (name.isEmpty() || id.isEmpty() || reason.isEmpty()) {
                changeButtonState(false);
            } else {
                changeButtonState(true);
            }

        }
    };

}
