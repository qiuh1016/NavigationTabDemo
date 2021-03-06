package com.cetcme.rcldandroidZhejiang.rcld;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.R;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.fr.android.ui.ptrlibrary.PullToRefreshBase;
import com.fr.android.ui.ptrlibrary.PullToRefreshListView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

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


import static android.widget.Toast.LENGTH_SHORT;

public class PunchActivity extends Activity {


    private PullToRefreshListView listView;

    private List<Map<String, Object>> dataList = new ArrayList<>();
    private Toast toast;
    private SimpleAdapter simpleAdapter;

    private int sum; //总数
    private int totalPage;
    private int currentPage = 0;
    private Boolean isFirstTimeToGet = true;

    private KProgressHUD kProgressHUD;
    private int pageSize = 20;

    private String shipNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);

        Bundle bundle = this.getIntent().getExtras();
        shipNo = bundle.getString("shipNo");

        initNavigationView();

        /**
         * umeng 推送
         */
        PushAgent.getInstance(this).onAppStart();

        toast = Toast.makeText(PunchActivity.this, "", LENGTH_SHORT);

        initListView();

    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_punch_activity);
        navigationView.setTitle("打卡记录");
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

    private void initListView() {
        listView = (PullToRefreshListView) findViewById(R.id.punchHistoryListView);
        listView.setMode(PullToRefreshBase.Mode.BOTH);

        listView.getLoadingLayoutProxy(true,false).setRefreshingLabel("刷新中");
        listView.getLoadingLayoutProxy(true,false).setReleaseLabel("松开立即刷新");
        listView.getLoadingLayoutProxy(true,false).setPullLabel("下拉可以刷新");
        listView.getLoadingLayoutProxy(false,true).setRefreshingLabel("加载中");
        listView.getLoadingLayoutProxy(false,true).setReleaseLabel("松开立即加载");
        listView.getLoadingLayoutProxy(false,true).setPullLabel("上拉可以加载");

        simpleAdapter = new SimpleAdapter(PunchActivity.this, getPunchData(true), R.layout.punch_list_cell,
                new String[]{"name", "id", "punchTime", "null"},
                new int[]{
                        R.id.nameTextViewInPunchListCell,
                        R.id.idTextViewInPunchListCell,
                        R.id.punchTimeTextViewInPunchListView,
                        R.id.dataTypeTextViewInPunchListView
                });
        listView.setAdapter(simpleAdapter);

//        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//
//                if (listView.isHeaderShown()) {
//                    getPunchData(true);
//                } else if (listView.isFooterShown()) {
//                    if (currentPage != totalPage) {
//                        getPunchData(false);
//                    } else {
//                        listView.getLoadingLayoutProxy(false,true).setRefreshingLabel("已全部加载完成");
//                        listView.getLoadingLayoutProxy(false,true).setReleaseLabel("已全部加载完成");
//                        listView.getLoadingLayoutProxy(false,true).setPullLabel("已全部加载完成");
//                        listView.onRefreshComplete();
//                    }
//
//                }
//
//            }
//        });

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                getPunchData(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (currentPage != totalPage) {
                    getPunchData(false);
                } else {
                    listView.getLoadingLayoutProxy(false,true).setRefreshingLabel("已全部加载完成");
                    listView.getLoadingLayoutProxy(false,true).setReleaseLabel("已全部加载完成");
                    listView.getLoadingLayoutProxy(false,true).setPullLabel("已全部加载完成");
                    listView.onRefreshComplete();
                }
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

    public void onDestroy() {
        super.onDestroy();
        toast.cancel();
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

    private List<Map<String, Object>> getPunchData(final Boolean isRefresh) {

        if (isFirstTimeToGet) {
            kProgressHUD = KProgressHUD.create(PunchActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("获取中")
                    .setAnimationSpeed(1)
                    .setDimAmount(0.3f)
                    .setSize(110, 110)
                    .setCancellable(false)
                    .show();
        }

        //获取保存的用户名和密码
        String username,password;
        SharedPreferences user = getSharedPreferences("user", Activity.MODE_PRIVATE);
        username = user.getString("username","");
        password = user.getString("password","");

        //刷新则清空
        if (isRefresh) {
            dataList.clear();
            currentPage = 0;
            Log.i("Main", "isRefresh");
            listView.getLoadingLayoutProxy(false,true).setRefreshingLabel("加载中");
            listView.getLoadingLayoutProxy(false,true).setReleaseLabel("松开立即加载");
            listView.getLoadingLayoutProxy(false,true).setPullLabel("上拉可以加载");
        }

        //设置输入参数
        RequestParams params = new RequestParams();
        params.put("userName", "jkxx");
        params.put("password", "xMpCOKC5I4INzFCab3WEmw==");
        params.put("shipNo", shipNo);
        params.put("pageNum", currentPage + 1);
        params.put("pageSize", pageSize);
        params.put("jkxxUser", username);

        String urlBody = getString(R.string.rcldServerIP)+ getString(R.string.punchAllByPageUrl);
        String url = urlBody+"?userName="+username+"&password="+password+"&pageNum=0"+"&pageSize=20";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlBody, params, new JsonHttpResponseHandler("UTF-8"){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i("Main",response.toString());

                try {
                    String msg = response.getString("msg");
                    if (msg.equals("成功")) {

                        JSONArray dataArray = response.getJSONArray("data");

                        //刷新的时候 重置总数和总页数
                        if (isRefresh) {
                            sum = response.getInt("total");  //获取总数
                            totalPage = sum / pageSize + 1;

                            //如果只有1页
                            if (totalPage == 1) {
                                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else {
                                listView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        } else {
                            //加载的时候检测总数是否变化 如变化 重新刷新
                            int sumToget = response.getInt("total");
                            if (sumToget != sum) {
                                getPunchData(true);
                                Toast.makeText(getApplicationContext(),"总数有变，已刷新数据",LENGTH_SHORT).show();
                                return;
                            }

                        }

                        currentPage += 1;

                        for(int i = 0; i < dataArray.length(); i++) {
                            JSONObject punch = (JSONObject) dataArray.get(i);

                            Map<String, Object> map = new Hashtable<>();
                            map.put("id", punch.getString("sailorIdNo"));
                            map.put("name", punch.getString("sailorName"));
                            map.put("punchTime", punch.getString("punchTime"));
                            map.put("null", "");
//                            map.put("null", "第"+currentPage+"页");

                            dataList.add(map);
                        }

                    } else {
                        toast.setText(msg);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toast.setText("数据解析失败");
                    toast.show();
                }
                if (isFirstTimeToGet) {
                    kProgressHUD.dismiss();
                    isFirstTimeToGet = false;
                }
                simpleAdapter.notifyDataSetChanged();
                listView.onRefreshComplete();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                if (isFirstTimeToGet) {
                    kProgressHUD.dismiss();
                    isFirstTimeToGet = false;
                }
                listView.onRefreshComplete();
                toast.setText("网络连接失败");
                toast.show();
            }
        });

        return dataList;
    }

}

