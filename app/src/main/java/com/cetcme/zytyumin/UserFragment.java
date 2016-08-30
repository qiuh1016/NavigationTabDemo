package com.cetcme.zytyumin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, null, false);
        initNavigationView();
        initTodoNumber();
        initLineClick();

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

        LinearLayout linearLayout4 = (LinearLayout) view.findViewById(R.id.line_4_in_fragment_user);
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: setting1");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                MainActivity activity = (MainActivity) getActivity();
                startActivity(intent);
                activity.overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
//                activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });
    }


}
