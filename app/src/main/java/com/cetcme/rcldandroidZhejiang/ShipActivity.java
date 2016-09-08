package com.cetcme.rcldandroidZhejiang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cetcme.rcldandroidZhejiang.MyClass.ListCell;
import com.cetcme.rcldandroidZhejiang.MyClass.ListSeparator;
import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.MyClass.Ship;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class ShipActivity extends Activity {

    private boolean openFromMapFragment;
    private List<Ship> ships;

    private String TAG = "ShipActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);

        Log.i(TAG, "onCreate: ");

        openFromMapFragment = getIntent().getExtras().getBoolean("openFromMapFragment");
        ships = (List<Ship>) getIntent().getExtras().getSerializable("ships");

        initNavigationView();
        initLayoutWithShipClass();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_ship_activity);
        navigationView.setTitle("我的船只");
        navigationView.setBackView(R.drawable.icon_back_button);
        navigationView.setRightView(R.drawable.icon_search);
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

    private void initLayoutWithShipClass() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linerLayout_in_visa_activity);

        int selector;
        for (int i = 0; i < ships.size(); i++) {

            if (i == 0) {
                selector = ships.size() == 1 ? R.drawable.single_layout_selector : R.drawable.top_layout_selector;
            } else if (i == ships.size() - 1){
                selector = (i % 2 == 0) ? R.drawable.bottom_layout_selector: R.drawable.bottom_layout_light_selector;
            } else {
                selector = (i % 2 == 0) ? R.drawable.mid_layout_selector: R.drawable.mid_layout_light_selector;
            }

            /**
             * 功能行 list_cell
             */
            ListCell listCell = new ListCell(this, ships.get(i).name, selector);
            final int finalI = i;
            listCell.setClickCallback(new ListCell.ClickCallback() {
                @Override
                public void onClick() {
                    Log.i("main", "onClick: " + ships.get(finalI).name);

                    if (openFromMapFragment) {
                        /**
                         * 进入ship info
                         */
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ship", ships.get(finalI));
                        intent.putExtras(bundle);
                        intent.setClass(getApplicationContext(), ShipInfoActivity.class);
                        startActivity(intent);
                    } else {
                        /**
                         * 进入visa界面
                         */
                        if (ships.get(finalI).deviceInstall) {
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), VisaActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("shipName", ships.get(finalI).name);
                            bundle.putString("shipNumber", ships.get(finalI).number);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                        } else {
                            Toast.makeText(ShipActivity.this, "本船不支持电子签证", Toast.LENGTH_SHORT).show();
                        }

                    }


                }
            });
            linearLayout.addView(listCell);

            /**
             * 分割线 list_separator
             */
            if (i != ships.size() - 1) {
                ListSeparator listSeparator = new ListSeparator(this);
                linearLayout.addView(listSeparator);
            }
        }

    }
}
