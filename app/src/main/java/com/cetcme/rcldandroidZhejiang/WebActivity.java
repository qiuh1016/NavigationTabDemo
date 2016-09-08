package com.cetcme.rcldandroidZhejiang;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.MyClass.ProgressWebView;
import com.umeng.analytics.MobclickAgent;

public class WebActivity extends Activity {

    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initData();
        initNavigationView();
        initWebView();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_web_activity);
        navigationView.setTitle(title);
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

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        url = bundle.getString("url");
        title = bundle.getString("title");
    }

    private void initWebView() {
        ProgressWebView webView = (ProgressWebView) findViewById(R.id.progressWebView_in_web_activity);
//        webView.loadUrl("file:///android_asset/www/terms.html");

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        webView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        webView.getSettings().setDomStorageEnabled(true);//DOM Storage

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.push_up_out_no_alpha);
    }
}
