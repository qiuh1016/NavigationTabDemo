package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.ListCell;
import com.cetcme.zytyumin.MyClass.ListSeparator;
import com.cetcme.zytyumin.MyClass.NavigationView;
import com.cetcme.zytyumin.MyClass.TodoListCell;
import com.fr.android.activity.LoadAppFromURLActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LawActivity extends Activity {

    private KProgressHUD kProgressHUD;
    
    private String TAG = "LawActivity";

    private List<LawTitle> lawTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);
        initNavigationView();
        initHud();
        getData();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_law_activity);
        navigationView.setTitle("法律法规");
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

    private void initHud() {
        //hudView
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("获取中")
                .setAnimationSpeed(1)
                .setDimAmount(0.3f)
                .setSize(110, 110)
                .setCancellable(false);
    }

    private class LawTitle {

        private String title;
        private int id;

        public LawTitle() {

        }

        public LawTitle(String title, int id) {
            this.title = title;
            this.id = id;
        }

        public void setName(String title) {
            this.title = title;
        }

        public void setID(int id) {
            this.id = id;
        }
    }

    private void getData() {

        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        String sessionKey = user.getString("sessionKey", "");

        RequestParams params = new RequestParams();
        params.put("sessionKey", sessionKey);
        String url = getString(R.string.serverIP) + getString(R.string.getLawTitleListUrl);

        kProgressHUD.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
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
                            Toast.makeText(LawActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                            kProgressHUD.dismiss();
                            return;
                        }

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject object = list.getJSONObject(i);
                            String title = object.getString("Key");
                            int id = object.getInt("Value");

                            lawTitleList.add(new LawTitle(title, id));
                        }

                        initLayout(lawTitleList);

                    } else {
                        /**
                         * 获取失败
                         */
                        kProgressHUD.dismiss();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    /**
                     * json解析失败
                     */
                    e.printStackTrace();
                    Log.i(TAG, "onResponse: json解析错误");
                    kProgressHUD.dismiss();
                    Toast.makeText(getApplicationContext(), "解析错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                kProgressHUD.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                kProgressHUD.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initLayout(final List<LawTitle> titles) {

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linerLayout_in_law_activity);
        linearLayout.setVisibility(View.VISIBLE);

        int selector;
        for (int i = 0; i < titles.size(); i++) {
            final int finalI = i;

            if (i == 0) {
                selector = titles.size() == 1 ? R.drawable.single_layout_selector : R.drawable.top_layout_selector;
            } else if (i == titles.size() - 1){
                selector = (i % 2 == 0) ? R.drawable.bottom_layout_selector: R.drawable.bottom_layout_light_selector;
            } else {
                selector = (i % 2 == 0) ? R.drawable.mid_layout_selector: R.drawable.mid_layout_light_selector;
            }

            /**
             * 功能行 list_cell
             */
            ListCell listCell = new ListCell(this, titles.get(i).title, selector);
            listCell.removeImageView();
            listCell.setTextSize(14);

            listCell.setClickCallback(new ListCell.ClickCallback() {
                @Override
                public void onClick() {
                    Log.i(TAG, "onClick: " + titles.get(finalI).id + "-" + titles.get(finalI).title);

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", titles.get(finalI).id);
                    bundle.putString("title", titles.get(finalI).title);
                    intent.putExtras(bundle);
                    intent.setClass(getApplicationContext(), LawContentActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                }
            });

            linearLayout.addView(listCell);

            /**
             * 分割线 list_separator
             */
            if (i != titles.size() - 1) {
                ListSeparator listSeparator = new ListSeparator(this);
                linearLayout.addView(listSeparator);
            }
        }

        kProgressHUD.dismiss();

    }
}
