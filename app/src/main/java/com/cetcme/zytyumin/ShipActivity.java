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

    private String[] shipName = {
            "浙嘉渔1234",
            "浙嘉渔3214",
            "浙嘉渔1314"};

    private String[] shipNo = {
            "3303811998090003",
            "3303812001050005",
            "3302251998010002"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);
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

    private void initLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linerLayout_in_visa_activity);

        int selector;
        for (int i = 0; i < shipName.length; i++) {

            if (i == 0) {
                selector = R.drawable.top_layout_selector;
            } else if (i == shipName.length - 1){
                selector = (i % 2 == 0) ? R.drawable.bottom_layout_selector: R.drawable.bottom_layout_light_selector;
            } else {
                selector = (i % 2 == 0) ? R.drawable.mid_layout_selector: R.drawable.mid_layout_light_selector;
            }

            /**
             * 功能行 list_cell
             */
            ListCell listCell = new ListCell(this, shipName[i], selector);
            final int finalI = i;
            listCell.setClickCallback(new ListCell.ClickCallback() {
                @Override
                public void onClick() {
                    Log.i("main", "onClick: " + shipName[finalI]);
                    /**
                     * 进入visa界面
                     */
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), VisaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("shipName", shipName[finalI]);
                    bundle.putString("shipNo", shipNo[finalI]);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);

                }
            });
            linearLayout.addView(listCell);

            /**
             * 分割线 list_separator
             */
            if (i != shipName.length - 1) {
                ListSeparator listSeparator = new ListSeparator(this);
                linearLayout.addView(listSeparator);
            }
        }

    }
}
