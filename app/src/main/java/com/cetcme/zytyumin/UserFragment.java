package com.cetcme.zytyumin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import IconPager.BaseFragment;
import MyClass.NavigationView;

/**
 * Created by qiuhong on 8/24/16.
 */
public class UserFragment extends BaseFragment {

    private View view;
    private String TAG = "UserFragment";
    private NavigationView navigationView;
    private int todoNumber = 3;

    private Button loginButton;
    private boolean hasLogin;

    private KProgressHUD kProgressHUD;
    private KProgressHUD okHUD;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, null, false);
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
        todoNumberTextView.setText(String.valueOf(todoNumber));
        todoNumberTextView.setVisibility(todoNumber == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private void initLineClick() {
        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.line_1_in_fragment_user);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: setting1");
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
                Log.i(TAG, "onClick: setting1");
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
                Log.i(TAG, "onClick: setting1");
                Intent intent = new Intent(getActivity(), VersionActivity.class);
                MainActivity activity = (MainActivity) getActivity();
                startActivity(intent);
                activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });

//        LinearLayout linearLayout4 = (LinearLayout) view.findViewById(R.id.line_4_in_fragment_user);
//        linearLayout4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick: setting1");
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                MainActivity activity = (MainActivity) getActivity();
//                startActivity(intent);
//                activity.overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
////                activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
//            }
//        });
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

        final SharedPreferences user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        hasLogin = user.getBoolean("hasLogin", false);

        modifyLoginButton(hasLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hasLogin = user.getBoolean("hasLogin", false);

                if (hasLogin) {
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
                                }
                            },1000);
                        }
                    }, 3000);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    MainActivity activity = (MainActivity) getActivity();
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
                    activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                }


            }
        });

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
        hasLogin = user.getBoolean("hasLogin", false);
        modifyLoginButton(hasLogin);
    }
}
