package com.cetcme.rcldandroidZhejiang;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.MyClass.Ship;
import com.cetcme.rcldandroidZhejiang.rcld.RouteActivity;
import com.cetcme.rcldandroidZhejiang.xia.DetailFishShipActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipInfoActivity extends Activity {

    private String TAG = "ShipInfoActivity";

    private Ship ship;

    private TextView shipMaterialTextView;
    private TextView shipLengthTextView;
    private TextView mainPowerTextView;
    private TextView allTonTextView;
    private TextView shipTypeTextView;
    private TextView jobTypeTextView;
    private TextView updateDateTextView;

    private Toast toast;

    private KProgressHUD kProgressHUD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_info);

        ship = (Ship) getIntent().getExtras().getSerializable("ship");

        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

        initNavigationView();
        initUI();
        initHud();
        getShipInfo(this.ship.number);

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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

        Button routeButton = (Button) findViewById(R.id.route_button_in_ship_info_activity);
//        routeButton.setEnabled(ship.deviceInstall);
        if (!ship.deviceInstall) {
            routeButton.setBackgroundResource(R.drawable.single_select_unavailable);
        }

    }

    private void initHud() {
        //hudView
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("登陆中")
                .setAnimationSpeed(1)
                .setDimAmount(0.3f)
                .setSize(90, 90)
                .setCancellable(false);
    }

    /**
     * 点击视图外 关闭窗口
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        finish();
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
    }

    public void routeButtonTapped(View v) {
        if (ship.deviceInstall) {
            Intent intent = new Intent();
            intent.setClass(this, RouteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("shipNo", ship.number);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
        } else {
            toast.setText("本船不支持查看轨迹");
            toast.show();
        }

    }

    private void getShipInfo(String shipNumber) {
        kProgressHUD.show();

        RequestParams params = new RequestParams();
        params.put("shipNo", shipNumber);
        String urlBody = getString(R.string.serverIP) + getString(R.string.getShipInfoByNo);

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
//                        String shipMaterial  = getString(R.string.line_1_in_ship_info_activity) + ship_info.getString("APP_SHIP_MATERIAL");
//                        String shipLength    = getString(R.string.line_2_in_ship_info_activity) + ship_info.getString("APP_SHIP_LENGTH");
//                        String mainPower     = getString(R.string.line_3_in_ship_info_activity) + ship_info.getString("APP_MAIN_POWER");
//                        String allTon        = getString(R.string.line_4_in_ship_info_activity) + ship_info.getString("APP_ALL_TON");
//                        String shipType      = getString(R.string.line_5_in_ship_info_activity) + ship_info.getString("APP_SHIP_TYPE");
//                        String jobType       = getString(R.string.line_6_in_ship_info_activity) + ship_info.getString("APP_JOB_TYPE1");

                        String shipMaterial  = getString(R.string.line_1_in_ship_info_activity) + ship_info.getString("APP_SHIP_MATERIAL");
                        String shipLength    = getString(R.string.line_2_in_ship_info_activity) + ship_info.getString("SHIP_LENGTH");
                        String mainPower     = getString(R.string.line_3_in_ship_info_activity) + ship_info.getString("SHIP_TOT_POWER");
                        String allTon        = getString(R.string.line_4_in_ship_info_activity) + ship_info.getString("SHIP_TOT_TON");
                        String shipType      = getString(R.string.line_5_in_ship_info_activity) + ship_info.getString("SHIP_BUSINESS_TYPE");
                        String jobType       = getString(R.string.line_6_in_ship_info_activity) + ship_info.getString("JOB_TYPE");

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

                kProgressHUD.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "onFailure: 1");
                toast.setText("获取信息失败");
                toast.show();
                kProgressHUD.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "onFailure: 2");
                toast.setText("获取信息失败");
                toast.show();
                kProgressHUD.dismiss();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        toast.cancel();
    }
}
