package com.cetcme.navigationtabdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import navigationView.NavigationView;

/**
 * Created by qiuhong on 8/24/16.
 */
public class FragmentPage2 extends BaseFragment{

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, null, false);
        initNavigationView();
        return view;
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) view.findViewById(R.id.nav_main_in_fragment_2);
        navigationView.setTitle("地图");
//        navigationView.setTitleTextColor(Color.WHITE);
        navigationView.setBackView(0);
        navigationView.setRightView(R.drawable.icon_square_nor);
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
}
