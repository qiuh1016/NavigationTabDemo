package com.cetcme.zytyumin;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import MyClass.FragmentAdapter;
import MyClass.NavigationView;

public class SignActivity extends FragmentActivity {

    /**
     * 顶部三个LinearLayout
     */
    private LinearLayout mTabChat;
    private LinearLayout mTabFound;
    private LinearLayout mTabContact;

    /**
     * 顶部的三个TextView
     */
    private TextView chat;
    private TextView found;
    private TextView contact;

    /**
     * Tab的那个引导线
     */
    private ImageView mTabLine;

    /**
     * 屏幕的宽度
     */
    private int screenWidth;

    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        res = getResources();
        initNavigationView();
        initViewPager();

    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_sign_activity);
        navigationView.setTitle("登陆");
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

    private void initViewPager() {

        initView();

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        /**
         * 初始化Adapter
         */
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());

        initTabLine();
    }


    /**
     * 根据屏幕的宽度，初始化引导线的宽度
     */
    private void initTabLine() {
        mTabLine = (ImageView) findViewById(R.id.id_tab_line);

        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        layoutParams.width = screenWidth / 3;//设置该控件的layoutParams参数
        mTabLine.setLayoutParams(layoutParams);//将修改好的layoutParams设置为该控件的layoutParams
    }

    /**
     * 初始化控件，初始化Fragment
     */
    private void initView() {
        chat=(TextView) findViewById(R.id.id_chat);
        found=(TextView) findViewById(R.id.id_found);
        contact=(TextView) findViewById(R.id.id_contact);

        chat.setOnClickListener(new TabOnClickListener(0));
        found.setOnClickListener(new TabOnClickListener(1));
        contact.setOnClickListener(new TabOnClickListener(2));

        fragments.add(new ChatMainTab01Fragment());
        fragments.add(new ChatMainTab02Fragment());
        fragments.add(new ChatMainTab02Fragment());

        mTabChat=(LinearLayout) findViewById(R.id.id_tab1_chat);
        mTabFound=(LinearLayout) findViewById(R.id.id_tab2_found);
        mTabContact=(LinearLayout) findViewById(R.id.id_tab3_contact);

        mTabChat.setOnClickListener(new TabOnClickListener(0));
        mTabFound.setOnClickListener(new TabOnClickListener(1));
        mTabContact.setOnClickListener(new TabOnClickListener(2));
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        chat.setTextColor(res.getColor(R.color.text_clo));
        found.setTextColor(res.getColor(R.color.text_clo));
        contact.setTextColor(res.getColor(R.color.text_clo));
    }

    /**
     * 功能：点击主页TAB事件
     */
    public class TabOnClickListener implements View.OnClickListener{
        private int index = 0;

        public TabOnClickListener(int i){
            index = i;
        }

        public void onClick(View v) {
            mViewPager.setCurrentItem(index);//选择某一页
        }

    }

    /**
     * 功能：Fragment页面改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
            LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
            //返回组件距离左侧组件的距离
            lp.leftMargin= (int) ((positionOffset+position)*screenWidth/3);
            mTabLine.setLayoutParams(lp);
        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            //重置所有TextView的字体颜色
            resetTextView();
            switch (position) {
                case 0:
                    chat.setTextColor(res.getColor(R.color.main_color));
                    break;
                case 1:
                    found.setTextColor(res.getColor(R.color.main_color));
                    break;
                case 2:
                    contact.setTextColor(res.getColor(R.color.main_color));
                    break;
            }
        }
    }

}



