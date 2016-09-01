package com.cetcme.zytyumin.rcld;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cetcme.zytyumin.MyClass.NavigationView;
import com.cetcme.zytyumin.R;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class iofSailorActivity extends Activity {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> dataList = new ArrayList<>();

    private String sailorListString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iof_sailor);

        /**
         * umeng 推送
         */
        PushAgent.getInstance(this).onAppStart();

        Bundle bundle = this.getIntent().getExtras();
        String iofTime = bundle.getString("iofTime");
        String iofFlag = bundle.getString("iofFlag");
        sailorListString = bundle.getString("iofSailorList");

        initNavigationView();
        initListView();


    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.iofSailorListView);
        simpleAdapter = new SimpleAdapter(iofSailorActivity.this, getioSailorData(), R.layout.punch_list_cell,
                new String[]{"sailorName", "sailorIdNo", "reason","dataType"},
                new int[]{
                        R.id.nameTextViewInPunchListCell,
                        R.id.idTextViewInPunchListCell,
                        R.id.punchTimeTextViewInPunchListView,
                        R.id.dataTypeTextViewInPunchListView
                });
        listView.setAdapter(simpleAdapter);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_ioSailor_activity);
        navigationView.setTitle("船员名单");
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private List<Map<String, Object>> getioSailorData() {
        Log.i("Main", sailorListString);

        try {
            JSONArray sailorList = new JSONArray(sailorListString);

            for (int i = 0; i < sailorList.length(); i++) {
                JSONObject sailor = sailorList.getJSONObject(i);
                Map<String, Object> map = new Hashtable<>();
                try{
                    map.put("sailorName", sailor.getString("sailorName"));
                }catch(JSONException e){
                    map.put("sailorName", "无");
                }
                try{
                    map.put("sailorIdNo", sailor.getString("sailorIdNo"));
                }catch(JSONException e){
                    map.put("sailorIdNo", "无");
                }
                try{
                    map.put("punchTime", sailor.getString("punchTime"));
                }catch(JSONException e){
                    map.put("punchTime", "无");
                }
                try{
                    int dataType = sailor.getInt("dataType");
                    if (dataType == 0) {
                        map.put("dataType", "自动生成");
                    } else if (dataType == 1) {
                        map.put("dataType", "手动添加");
                    } else if (dataType == 2) {
                        map.put("dataType", "手动删除");
                    } else {
                        map.put("dataType", "无");
                    }
                }catch(JSONException e){
                    map.put("dataType", "无");
                }
                try{
                    map.put("reason", sailor.getString("reason"));
                }catch(JSONException e){
                    map.put("reason", "无");
                }
                dataList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return dataList;
    }
}
