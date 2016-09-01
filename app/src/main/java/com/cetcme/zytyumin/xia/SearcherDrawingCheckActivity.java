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

public class SearcherDrawingCheckActivity extends Activity {

	private final String TAG = "SearcherDrawingCheckActivity";
	private ArrayList<HashMap<String, String>> mDrawingCheckList = null;
	private ListView mListView = null;
	private SimpleAdapter mAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_drawing_check_layout);
		initView();
		initNavigationView();
	}

	public void onBackPressed() {
		super.onBackPressed();
	}

	private NavigationView navigationView;

	private void initNavigationView() {
		navigationView = (NavigationView) findViewById(R.id.nav_main_in_search_drawing_check);
		navigationView.setTitle(getString(R.string.line_5_in_record_activity));
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

		mListView = (ListView) findViewById(R.id.drawing_check_lv);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				HashMap<String, String> map = mDrawingCheckList.get(position);
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						DetailDrawingCheckInfoActivity.class);
				intent.putExtra("id", (String) map.get("id"));

				Log.d(TAG, "id=" + (String) map.get("id"));
				startActivity(intent);
			}
		});

		findViewById(R.id.drawing_check_seacher_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String shipName = ((EditText) findViewById(R.id.drawing_check_ship_name_et))
								.getText().toString();

						Log.d(TAG, "input ship name is" + shipName);
						if (null != shipName && shipName.length() > 0) {

							ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
							for (int i = 0; i < resultList.size(); i++) {

								if (shipName.equalsIgnoreCase(resultList.get(i)
										.get("ship_name"))) {
									list.add(resultList.get(i));
								}
							}

							mDrawingCheckList.clear();
							mDrawingCheckList.addAll(list);
							mAdapter.notifyDataSetChanged();
						} else {
							Toast.makeText(SearcherDrawingCheckActivity.this,
									"渔船名字输入有误！", Toast.LENGTH_SHORT).show();
						}
					}
				});

		mDrawingCheckList = new ArrayList<HashMap<String, String>>();

		mAdapter = new SimpleAdapter(this, mDrawingCheckList,
				R.layout.ship_detect_listview_item, new String[] { "drawing_num",
						"app_name", "app_date" }, new int[] { R.id.ship_name,
						R.id.app_name, R.id.app_date });
		mListView.setAdapter(mAdapter);

		new GetShipDetectRecordInfoTask()
				.execute(new String[] { "mm147258369" });
	}

	private ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

	class GetShipDetectRecordInfoTask extends AsyncTask<String, Void, String> {

		String mResult = null;
		String URL = "http://61.164.218.155:5000/bpm/YZSoft/API/BoatCheckProcess.asmx/GetDrawingCheckRecordByApplyAccount";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			URL = URL + "?app_account=" + params[0];
			Log.d(TAG, "" + URL);

			mResult = new GetWebDataWithHttpGet().executeGet(URL);

			Log.d(TAG, "result=" + mResult);

			if (null != mResult && mResult.length() > 0) {

				ParseJson parseJson = new ParseJson();

				resultList.clear();
				resultList = parseJson.getDrawingCheckRecord(mResult);
			} else {
				resultList.clear();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			mDrawingCheckList.clear();
			if (resultList.size() > 0) {

				mDrawingCheckList.addAll(resultList);
			} else {
				Toast.makeText(SearcherDrawingCheckActivity.this, "该账号名下目前没有渔船检验记录！",
						Toast.LENGTH_SHORT).show();
			}

			System.out.println("===" + mDrawingCheckList.size());
			mAdapter.notifyDataSetChanged();
		}
	}
}
