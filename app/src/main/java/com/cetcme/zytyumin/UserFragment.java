package com.cetcme.zytyumin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import com.cetcme.zytyumin.IconPager.BaseFragment;
import com.cetcme.zytyumin.MyClass.NavigationView;

/**
 * Created by qiuhong on 8/24/16.
 */
public class UserFragment extends BaseFragment {

    private View view;
    private String TAG = "UserFragment";
    private NavigationView navigationView;
    private int todoNumber = 3;

    private Button loginButton;

    private KProgressHUD kProgressHUD;
    private KProgressHUD okHUD;

    private SharedPreferences user;

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
                Intent intent = new Intent(getActivity(), TodoActivity.class);
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
        modifyLoginButton(user.getBoolean("hasLogin", false));
        initTodoNumber();
    }

    private void logoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(android.R.drawable.ic_menu_myplaces);
        builder.setMessage("是否继续?");
        builder.setTitle("即将退出登录");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                kProgressHUD.show();
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
                }, 2000);
            }
        });
        builder.setNegativeButton("取消", null);

        /**
         * 设置自定义按钮
         */
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btnPositive = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
        btnNegative.setTextColor(getResources().getColor(R.color.main_color));
//        btnNegative.setTextSize(18);
        btnPositive.setTextColor(getResources().getColor(R.color.main_color));
//        btnPositive.setTextSize(18);

    }
}
