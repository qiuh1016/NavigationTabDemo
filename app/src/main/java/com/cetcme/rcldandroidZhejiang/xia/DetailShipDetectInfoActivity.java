package com.cetcme.rcldandroidZhejiang.xia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cetcme.rcldandroidZhejiang.R;

import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.MyClass.ProgressWebView;
import com.umeng.analytics.MobclickAgent;

public class DetailShipDetectInfoActivity extends Activity {

	private ProgressWebView mWebView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ship_detect_process_info);

		initView();
		initNavigationView();
		String id = getIntent().getStringExtra("id");
		mWebView.loadUrl("http://61.164.218.155:5008/WebReport/ReportServer?reportlet=apply%2Fboat_detect_2.cpt&op=h5&id="
				+ id);

	}

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in_no_alpha,
				R.anim.push_right_out_no_alpha);
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
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_ship_detect_process);
		navigationView.setTitle("船检记录详情");
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

	public void initView() {
		mWebView = (ProgressWebView) findViewById(R.id.web_view_in_in_ship_detect_process);

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setDomStorageEnabled(true);

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});

	}
}
