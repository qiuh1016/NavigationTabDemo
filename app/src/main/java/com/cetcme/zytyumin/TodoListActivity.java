package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.ButtonShack;
import com.cetcme.zytyumin.MyClass.ListCell;
import com.cetcme.zytyumin.MyClass.ListSeparator;
import com.cetcme.zytyumin.MyClass.NavigationView;
import com.cetcme.zytyumin.MyClass.PrivateEncode;
import com.cetcme.zytyumin.MyClass.Ship;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListActivity extends Activity {

    private String title;
    private List<TodoObject> todoObjects = new ArrayList<>();

    private String TAG = "TodoListActivity";
    private KProgressHUD kProgressHUD;

    private String url;
    private String frURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        title = getIntent().getExtras().getString("title");

        initNavigationView();
        initHud();
        initURL();
        getData();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_todo_list_activity);
        navigationView.setTitle(title);
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

    private class TodoObject {
        private String name;
        private String time;
        private int id;

        public TodoObject() {

        }
        public TodoObject(String name, String time, int id) {
            this.name = name;
            this.time = time;
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setID(int id) {
            this.id = id;
        }
    }

    private void initURL() {
        switch (title) {
            case "审图意见书":
                url = getString(R.string.serverIP) + getString(R.string.get_todo_line_1_url);
                frURL = getString(R.string.serverIP) + getString(R.string.fr_todo_line_1_url);
                break;
            case "报检表":
                url = getString(R.string.serverIP) + getString(R.string.get_todo_line_2_url);
                frURL = getString(R.string.serverIP) + getString(R.string.fr_todo_line_2_url);
                break;
            case "整改意见书":
                url = getString(R.string.serverIP) + getString(R.string.get_todo_line_3_url);
                frURL = getString(R.string.serverIP) + getString(R.string.fr_todo_line_3_url);
                break;
            case "检验记录":
                url = getString(R.string.serverIP) + getString(R.string.get_todo_line_4_url);
                frURL = getString(R.string.serverIP) + getString(R.string.fr_todo_line_4_url);
                break;
        }
    }

    private void getData() {

        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        String sessionKey = user.getString("sessionKey", "");
        String username = user.getString("username", "");

        RequestParams params = new RequestParams();
        params.put("sessionKey", sessionKey);
        params.put("account", username);

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
                            Toast.makeText(TodoListActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                            kProgressHUD.dismiss();
                            return;
                        }

                        for (int i = 0; i < list.length(); i++) {
                            JSONObject object = list.getJSONObject(i);
                            TodoObject todo = new TodoObject();
                            /**
                             * 根据不同类别进行解析
                             */
                            switch (title) {
                                case "审图意见书":
                                    todo.setName(object.getString("drawing_num"));
                                    todo.setTime(getFormatTime(object.getString("app_date")));
                                    todo.setID(object.getInt("id"));
                                    break;
                                case "报检表":
                                    todo.setName(object.getString("ship_name"));
                                    todo.setTime(getFormatTime(object.getString("app_date")));
                                    todo.setID(object.getInt("appid"));
                                    break;
                                case "整改意见书":
                                    todo.setName(object.getString("ship_name"));
                                    todo.setTime(getFormatTime(object.getString("app_date")));
                                    todo.setID(object.getInt("appid"));
                                    break;
                                case "检验记录":
                                    todo.setName(object.getString("ship_name"));
                                    todo.setTime(getFormatTime(object.getString("app_date")));
                                    todo.setID(object.getInt("id"));
                                    break;
                            }
                            todoObjects.add(todo);
                        }

                        kProgressHUD.dismiss();
                        initLayout(todoObjects);


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

    private void initLayout(final List<TodoObject> todos) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linerLayout_in_todo_list_activity);
        linearLayout.setVisibility(View.VISIBLE);

        int selector;
        for (int i = 0; i < todos.size(); i++) {

            if (i == 0) {
                selector = todos.size() == 1 ? R.drawable.single_layout_selector : R.drawable.top_layout_selector;
            } else if (i == todos.size() - 1){
                selector = (i % 2 == 0) ? R.drawable.bottom_layout_selector: R.drawable.bottom_layout_light_selector;
            } else {
                selector = (i % 2 == 0) ? R.drawable.mid_layout_selector: R.drawable.mid_layout_light_selector;
            }

            /**
             * 功能行 list_cell
             */
            TodoListCell listCell = new TodoListCell(this, todos.get(i).name, todos.get(i).time, selector);
            final int finalI = i;

            listCell.setClickCallback(new TodoListCell.ClickCallback() {
                @Override
                public void onClick() {
                    if (frURL.isEmpty()) {
                        Toast.makeText(TodoListActivity.this, "无链接", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent();
                    String urlString = frURL +todos.get(finalI).id;
                    if (title.equals("报检表")) {
                        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
                        String username = user.getString("username", "");
                        urlString += "&account=" + username;
                    }
                    intent.putExtra("url", urlString);
                    Log.i(TAG, "onClick: " + urlString);
                    intent.putExtra("title", title);
                    intent.setClass(getApplicationContext(), LoadAppFromURLActivity.class);
                    startActivity(intent);
                }
            });

            linearLayout.addView(listCell);

            /**
             * 分割线 list_separator
             */
            if (i != todos.size() - 1) {
                ListSeparator listSeparator = new ListSeparator(this);
                linearLayout.addView(listSeparator);
            }
        }

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

    private String getFormatTime(String unFormatTime) {
        String time = unFormatTime.substring(6 ,unFormatTime.length() - 2);
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
