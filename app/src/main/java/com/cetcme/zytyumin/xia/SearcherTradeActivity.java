package com.cetcme.zytyumin.xia;

import java.util.ArrayList;
import java.util.HashMap;

import com.cetcme.zytyumin.R;
import com.cetcme.zytyumin.Http.GetWebDataWithHttpGet;
import com.cetcme.zytyumin.Http.ParseJson;
import com.cetcme.zytyumin.MyClass.NavigationView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SearcherTradeActivity extends Activity {

	private final String TAG = "SearcherTradeActivity";
	private ArrayList<HashMap<String, String>> mTradeList = null;
	private ListView mListView = null;
	private SimpleAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_trade_layout);
		initView();
		initNavigationView();
	}

	public void onBackPressed() {
		super.onBackPressed();
	}

	private NavigationView navigationView;

	private void initNavigationView() {
		navigationView = (NavigationView) findViewById(R.id.nav_main_in_search_trade);
		navigationView.setTitle(getString(R.string.line_3_in_record_activity));
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

	public void initView() {

		mListView = (ListView) findViewById(R.id.trade_lv);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				HashMap<String, String> map = mTradeList.get(position);
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						DetailTradingInfoActivity.class);
				intent.putExtra("id", (String) map.get("id"));

				Log.d(TAG, "id=" + (String) map.get("id"));
				startActivity(intent);
			}
		});

		findViewById(R.id.trade_seacher_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String shipName = ((EditText) findViewById(R.id.trade_ship_name_et))
								.getText().toString();

						Log.d(TAG, "input ship name is" + shipName);
						Log.d(TAG, "input ship name is" + shipName);
						if (null != shipName && shipName.length() > 0) {

							ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
							for (int i = 0; i < resultList.size(); i++) {

								if (shipName.equalsIgnoreCase(resultList.get(i)
										.get("c_boat_name"))) {
									list.add(resultList.get(i));
								}
							}

							mTradeList.clear();
							if (list.size() > 0) {

								mTradeList.addAll(list);
							} else {
								Toast.makeText(SearcherTradeActivity.this,
										"没有相匹配的记录！", Toast.LENGTH_SHORT).show();
							}
							mAdapter.notifyDataSetChanged();
						} else {
							Toast.makeText(SearcherTradeActivity.this,
									"渔船名字输入有误！", Toast.LENGTH_SHORT).show();
						}
					}
				});

		mTradeList = new ArrayList<HashMap<String, String>>();

		mAdapter = new SimpleAdapter(this, mTradeList,
				R.layout.trade_listview_item, new String[] { "c_boat_name",
						"c_seller_name", "c_buyer_name", "t_signed_date" },
				new int[] { R.id.ship_name, R.id.start_date, R.id.dead_date,
						R.id.is_own });
		mListView.setAdapter(mAdapter);

		new GetPayInsuranceInfoTask()
				.execute(new String[] { "330327196301103258" });
	}

	private ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

	class GetPayInsuranceInfoTask extends AsyncTask<String, Void, String> {

		String mResult = null;
		String URL = "http://61.164.218.155:5000/bpm/YZSoft/Webservice/AppWebservice.asmx/GetTradingByOwnnerNo";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			// URL = URL + "?page=" + "1" + "&rowNum=" + "1000" + "&boatName="
			// + params[0] + "&prnNo=" + "null" + "&price=" + "null"
			// + "&dealMode=" + "null" + "&acceptDpt=" + "null"
			// + "&seller=" + "null" + "&buyer=" + "null";

			URL = URL + "?ownner_no=" + params[0];
			Log.d(TAG, "" + URL);

			mResult = new GetWebDataWithHttpGet().executeGet(URL);

			Log.d(TAG, "result=" + mResult);

			if (null != mResult && mResult.length() > 0) {

				ParseJson parseJson = new ParseJson();

				resultList.clear();
				resultList = parseJson.getbasicTradingInfo(mResult);
			} else {
				resultList.clear();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			mTradeList.clear();
			if (resultList.size() > 0) {

				mTradeList.addAll(resultList);
			} else {
				Toast.makeText(SearcherTradeActivity.this, "该账号名下目前没有交易渔船！",
						Toast.LENGTH_SHORT).show();
			}

			System.out.println("===" + mTradeList.size());
			mAdapter.notifyDataSetChanged();
		}
	}
}
