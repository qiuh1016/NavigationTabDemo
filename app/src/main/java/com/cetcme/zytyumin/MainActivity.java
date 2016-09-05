package com.cetcme.zytyumin;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.cetcme.zytyumin.IconPager.BaseFragment;
import com.cetcme.zytyumin.IconPager.IconPagerAdapter;
import com.cetcme.zytyumin.IconPager.IconTabPageIndicator;
import com.cetcme.zytyumin.IconPager.NoScrollViewPager;


public class MainActivity extends FragmentActivity {

    private NoScrollViewPager mViewPager;
    private IconTabPageIndicator mIndicator;

    //按2次返回退出
    private boolean hasPressedBackOnce = false;
    //back toast
    private Toast backToast;


    //TODO: 测试用的 需要更新数据  什么时候更新 什么时候更新map
    private String[] shipNames = {
            "浙三渔04529",
            "浙象渔84006",
            "浙象渔10035"};

    private String[] shipNumbers = {
            "3303811998090003",
            "3303812001050005",
            "3302251998010002"};

    private LatLng[] shipLocations = {
            new LatLng(30, 122),
            new LatLng(31, 121),
            new LatLng(32.5, 120.5)
    };

    public String[] getShipNames() {
        return this.shipNames;
    }

    public String[] getShipNumbers() {
        return this.shipNumbers;
    }

    public LatLng[] getShipLocations() {
        return this.shipLocations;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);
        mIndicator = (IconTabPageIndicator) findViewById(R.id.indicator);
        List<BaseFragment> fragments = initFragments();
        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
    }

    private List<BaseFragment> initFragments() {
        List<BaseFragment> fragments = new ArrayList<BaseFragment>();

        BaseFragment userFragment = new HomepageFragment();
        userFragment.setTitle("首页");
        userFragment.setIconId(R.drawable.tab_homepage_selector);
        fragments.add(userFragment);

        BaseFragment noteFragment = new MapFragment();
        noteFragment.setTitle("地图");
        noteFragment.setIconId(R.drawable.tab_map_selector);
        fragments.add(noteFragment);

        BaseFragment contactFragment = new UserFragment();
        contactFragment.setTitle("我的");
        contactFragment.setIconId(R.drawable.tab_user_selector);
        fragments.add(contactFragment);


        return fragments;
    }

    class FragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        private List<BaseFragment> mFragments;

        public FragmentAdapter(List<BaseFragment> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getIconResId(int index) {
            return mFragments.get(index).getIconId();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).getTitle();
        }
    }

    public void onBackPressed() {

        if (!hasPressedBackOnce) {
            backToast = Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT);
            backToast.show();
            hasPressedBackOnce = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hasPressedBackOnce = false;
                }
            },2500);
        } else {
            backToast.cancel();
            super.onBackPressed();
        }
    }

}
