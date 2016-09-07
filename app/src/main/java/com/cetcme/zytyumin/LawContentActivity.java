package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class LawContentActivity extends Activity {

    private String title;
    private int id;
    private String TAG = "LawContentActivity";

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> dataList = new ArrayList<>();

    private TextView titleTextView;
    private TextView contentTextView;
    private ScrollView contentScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_content);

        title = getIntent().getExtras().getString("title");
        id = getIntent().getExtras().getInt("id");

        initUI();
        initNavigationView();
        initListView();

    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initUI() {
        titleTextView = (TextView) findViewById(R.id.titleTextView_in_law_content_activity);
        contentTextView = (TextView) findViewById(R.id.contentTextView_in_law_content_activity);
        contentScrollView = (ScrollView) findViewById(R.id.contentScrollView_in_law_content_activity);

        titleTextView.setText(title);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_law_content_activity);
        navigationView.setTitle("法律法规详情");
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

    private void initListView() {
        listView = (ListView) findViewById(R.id.listView_in_law_content_activity);
        simpleAdapter = new SimpleAdapter(this, getDataList(),
                R.layout.law_chapter_list_cell,
                new String[]{"chapter", "name"},
                new int[]{
                        R.id.chapter_in_law_chapter_List_Cell,
                        R.id.name_in_law_chapter_List_Cell
                });
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " + position);

                getContent((int) dataList.get(position).get("id"));

            }
        });
    }

    private List<Map<String, Object>> getDataList() {

        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        String sessionKey = user.getString("sessionKey", "");

        //设置输入参数
        RequestParams params = new RequestParams();
        params.put("sessionKey", sessionKey);
        params.put("id", id);

        String url = getString(R.string.serverIP)+ getString(R.string.getLawChapterByLawIdUrl);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler("UTF-8"){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {

                    Log.i(TAG, "onSuccess: " + response.toString());
                    int code = response.getInt("Code");
                    String msg = response.getString("Msg");

                    /**
                     * 获取成功
                     */
                    if (code == 0) {

                        JSONArray list = response.getJSONArray("List");
                        if (list.length() == 0) {
                            Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject object = list.getJSONObject(i);
                            String chapter = object.getString("charpter");
                            String name = object.getString("name");
                            int id = object.getInt("id");

                            Map<String, Object> map = new Hashtable<>();
                            map.put("chapter", chapter);
                            map.put("name", name);
                            map.put("id", id);

                            dataList.add(map);
                        }

                        simpleAdapter.notifyDataSetChanged();
                        getContent((int) dataList.get(0).get("id"));


                    } else {
                        /**
                         * 获取失败
                         */
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    /**
                     * json解析失败
                     */
                    e.printStackTrace();
                    Log.i(TAG, "onResponse: json解析错误");
                    Toast.makeText(getApplicationContext(), "解析错误", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.i(TAG, "onResponse: network error");
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }

        });

        return dataList;
    }

    private void getContent(int id) {
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        String sessionKey = user.getString("sessionKey", "");

        //设置输入参数
        RequestParams params = new RequestParams();
        params.put("sessionKey", sessionKey);
        params.put("id", id);

        String url = getString(R.string.serverIP)+ getString(R.string.getLawClauseByChapterIdUrl);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler("UTF-8"){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {

                    Log.i(TAG, "onSuccess: " + response.toString());
                    int code = response.getInt("Code");
                    String msg = response.getString("Msg");

                    /**
                     * 获取成功
                     */
                    if (code == 0) {

                        JSONArray list = response.getJSONArray("List");
                        if (list.length() == 0) {
                            Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String content = "";

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject object = list.getJSONObject(i);
                            String clause = object.getString("clause");
                            String law_content = object.getString("law_content");

                            content += clause + "\n";
                            content += law_content + "\n\n";
                        }

                        contentTextView.setText(content);
                        contentScrollView.smoothScrollTo(0,0);

                    } else {
                        /**
                         * 获取失败
                         */
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    /**
                     * json解析失败
                     */
                    e.printStackTrace();
                    Log.i(TAG, "onResponse: json解析错误");
                    Toast.makeText(getApplicationContext(), "解析错误", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.i(TAG, "onResponse: network error");
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
