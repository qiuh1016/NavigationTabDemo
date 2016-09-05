package com.cetcme.zytyumin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.CustomDialog;
import com.kaopiz.kprogresshud.KProgressHUD;

import com.cetcme.zytyumin.IconPager.BaseFragment;
import com.cetcme.zytyumin.MyClass.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiuhong on 8/24/16.
 */
public class UserFragment extends BaseFragment {

    private View view;
    private String TAG = "UserFragment";
    private NavigationView navigationView;
    private int todoNumber = 0;

    private Button loginButton;

    private KProgressHUD kProgressHUD;
    private KProgressHUD okHUD;

    private SharedPreferences user;

    private int Check_Drawing_Examine_Opinion_Count = 0;
    private int Check_Detect_Info_Detail_Inspection_Count = 0;
    private int Check_Detect_Info_Opinion_Count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, null, false);

        user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        initNavigationView();
        initHud();
        initTodoNumber();
        initLineClick();
        initLoginButton();

        return view;
    }

    private void initNavigationView() {
        navigationView = (NavigationView) view.findViewById(R.id.nav_main_in_fragment_user);
        navigationView.setTitle("我的");
        navigationView.setBackView(0);
        navigationView.setRightView(0);
        navigationView.setClickCallback(new NavigationView.ClickCallback() {

            @Override
            public void onRightClick() {
                Log.i("main","点击了右侧按钮!");
            }

            @Override
            public void onBackClick() {
                Log.i("main","点击了左侧按钮!");
            }
        });
    }

    private void initTodoNumber() {
        TextView todoNumberTextView = (TextView) view.findViewById(R.id.todoNumber_in_fragment_user);
        /**
         * 如果没登录则不现实todo数量
         */
        if (!user.getBoolean("hasLogin", false)) {
            todoNumberTextView.setVisibility(View.INVISIBLE);
            return;
        }
        todoNumberTextView.setText(String.valueOf(todoNumber));
        todoNumberTextView.setVisibility(todoNumber == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private void initLineClick() {
        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.line_1_in_fragment_user);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getBoolean("hasLogin", false)) {
                    startLoginActivity();
                    return;
                }
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                MainActivity activity = (MainActivity) getActivity();
                startActivity(intent);
                activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });

        LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.line_2_in_fragment_user);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getBoolean("hasLogin", false)) {
                    startLoginActivity();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putInt("Check_Drawing_Examine_Opinion_Count", Check_Drawing_Examine_Opinion_Count);
                bundle.putInt("Check_Detect_Info_Detail_Inspection_Count", Check_Detect_Info_Detail_Inspection_Count);
                bundle.putInt("Check_Detect_Info_Opinion_Count", Check_Detect_Info_Opinion_Count);
                Intent intent = new Intent(getActivity(), TodoActivity.class);
                intent.putExtras(bundle);
                MainActivity activity = (MainActivity) getActivity();
                startActivity(intent);
                activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });

        LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.line_3_in_fragment_user);
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VersionActivity.class);
                MainActivity activity = (MainActivity) getActivity();
                startActivity(intent);
                activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });

    }

    private void initHud() {
        //hudView
        kProgressHUD = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("退出中")
                .setAnimationSpeed(1)
                .setDimAmount(0.3f)
                .setSize(110, 110)
                .setCancellable(false);
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(R.drawable.checkmark);
        okHUD  =  KProgressHUD.create(getActivity())
                .setCustomView(imageView)
                .setLabel("退出成功")
                .setCancellable(false)
                .setSize(110,110)
                .setDimAmount(0.3f);
    }

    private void initLoginButton() {
        loginButton = (Button) view.findViewById(R.id.button_in_fragment_user);
        modifyLoginButton(user.getBoolean("hasLogin", false));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getBoolean("hasLogin", false)) {
                    logoutDialog();
                } else {
                    startLoginActivity();
                }
            }
        });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        MainActivity activity = (MainActivity) getActivity();
        startActivity(intent);
        activity.overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
    }

    private void modifyLoginButton(boolean hasLogin) {
        if (hasLogin) {
            loginButton.setBackgroundResource(R.drawable.single_select_logout);
            loginButton.setText("退出");
        } else {
            loginButton.setBackgroundResource(R.drawable.single_select);
            loginButton.setText("登陆");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 重新设置登陆按钮
         */
        SharedPreferences user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean hasLogin = user.getBoolean("hasLogin", false);
        modifyLoginButton(hasLogin);

        initTodoNumber();
        if (hasLogin) {
            getTodoCount(user.getString("sessionKey", ""), user.getString("username", ""));
        }


    }

    private void logoutDialog() {

        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("即将退出，是否继续?");
        builder.setCancelable(false);
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: right");
                dialog.dismiss();
                logout();
                //设置你的操作事项
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "onClick: left");
                dialog.dismiss();
            }
        });

        builder.create().show();


//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setIcon(android.R.drawable.ic_menu_myplaces);
//        builder.setMessage("是否继续?");
//        builder.setTitle("即将退出登录");
//        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                logout();
//            }
//        });
//        builder.setNegativeButton("取消", null);
//
//        /**
//         * 设置自定义按钮
//         */
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//
//        Button btnPositive = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
//        Button btnNegative = alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
//        btnNegative.setTextColor(getResources().getColor(R.color.main_color));
//        btnPositive.setTextColor(getResources().getColor(R.color.main_color));

    }

    private void getTodoCount(String sessionKey, String account) {

        Log.i(TAG, "getTodoCount: to get todo count");
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
                        Check_Drawing_Examine_Opinion_Count = response.getInt("Check_Drawing_Examine_Opinion_Count");
                        Check_Detect_Info_Detail_Inspection_Count = response.getInt("Check_Detect_Info_Detail_Inspection_Count");
                        Check_Detect_Info_Opinion_Count = response.getInt("Check_Detect_Info_Opinion_Count");

                        todoNumber = Check_Drawing_Examine_Opinion_Count + Check_Detect_Info_Detail_Inspection_Count + Check_Detect_Info_Opinion_Count;
                        initTodoNumber();
                        int[] todoNumbers = {
                                Check_Drawing_Examine_Opinion_Count,
                                Check_Detect_Info_Detail_Inspection_Count,
                                Check_Detect_Info_Opinion_Count,
                                0
                        };
                        sendTodoNumberBroadcast(todoNumbers);
                    } else if (code == 2) {
                        Toast.makeText(getActivity(), "登陆信息过期，请重新登陆",Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = user.edit();//获取编辑器
                        editor.putBoolean("hasLogin", false);
                        editor.apply();//提交修改
                        startLoginActivity();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "onFailure: get todo network error");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "onFailure: get todo network error");
            }
        });

    }

    private void logout() {
        kProgressHUD.show();
        sendLoginFlagBroadcast(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                kProgressHUD.dismiss();
                okHUD.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        okHUD.dismiss();
                        modifyLoginButton(false);
                        SharedPreferences.Editor editor = user.edit();//获取编辑器
                        editor.putBoolean("hasLogin", false);
                        editor.apply();//提交修改
                        initTodoNumber();
                    }
                },1000);
            }
        }, 1000);
    }

    private void sendLoginFlagBroadcast(boolean login) {
        Intent intent = new Intent();
        intent.setAction("com.loginFlag");
        intent.putExtra("loginFlag" , login);
        getActivity().sendBroadcast(intent);
    }

    private void sendTodoNumberBroadcast(int[] todoNumbers) {
        Intent intent = new Intent();
        intent.setAction("com.todoNumbers");
        intent.putExtra("todoNumbers" , todoNumbers);
        getActivity().sendBroadcast(intent);
    }
}
