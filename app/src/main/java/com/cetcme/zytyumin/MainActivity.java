package com.cetcme.zytyumin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

import com.cetcme.zytyumin.IconPager.BaseFragment;
import com.cetcme.zytyumin.IconPager.IconPagerAdapter;
import com.cetcme.zytyumin.IconPager.IconTabPageIndicator;
import com.cetcme.zytyumin.IconPager.NoScrollViewPager;
import com.cetcme.zytyumin.MyClass.Ship;


public class MainActivity extends FragmentActivity {

    private NoScrollViewPager mViewPager;
    private IconTabPageIndicator mIndicator;

    private String TAG = "MainActivity";

    private MyShipDataReceiver myShipDataReceiver;
    private MyTodoNumbersReceiver myTodoNumbersReceiver;

    //按2次返回退出
    private boolean hasPressedBackOnce = false;
    //back toast
    private Toast backToast;

    private List<Ship> ships = new ArrayList<>();

    private int[] todoNumbers = {0, 0, 0, 0};

    public List<Ship> getShips() {
        return this.ships;
    }

    public int[] getTodoNumbers() {
        return this.todoNumbers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initViews();
        initBroadcast();

//        ships.add(new Ship("浙三渔04529", "3303811998090003", 30,122, false));
//        ships.add(new Ship("浙象渔84006", "3303812001050005", 31,121, false));
//        ships.add(new Ship("浙象渔10035", "3302251998010002", 30.5,120.5, false));
//        ships.add(new Ship("浙象渔10035", "3302251998010002", 32.5,122, false));
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

    public class MyShipDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {

            Log.i(TAG, "onReceive: get ship data");
            Bundle bundle = arg1.getExtras();
            ships = (List<Ship>) bundle.getSerializable("ships");

        }
    }
    public class MyTodoNumbersReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {

            Log.i(TAG, "onReceive: get todo numbers");
            Bundle bundle = arg1.getExtras();
            todoNumbers = (int[]) bundle.getSerializable("todoNumbers");

        }
    }

    private void initBroadcast() {
        myShipDataReceiver = new MyShipDataReceiver();
        myTodoNumbersReceiver = new MyTodoNumbersReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.shipData");
        filter.addAction("com.todoNumbers");
        registerReceiver(myShipDataReceiver, filter);
        registerReceiver(myTodoNumbersReceiver,filter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myShipDataReceiver);
        unregisterReceiver(myTodoNumbersReceiver);
    }

}
