package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.NavigationView;
import com.cetcme.zytyumin.MyClass.PrivateEncode;
import com.cetcme.zytyumin.MyClass.Ship;
import com.cetcme.zytyumin.rcld.RouteActivity;
import com.cetcme.zytyumin.xia.DetailFishShipActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipInfoActivity extends Activity {

    private String TAG = "ShipInfoActivity";
//    private String shipName;
//    private String shipNumber;

    private Ship ship;

    private TextView shipMaterialTextView;
    private TextView shipLengthTextView;
    private TextView mainPowerTextView;
    private TextView allTonTextView;
    private TextView shipTypeTextView;
    private TextView jobTypeTextView;
    private TextView updateDateTextView;

    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_info);

//        shipName = getIntent().getExtras().getString("shipName");
//        shipNumber = getIntent().getExtras().getString("shipNumber");
        ship = (Ship) getIntent().getExtras().getSerializable("ship");

        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

        initNavigationView();
        initUI();

        getShipInfo(this.ship.number);

    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_in_ship_info_activity);
        navigationView.setBackgroundResource(R.drawable.top_select);
        navigationView.setTitle(ship.name);
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
                finish();
            }
        });
    }

    private void initUI() {
        shipMaterialTextView = (TextView) findViewById(R.id.text1_in_ship_info_activity);
        shipLengthTextView   = (TextView) findViewById(R.id.text2_in_ship_info_activity);
        mainPowerTextView    = (TextView) findViewById(R.id.text3_in_ship_info_activity);
        allTonTextView       = (TextView) findViewById(R.id.text4_in_ship_info_activity);
        shipTypeTextView     = (TextView) findViewById(R.id.text5_in_ship_info_activity);
        jobTypeTextView      = (TextView) findViewById(R.id.text6_in_ship_info_activity);
        updateDateTextView   = (TextView) findViewById(R.id.text7_in_ship_info_activity);
    }

    /**
     * 点击视图外 关闭窗口
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        finish();
//        Log.i(TAG, "onTouchEvent: ");
        return true;
    }

    public void detailButtonTapped(View v) {
        Intent intent = new Intent();
        intent.setClass(this, DetailFishShipActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("shipNumber", ship.number);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
//        Toast.makeText(ShipInfoActivity.this, "显示详情界面", Toast.LENGTH_SHORT).show();
    }

    public void routeButtonTapped(View v) {
        if (ship.deviceInstall) {
            Intent intent = new Intent();
            intent.setClass(this, RouteActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
        } else {
            Toast.makeText(this, "本船不支持查看轨迹", Toast.LENGTH_SHORT).show();
        }

    }

    private void getShipInfo(String shipNumber) {
        RequestParams params = new RequestParams();
        params.put("shipNo", shipNumber);
        String urlBody = "http://61.164.218.155:5000//bpm/YZSoft/Webservice/AppWebservice.asmx/GetShipInfoByNo";
        String url = "http://61.164.218.155:5000//bpm/YZSoft/Webservice/AppWebservice.asmx/GetShipInfoByName?shipName=浙三渔04529";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlBody, params, new JsonHttpResponseHandler("UTF-8") {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    final String msg = response.getString("MESSAGE");

                    /**
                     * 成功
                     */
                    if (msg.equals("请求成功")) {
                        JSONObject ship_info =  response.getJSONObject("SHIP_INFO");
                        String shipMaterial  = getString(R.string.line_1_in_ship_info_activity) + ship_info.getString("APP_SHIP_MATERIAL");
                        String shipLength    = getString(R.string.line_2_in_ship_info_activity) + ship_info.getString("APP_SHIP_LENGTH");
                        String mainPower     = getString(R.string.line_3_in_ship_info_activity) + ship_info.getString("APP_MAIN_POWER");
                        String allTon        = getString(R.string.line_4_in_ship_info_activity) + ship_info.getString("APP_ALL_TON");
                        String shipType      = getString(R.string.line_5_in_ship_info_activity) + ship_info.getString("APP_SHIP_TYPE");
                        String jobType       = getString(R.string.line_6_in_ship_info_activity) + ship_info.getString("APP_JOB_TYPE1");

                        /**
                         * 转换时间格式
                         */
                        String updateDate    = ship_info.getString("UPDATE_DATE");
                        String time = updateDate.substring(6 ,updateDate.length() - 7);
                        System.out.println(time);
                        Date date = new Date(Long.parseLong(time));
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
                        System.out.println(format.format(date));
                        updateDate    = getString(R.string.line_7_in_ship_info_activity) + format.format(date);

                        shipMaterialTextView .setText(shipMaterial);
                        shipLengthTextView   .setText(shipLength);
                        mainPowerTextView    .setText(mainPower);
                        allTonTextView       .setText(allTon);
                        shipTypeTextView     .setText(shipType);
                        jobTypeTextView      .setText(jobType);
                        updateDateTextView   .setText(updateDate);

                    } else {
                        /**
                         * 失败
                         */
                        toast.setText(msg);
                        toast.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);
                    }

                } catch (JSONException e) {
                    /**
                     * json解析失败
                     */
                    e.printStackTrace();
                    toast.setText("json解析错误");
                    toast.show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "onFailure: 1");
                toast.setText("获取信息失败");
                toast.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "onFailure: 2");
                toast.setText("获取信息失败");
                toast.show();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        toast.cancel();
    }
}
