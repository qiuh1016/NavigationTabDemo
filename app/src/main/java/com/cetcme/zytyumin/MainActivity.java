package com.cetcme.zytyumin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends FragmentActivity {

    private NoScrollViewPager mViewPager;
    private IconTabPageIndicator mIndicator;

    private String TAG = "MainActivity";

    private MyShipDataReceiver myShipDataReceiver;
    private MyTodoNumbersReceiver myTodoNumbersReceiver;

    private SharedPreferences user;

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

        user = getSharedPreferences("user", Context.MODE_PRIVATE);

        initViews();
        initBroadcast();
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
        SharedPreferences.Editor editor = user.edit();//获取编辑器
        editor.putBoolean("hasLogin", false);
        editor.apply();//提交修改
    }

    public void onResume() {
        super.onResume();
        getTodoCount(user.getString("sessionKey", ""), user.getString("username", ""));

    }

    private void startLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
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
                        int Check_Drawing_Examine_Opinion_Count = response.getInt("Check_Drawing_Examine_Opinion_Count");
                        int Check_Detect_Info_Detail_Inspection_Count = response.getInt("Check_Detect_Info_Detail_Inspection_Count");
                        int Check_Detect_Info_Opinion_Count = response.getInt("Check_Detect_Info_Opinion_Count");

                        todoNumbers = new int[] {
                                Check_Drawing_Examine_Opinion_Count,
                                Check_Detect_Info_Detail_Inspection_Count,
                                Check_Detect_Info_Opinion_Count,
                                0
                        };
//
//                        todoNumbers[0] = Check_Drawing_Examine_Opinion_Count;
//                        todoNumbers[1] = Check_Detect_Info_Detail_Inspection_Count;
//                        todoNumbers[2] = Check_Detect_Info_Opinion_Count;

                    } else if (code == 2) {
                        Toast.makeText(getApplicationContext(), "登陆信息过期，请重新登陆",Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = user.edit();//获取编辑器
                        editor.putBoolean("hasLogin", false);
                        editor.apply();//提交修改
                        startLoginActivity();
                        Log.i(TAG, "onSuccess: 登陆信息过期");
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

}
