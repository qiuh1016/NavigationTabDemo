package com.cetcme.zytyumin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import IconPager.BaseFragment;
import MyClass.NavigationView;

/**
 * Created by qiuhong on 8/24/16.
 */
public class FragmentPage3 extends BaseFragment {

    private View view;
    private String TAG = "FragmentPage3";
    private NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_3, null, false);
        initNavigationView();

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.setting1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: setting1");
                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", "个性签名");
                intent.putExtras(bundle);
                MainActivity activity = (MainActivity) getActivity();
                startActivity(intent);
                activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });
        return view;
    }

    private void initNavigationView() {
        navigationView = (NavigationView) view.findViewById(R.id.nav_main_in_fragment_3);
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


}
