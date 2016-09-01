package com.cetcme.zytyumin.xia;

import java.util.ArrayList;
import java.util.HashMap;

import com.cetcme.zytyumin.R;
import Http.GetWebDataWithHttpGet;
import Http.ParseJson;
import MyClass.NavigationView;

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

public class SearcherClaimActivity extends Activity {

	private final String TAG = "SearcherClaimActivity";
	private ArrayList<HashMap<String, String>> mClaimList = null;
	private ListView mListView = null;
	private SimpleAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_claim_layout);
		initView();
		initNavigationView();
	}

	public void onBackPressed() {
		super.onBackPressed();
	}

	private NavigationView navigationView;

	private void initNavigationView() {
		navigationView = (NavigationView) findViewById(R.id.nav_main_in_search_claim_layout);
		navigationView.setTitle(getString(R.string.line_2_in_record_activity));
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

		mListView = (ListView) findViewById(R.id.claim_lv);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				HashMap<String, String> map = mClaimList.get(position);
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						DetailClaimInfoActivity.class);
				intent.putExtra("id", (String) map.get("id"));

				Log.d(TAG, "id=" + (String) map.get("id"));
				startActivity(intent);
			}
		});

		findViewById(R.id.claim_seacher_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String shipName = ((EditText) findViewById(R.id.claim_ship_name_et))
								.getText().toString();

						Log.d(TAG, "input ship name is" + shipName);
						if (null != shipName && shipName.length() > 0) {

							ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
							for (int i = 0; i < resultList.size(); i++) {

								if (shipName.equalsIgnoreCase(resultList.get(i)
										.get("c_boat_name"))) {
									list.add(resultList.get(i));
								}
							}

							mClaimList.clear();
							if (list.size() > 0) {

								mClaimList.addAll(list);
							} else {
								Toast.makeText(SearcherClaimActivity.this,
										"没有相匹配的记录！", Toast.LENGTH_SHORT).show();
							}
							mAdapter.notifyDataSetChanged();
						} else {
							Toast.makeText(SearcherClaimActivity.this,
									"渔船名字输入有误！", Toast.LENGTH_SHORT).show();
						}
					}
				});

		mClaimList = new ArrayList<HashMap<String, String>>();

		mAdapter = new SimpleAdapter(this, mClaimList,
				R.layout.claim_listview_item, new String[] { "c_boat_name",
						"n_sum_clm_amt", "t_clm_rgst_tm", "t_cls_tm" },
				new int[] { R.id.ship_name, R.id.start_date, R.id.dead_date,
						R.id.is_own });
		mListView.setAdapter(mAdapter);

		new GetClaimInfoTask().execute(new String[] { "330903196504260394" });
	}

	private ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

	class GetClaimInfoTask extends AsyncTask<String, Void, String> {

		String mResult = null;
		String URL = "http://61.164.218.155:5000/bpm/YZSoft/Webservice/AppWebservice.asmx/GetClmInfoByOwnnerNo";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

//			URL = URL + "?page=" + "1" + "&rowNum=" + "1000" + "&boatName="
//					+ params[0] + "&boatCode=" + "null" + "&clmNo=" + "null"
//					+ "&clmNum=" + "null" + "&manName=" + "null" + "&clmName="
//					+ "null" + "&clmAmt=" + "null" + "&clmStatus=" + "null"
//					+ "&start=" + "null" + "&end=" + "null";
			
			URL = URL + "?ownner_no=" + params[0];
			Log.d(TAG, "" + URL);

			mResult = new GetWebDataWithHttpGet().executeGet(URL);

			Log.d(TAG, "result=" + mResult);

			if (null != mResult && mResult.length() > 0) {

				ParseJson parseJson = new ParseJson();

				resultList.clear();
				resultList = parseJson.getbasicClmInfo(mResult);
			} else {
				resultList.clear();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			mClaimList.clear();

			if (resultList.size() > 0) {

				mClaimList.addAll(resultList);
			} else {
				Toast.makeText(SearcherClaimActivity.this, "该账号名下目前没有交易渔船！",
						Toast.LENGTH_SHORT).show();
			}

			mAdapter.notifyDataSetChanged();
		}
	}
}
