package com.cetcme.zytyumin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import MyClass.NavigationView;

public class TableActivity extends Activity {

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;

    private NavigationView navigationView;

    private List<Map<String, Object>> info = new LinkedList<>();
    private String[] tableHeadString = {"船名", "投保起期", "投保止期", "是否欠费"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        initNavigationView();
        initData();
        initTableLayout();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_main_in_table_activity);
        navigationView.setTitle("投保记录");
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

    private void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put(tableHeadString[0], "船名1");
        map.put(tableHeadString[1], "2014/11/22");
        map.put(tableHeadString[2], "2015/11/21");
        map.put(tableHeadString[3], "是");
        info.add(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put(tableHeadString[0], "船名2");
        map1.put(tableHeadString[1], "2014/03/14");
        map1.put(tableHeadString[2], "2015/03/13");
        map1.put(tableHeadString[3], "是");
        info.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put(tableHeadString[0], "船名3");
        map2.put(tableHeadString[1], "2015/09/12");
        map2.put(tableHeadString[2], "2016/09/11");
        map2.put(tableHeadString[3], "否");
        info.add(map2);

        info.add(map1);
        info.add(map1);
        info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);info.add(map1);

    }

    private void initTableLayout() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout);
        //全部列自动填充空白处
        tableLayout.setStretchAllColumns(true);

        //标题栏
        TableRow tableHeadRow = new TableRow(this);
        for(int col = 0; col < 4; col++)
        {
            //tv用于显示
            TextView tv = new TextView(this);
            tv.setText(tableHeadString[col]);
            tv.setBackgroundResource(R.drawable.table_background_light);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0, 20, 0, 20);
            tv.getPaint().setFakeBoldText(true);
            tableHeadRow.addView(tv);
        }
        //新建的TableRow添加到TableLayout
        tableLayout.addView(tableHeadRow, new TableLayout.LayoutParams(FP, WC));


        //生成表格
        for(int row = 0; row < info.size(); row++)
        {
            TableRow tableRow = new TableRow(this);

            //绘制列
            for(int col = 0; col < tableHeadString.length; col++)
            {
                //tv用于显示
                TextView tv = new TextView(this);
                tv.setText(info.get(row).get(tableHeadString[col]).toString());
                tv.setBackgroundResource(R.drawable.boder);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(0, 20, 0, 20);
                tableRow.addView(tv);
            }

            //设置背景
            if (row % 2 == 0) {
                tableRow.setBackgroundResource(R.drawable.mid_layout_selector);
            } else {
                tableRow.setBackgroundResource(R.drawable.mid_layout_light_selector);
            }

            //新建的TableRow添加到TableLayout
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(FP, WC));

            //设置点击事件
            final int finalRow = row;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("main", "onClick: " + finalRow + info.get(finalRow));
                }
            });

        }

    }
}
