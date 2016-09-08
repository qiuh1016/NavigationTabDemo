package com.cetcme.rcldandroidZhejiang.xia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.jiakeinfo.ocean.dataParse.GetWebDataWithHttpGet;
//import com.jiakeinfo.ocean.dataParse.ParseJson;
//import com.jiakeinfo.ocean.db.DatabaseOperation;
//import com.jiakeinfo.ocean.util.Util;


import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.jecInfoHttp.GetWebDataWithHttpGet;
import com.cetcme.rcldandroidZhejiang.jecInfoHttp.ParseJson;
import com.cetcme.rcldandroidZhejiang.R;
import com.umeng.analytics.MobclickAgent;

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

public class DetailFishShipActivity extends Activity {

	private ArrayList<HashMap<String, Object>> mSeachResultArrayList = null;

	private LinearLayout mDetailInfolLayout = null;
	private LayoutInflater mInflater = null;
	private String mURL = null;
	private final String TAG = "DetailFishShipActivity";
	private ImageView mBack = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_fish_ship_layout_two);

        initNavigationView();


		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mDetailInfolLayout = (LinearLayout) findViewById(R.id.detail_fish_ship_info);


		mSeachResultArrayList = new ArrayList<HashMap<String, Object>>();

		String shipNumber = getIntent().getExtras().getString("shipNumber");
//		SHIP_BUSINESS_TYPE = getIntent().getExtras().getString(
//				"SHIP_BUSINESS_TYPE");
		new GetDetailShipInfoTask().execute(new String[] { shipNumber });



	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_detail_fish_ship);
        navigationView.setTitle("渔船详细信息");
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

//	private String SHIP_BUSINESS_TYPE = "";
	private Handler mUpdateUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			HashMap<String, String> map = (HashMap<String, String>) msg.obj;

			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String key = entry.getKey().toString();
				if ("null".equalsIgnoreCase(map.get(key).toString())) {
					map.put(key, "");
				}
				// System.out.println("key = " + entry.getKey().toString()
				// + "; value = " + entry.getValue().toString());
			}

			View view1 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view1.findViewById(R.id.key))
					.setText(getString(R.string.ship_name));
			((TextView) view1.findViewById(R.id.value)).setText(map.get(
					"SHIP_NAME").toString());
			mDetailInfolLayout.addView(view1);

			View view2 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view2.findViewById(R.id.key))
					.setText(getString(R.string.ship_no));
			((TextView) view2.findViewById(R.id.value)).setText(map.get(
					"SHIP_NO").toString());
			mDetailInfolLayout.addView(view2);

			View view5 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view5.findViewById(R.id.key)).setText("渔船建造完成日期");
			String mShipCompleteDate = map.get("SHIP_BUILD_COMP_DATE")
					.toString();
			String regEx = "[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(mShipCompleteDate);
			Long time = 0L;
			Date date = null;
			time = new Long(m.replaceAll("").trim());
			if (time < 10000000000L) {
				time = time * 1000;
			}
			String dateNow = new SimpleDateFormat("yyyy/MM/dd").format(time);
			((TextView) view5.findViewById(R.id.value)).setText(dateNow);

			mDetailInfolLayout.addView(view5);

			View view105 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view105.findViewById(R.id.key))
					.setText(getString(R.string.ship_age));
			String mShipCompleteDate1 = map.get("SHIP_BUILD_COMP_DATE")
					.toString();
			Long mShipCompleteDateLong1 = new Long(mShipCompleteDate1);
			if (mShipCompleteDateLong1 < 10000000000L) {
				mShipCompleteDateLong1 = mShipCompleteDateLong1 * 1000;
			}
			Date date1 = new Date(mShipCompleteDateLong1);
			Date dateNow1 = new Date();
			long day = (dateNow1.getTime() - date1.getTime())
					/ (24 * 60 * 60 * 1000) + 1;
			String year = new java.text.DecimalFormat("#0").format(day / 365f);
			((TextView) view105.findViewById(R.id.value)).setText(year + "");
			mDetailInfolLayout.addView(view105);

			View view3 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view3.findViewById(R.id.key))
					.setText(getString(R.string.ship_port));
			((TextView) view3.findViewById(R.id.value)).setText(map.get(
					"SHIP_PORT").toString());
			mDetailInfolLayout.addView(view3);

			View view4 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view4.findViewById(R.id.key))
					.setText(getString(R.string.dist_ship_district));
			((TextView) view4.findViewById(R.id.value)).setText(map.get(
					"SHIP_DISTRICT").toString());
			mDetailInfolLayout.addView(view4);

			View view102 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view102.findViewById(R.id.key))
					.setText(getString(R.string.ship_business_type));
			((TextView) view102.findViewById(R.id.value)).setText(map.get(
					"SHIP_BUSINESS_TYPE").toString());
			mDetailInfolLayout.addView(view102);

			View view6 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view6.findViewById(R.id.key))
					.setText(getString(R.string.ship_dict_job_type));
			((TextView) view6.findViewById(R.id.value)).setText(map.get(
					"JOB_TYPE").toString());
			mDetailInfolLayout.addView(view6);

			View view7 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view7.findViewById(R.id.key))
					.setText(getString(R.string.ship_length));
			((TextView) view7.findViewById(R.id.value)).setText(map.get(
					"SHIP_LENGTH").toString());
			mDetailInfolLayout.addView(view7);

			View view8 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view8.findViewById(R.id.key))
					.setText(getString(R.string.ship_width));
			((TextView) view8.findViewById(R.id.value)).setText(map.get(
					"SHIP_WIDTH").toString());
			mDetailInfolLayout.addView(view8);

			View view9 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view9.findViewById(R.id.key))
					.setText(getString(R.string.ship_deep));
			((TextView) view9.findViewById(R.id.value)).setText(map.get(
					"SHIP_DEEP").toString());
			mDetailInfolLayout.addView(view9);

			View view10 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view10.findViewById(R.id.key))
					.setText(getString(R.string.ship_tot_power));
			((TextView) view10.findViewById(R.id.value)).setText(map.get(
					"SHIP_TOT_POWER").toString());
			mDetailInfolLayout.addView(view10);

			View view101 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view101.findViewById(R.id.key))
					.setText(getString(R.string.ship_tot_ton));
			((TextView) view101.findViewById(R.id.value)).setText(map.get(
					"SHIP_TOT_TON").toString());
			mDetailInfolLayout.addView(view101);

			View view11 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view11.findViewById(R.id.key))
					.setText(getString(R.string.dict_ship_material));
			((TextView) view11.findViewById(R.id.value)).setText(map.get(
					"APP_SHIP_MATERIAL").toString());
			mDetailInfolLayout.addView(view11);

			View shipOwnerInfoTitle = mInflater.inflate(R.layout.detail_title,
					null);
			((TextView) shipOwnerInfoTitle.findViewById(R.id.title))
					.setText(getString(R.string.ship_owner_info));
			mDetailInfolLayout.addView(shipOwnerInfoTitle);

			View view14 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view14.findViewById(R.id.key))
					.setText(getString(R.string.job_place));
			((TextView) view14.findViewById(R.id.value)).setText(map.get(
					"JOB_PLACE").toString());
			mDetailInfolLayout.addView(view14);

			View view15 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view15.findViewById(R.id.key))
					.setText(getString(R.string.owner_name));
			((TextView) view15.findViewById(R.id.value)).setText(map.get(
					"OWNER_NAME").toString());
			mDetailInfolLayout.addView(view15);

			View view16 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view16.findViewById(R.id.key))
					.setText(getString(R.string.owner_no));
			((TextView) view16.findViewById(R.id.value)).setText(map.get(
					"OWNER_NO").toString());
			mDetailInfolLayout.addView(view16);

			View view17 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view17.findViewById(R.id.key))
					.setText(getString(R.string.owner_addr));
			((TextView) view17.findViewById(R.id.value)).setText(map.get(
					"OWNER_ADDR").toString());
			mDetailInfolLayout.addView(view17);

			View view18 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view18.findViewById(R.id.key))
					.setText(getString(R.string.owner_tel));
			((TextView) view18.findViewById(R.id.value)).setText(map.get(
					"OWNER_TEL").toString());
			mDetailInfolLayout.addView(view18);

			View certInfoTitle = mInflater.inflate(R.layout.detail_title, null);
			((TextView) certInfoTitle.findViewById(R.id.title))
					.setText(getString(R.string.cert_info_title));
			mDetailInfolLayout.addView(certInfoTitle);

			View view12 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view12.findViewById(R.id.key))
					.setText(getString(R.string.owner_cert_no));
			((TextView) view12.findViewById(R.id.value)).setText(map.get(
					"OWNER_CERT_NO").toString());
			mDetailInfolLayout.addView(view12);

			View view13 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view13.findViewById(R.id.key))
					.setText(getString(R.string.owner_cert_get_date));
			String temp = map.get("OWNER_CERT_GET_DATE").toString();
			((TextView) view13.findViewById(R.id.value))
					.setText(getDateFromTime(temp.substring(
							temp.indexOf("(") + 1, temp.indexOf("+"))));
			mDetailInfolLayout.addView(view13);

			// View view19 = mInflater.inflate(R.layout.detail_info_item, null);
			// ((TextView) view19.findViewById(R.id.key))
			// .setText(getString(R.string.fishing_permit_period_date));
			// temp = map.get("FISHING_PERMIT_LOGOUT_TIME").toString();
			// ((TextView) view19.findViewById(R.id.value))
			// .setText(getDateFromTime(temp.substring(temp.indexOf("(-") + 1,
			// temp.indexOf("+"))));
			// mDetailInfolLayout.addView(view19);

			View view20 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view20.findViewById(R.id.key))
					.setText(getString(R.string.nationality_cert_no));
			((TextView) view20.findViewById(R.id.value)).setText(map.get(
					"NATIONALITY_CERT_NO").toString());
			mDetailInfolLayout.addView(view20);

			View view21 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view21.findViewById(R.id.key))
					.setText(getString(R.string.nationality_cert_period_date));
			temp = map.get("NATIONALITY_CERT_PERIOD_DATE").toString();
			((TextView) view21.findViewById(R.id.value))
					.setText(getDateFromTime(temp.substring(
							temp.indexOf("(-") + 1, temp.indexOf("+"))));
			mDetailInfolLayout.addView(view21);

			View view22 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view22.findViewById(R.id.key))
					.setText(getString(R.string.check_cert_no));
			((TextView) view22.findViewById(R.id.value)).setText(map.get(
					"CHECK_CERT_NO").toString());
			mDetailInfolLayout.addView(view22);

			View view23 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view23.findViewById(R.id.key))
					.setText(getString(R.string.check_dept));
			((TextView) view23.findViewById(R.id.value)).setText(map.get(
					"CHECK_DEPT").toString());
			mDetailInfolLayout.addView(view23);

			View view24 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view24.findViewById(R.id.key))
					.setText(getString(R.string.check_cert_get_date));
			temp = map.get("CHECK_CERT_DATE").toString();
			((TextView) view24.findViewById(R.id.value))
					.setText(getDateFromTime(temp.substring(
							temp.indexOf("(") + 1, temp.indexOf("+"))));
			mDetailInfolLayout.addView(view24);

			View view25 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view25.findViewById(R.id.key))
					.setText(getString(R.string.check_cert_period_date));
			temp = map.get("CHECK_CERT_PERIOD_DATE").toString();
			((TextView) view25.findViewById(R.id.value))
					.setText(getDateFromTime(temp.substring(
							temp.indexOf("(") + 1, temp.indexOf("+"))));
			mDetailInfolLayout.addView(view25);

			View view26 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view26.findViewById(R.id.key))
					.setText(getString(R.string.fishing_permit_cert_no));
			((TextView) view26.findViewById(R.id.value)).setText(map.get(
					"FISHING_PERMIT_NO").toString());
			mDetailInfolLayout.addView(view26);

			View view27 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view27.findViewById(R.id.key))
					.setText(getString(R.string.fishing_permit_cert_owner));
			((TextView) view27.findViewById(R.id.value)).setText(map.get(
					"FISHING_PERMIT_CERT_OWNER").toString());
			mDetailInfolLayout.addView(view27);

			View view31 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view31.findViewById(R.id.key))
					.setText(getString(R.string.finshing_permit_signate_dept));
			((TextView) view31.findViewById(R.id.value)).setText(map.get(
					"FISHING_PERMIT_SIGNATE_DEPT").toString());
			mDetailInfolLayout.addView(view31);

			View view28 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view28.findViewById(R.id.key))
					.setText(getString(R.string.finshing_permit_signate_date));
			temp = map.get("FISHING_PERMIT_SIGNATE_TIME").toString();
			((TextView) view28.findViewById(R.id.value))
					.setText(getDateFromTime(temp.substring(
							temp.indexOf("(") + 1, temp.indexOf("+"))));
			mDetailInfolLayout.addView(view28);

			View view29 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view29.findViewById(R.id.key))
					.setText(getString(R.string.finshing_permit_signate_logout_date));
			temp = map.get("FISHING_PERMIT_LOGOUT_TIME").toString();
			((TextView) view29.findViewById(R.id.value))
					.setText(getDateFromTime(temp.substring(
							temp.indexOf("(-") + 1, temp.indexOf("+"))));
			mDetailInfolLayout.addView(view29);

			View view30 = mInflater.inflate(R.layout.detail_info_item, null);
			((TextView) view30.findViewById(R.id.key))
					.setText(getString(R.string.fishing_gear_name));
			((TextView) view30.findViewById(R.id.value)).setText(map.get(
					"FISHING_GEAR_NAME").toString());
			mDetailInfolLayout.addView(view30);
			super.handleMessage(msg);
		}

	};

	private HashMap<String, String> mDetailShipInfohaHashMap = new HashMap<String, String>();

	class GetDetailShipInfoTask extends AsyncTask<String, Void, String> {

		String mResult = null;
		Boolean isLoginOk = false;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			mURL = "http://61.164.218.155:5000"
					+ "/bpm/YZSoft/Webservice/AppWebservice.asmx/GetShipInfoByNo?";
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			mURL = mURL + "shipNo=" + params[0];
			Log.d(TAG, "URL = " + mURL + ";");
			mResult = new GetWebDataWithHttpGet().executeGet(mURL);

			Log.d("Parker", "get result:" + mResult + "!!");
			if (null != mResult && mResult.length() > 0) {

				ParseJson parseJson = new ParseJson();
				mDetailShipInfohaHashMap.clear();
				mDetailShipInfohaHashMap = parseJson.getShipdetailInfo(mResult);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			if (null != mDetailShipInfohaHashMap
					&& mDetailShipInfohaHashMap.size() > 0) {
				Log.d(TAG, "get edtail ship data ok!");
				Message msg = new Message();
				msg.what = 0x00f0;
				msg.obj = mDetailShipInfohaHashMap;
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
