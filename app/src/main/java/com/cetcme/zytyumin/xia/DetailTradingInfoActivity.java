package com.cetcme.zytyumin.xia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cetcme.zytyumin.R;
import com.cetcme.zytyumin.jecInfoHttp.GetWebDataWithHttpGet;
import com.cetcme.zytyumin.jecInfoHttp.ParseJson;
import com.cetcme.zytyumin.MyClass.NavigationView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailTradingInfoActivity extends Activity {

	private ArrayList<HashMap<String, Object>> mSeachResultArrayList = null;

	private LinearLayout mDetailInfolLayout = null;
	private LayoutInflater mInflater = null;
	private String mURL = null;
	private final String TAG = "DetailTradingInfoActivity";
	private ImageView mBack = null;
	private TextView mTitle = null;
	private String mSignedDate = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_info_layout);

		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mDetailInfolLayout = (LinearLayout) findViewById(R.id.detail_fish_ship_info);

		mSeachResultArrayList = new ArrayList<HashMap<String, Object>>();

		String shipDetectID = getIntent().getExtras().getString("id");
		mSignedDate = getIntent().getExtras().getString("t_signed_date");
		new GetTradingDetailInfoTask().execute(new String[] { shipDetectID });


		initNavigationView();
	}

	public void onBackPressed() {
		super.onBackPressed();
	}

	private NavigationView navigationView;

	private void initNavigationView() {
		navigationView = (NavigationView) findViewById(R.id.nav_main_in_detail_info_layout);
		navigationView.setTitle("交易查询详情");
		navigationView.setBackView(R.drawable.icon_back_button);
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

	private Handler mUpdateUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			HashMap<String, String> map = (HashMap<String, String>) msg.obj;
			String temp = "";

			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String key = entry.getKey().toString();
				if ("null".equalsIgnoreCase(map.get(key).toString())) {
					map.put(key, "");
				}
				System.out.println("key = " + entry.getKey().toString()
						+ "; value = " + entry.getValue().toString());
			}

			View view1 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view1.findViewById(R.id.key))
					.setText(getString(R.string.c_boat_name));
			((TextView) view1.findViewById(R.id.value)).setText(map.get(
					"c_boat_name").toString());
			mDetailInfolLayout.addView(view1);

			View view2 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view2.findViewById(R.id.key))
					.setText(getString(R.string.c_boat_cde));
			((TextView) view2.findViewById(R.id.value)).setText(map.get(
					"c_boat_num").toString());
			mDetailInfolLayout.addView(view2);

			View view3 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view3.findViewById(R.id.key))
					.setText(getString(R.string.c_prn_no));
			((TextView) view3.findViewById(R.id.value)).setText(map.get(
					"c_prn_no").toString());
			mDetailInfolLayout.addView(view3);

			View view6 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view6.findViewById(R.id.key))
					.setText(getString(R.string.c_accept_dpt_name));
			((TextView) view6.findViewById(R.id.value)).setText(map.get(
					"c_accept_dpt_name").toString());
			mDetailInfolLayout.addView(view6);

			View view5 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view5.findViewById(R.id.key))
					.setText(getString(R.string.c_delfalg));
			((TextView) view5.findViewById(R.id.value)).setText(map.get(
					"c_delfalg").toString());
			mDetailInfolLayout.addView(view5);

			View view4 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view4.findViewById(R.id.key))
					.setText(getString(R.string.n_transfer_price_lower));
			((TextView) view4.findViewById(R.id.value)).setText(map.get(
					"n_transfer_price_lower").toString());
			mDetailInfolLayout.addView(view4);

			View view7 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view7.findViewById(R.id.key))
					.setText(getString(R.string.n_22_jia_prop));
			((TextView) view7.findViewById(R.id.value)).setText(map.get(
					"n_22_jia_prop").toString());
			mDetailInfolLayout.addView(view7);

			View view10 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view10.findViewById(R.id.key))
					.setText(getString(R.string.c_22_deal_mode));
			((TextView) view10.findViewById(R.id.value)).setText(map.get(
					"c_22_deal_mode").toString());
			mDetailInfolLayout.addView(view10);

			View view14 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view14.findViewById(R.id.key))
					.setText(getString(R.string.c_signed_addr));
			((TextView) view14.findViewById(R.id.value)).setText(map.get(
					"c_signed_addr").toString());
			mDetailInfolLayout.addView(view14);

			System.out.println("====== " + map.get("t_signed_date"));
			View view20 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view20.findViewById(R.id.key)).setText("鉴定日期");
			((TextView) view20.findViewById(R.id.value)).setText(mSignedDate);
			mDetailInfolLayout.addView(view20);

			View view8 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view8.findViewById(R.id.key))
					.setText(getString(R.string.c_seller_name));
			((TextView) view8.findViewById(R.id.value)).setText(map.get(
					"c_seller_name").toString());
			mDetailInfolLayout.addView(view8);

			View view9 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view9.findViewById(R.id.key))
					.setText(getString(R.string.c_seller_card_holder));
			((TextView) view9.findViewById(R.id.value)).setText(map.get(
					"c_seller_card_holder").toString());
			mDetailInfolLayout.addView(view9);

			View view16 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view16.findViewById(R.id.key))
					.setText(getString(R.string.seller_link_name));
			((TextView) view16.findViewById(R.id.value)).setText(map.get(
					"seller_link_name").toString());
			mDetailInfolLayout.addView(view16);

			View view17 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view17.findViewById(R.id.key))
					.setText(getString(R.string.seller_link_tel));
			((TextView) view17.findViewById(R.id.value)).setText(map.get(
					"seller_link_tel").toString());
			mDetailInfolLayout.addView(view17);

			View view11 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view11.findViewById(R.id.key))
					.setText(getString(R.string.c_buyer_name));
			((TextView) view11.findViewById(R.id.value)).setText(map.get(
					"c_buyer_name").toString());
			mDetailInfolLayout.addView(view11);

			View view12 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view12.findViewById(R.id.key))
					.setText(getString(R.string.c_buyer_card_holder));
			((TextView) view12.findViewById(R.id.value)).setText(map.get(
					"c_buyer_card_holder").toString());
			mDetailInfolLayout.addView(view12);

			View view13 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view13.findViewById(R.id.key))
					.setText(getString(R.string.buyer_link_name));
			((TextView) view13.findViewById(R.id.value)).setText(map.get(
					"buyer_link_name").toString());
			mDetailInfolLayout.addView(view13);

			View view15 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view15.findViewById(R.id.key))
					.setText(getString(R.string.buyer_link_tel));
			((TextView) view15.findViewById(R.id.value)).setText(map.get(
					"buyer_link_tel").toString());
			mDetailInfolLayout.addView(view15);

			super.handleMessage(msg);
		}

	};

	private HashMap<String, String> mDetailTradingInfoHashMap = new HashMap<String, String>();

	class GetTradingDetailInfoTask extends AsyncTask<String, Void, String> {

		String mResult = null;
		Boolean isLoginOk = false;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			mURL = "http://61.164.218.155:5000/bpm/YZSoft/Webservice/AppWebservice.asmx/GetTradingDetail?";
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			mURL = mURL + "id=" + params[0];
			// Log.d(TAG, "URL = " + mURL + ";");
			mResult = new GetWebDataWithHttpGet().executeGet(mURL);

			// Log.d("Parker", "get result:" + mResult + "!!");
			if (null != mResult && mResult.length() > 0) {

				ParseJson parseJson = new ParseJson();
				mDetailTradingInfoHashMap.clear();
				mDetailTradingInfoHashMap = parseJson
						.getTradingdetailInfo(mResult);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			if (mDetailTradingInfoHashMap.size() > 0) {
				Log.d(TAG, "get edtail ship data ok!");
				Message msg = new Message();
				msg.what = 0x00f0;
				msg.obj = mDetailTradingInfoHashMap;
				mUpdateUIHandler.sendMessage(msg);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	@SuppressLint("UseValueOf")
	public String getDateFromTime(String t) {

		String regEx = "[^\\-?0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(t);
		Long time = 0L;
		Date date = null;
		try {
			time = new Long(m.replaceAll("").trim());
			date = new Date(time);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

}
