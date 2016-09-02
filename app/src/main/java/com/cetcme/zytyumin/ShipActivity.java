package com.cetcme.zytyumin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.cetcme.zytyumin.MyClass.ListCell;
import com.cetcme.zytyumin.MyClass.ListSeparator;
import com.cetcme.zytyumin.MyClass.NavigationView;

public class ShipActivity extends Activity {

    private boolean openFromMapFragment;
    private String[] shipNames;
    private String[] shipNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);

        openFromMapFragment = getIntent().getExtras().getBoolean("openFromMapFragment");
        shipNames = getIntent().getExtras().getStringArray("shipNames");
        shipNumbers = getIntent().getExtras().getStringArray("shipNumbers");

        initNavigationView();
        initLayout();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_right_in_no_alpha,
                R.anim.push_right_out_no_alpha);
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_ship_activity);
        navigationView.setTitle("您的船只");
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

    private void initLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linerLayout_in_visa_activity);

        int selector;
        for (int i = 0; i < shipNames.length; i++) {

            if (i == 0) {
                selector = R.drawable.top_layout_selector;
            } else if (i == shipNames.length - 1){
                selector = (i % 2 == 0) ? R.drawable.bottom_layout_selector: R.drawable.bottom_layout_light_selector;
            } else {
                selector = (i % 2 == 0) ? R.drawable.mid_layout_selector: R.drawable.mid_layout_light_selector;
            }

            /**
             * 功能行 list_cell
             */
            ListCell listCell = new ListCell(this, shipNames[i], selector);
            final int finalI = i;
            listCell.setClickCallback(new ListCell.ClickCallback() {
                @Override
                public void onClick() {
                    Log.i("main", "onClick: " + shipNames[finalI]);

                    if (openFromMapFragment) {
                        /**
                         * 进入ship info
                         */
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("shipName", shipNames[finalI]);
                        intent.putExtras(bundle);
                        intent.setClass(getApplicationContext(), ShipInfoActivity.class);
                        startActivity(intent);
                    } else {
                        /**
                         * 进入visa界面
                         */
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), VisaActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("shipName", shipNames[finalI]);
                        bundle.putString("shipNumber", shipNumbers[finalI]);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                    }


                }
            });
            linearLayout.addView(listCell);

            /**
             * 分割线 list_separator
             */
            if (i != shipNames.length - 1) {
                ListSeparator listSeparator = new ListSeparator(this);
                linearLayout.addView(listSeparator);
            }
        }

    }
}
