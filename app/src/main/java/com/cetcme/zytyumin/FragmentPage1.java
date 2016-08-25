package com.cetcme.zytyumin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IconPager.BaseFragment;
import navigationView.NavigationView;

/**
 * Created by qiuhong on 8/24/16.
 */
public class FragmentPage1 extends BaseFragment {
    private View view;
    private String TAG = "FragmentPage1";

    private int[] gridIcon = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    private String[] gridName;

    private Class[] gridClass = {
            ServiceActivity.class,
            RecordActivity.class,
            null,
            null,
            null,
            null
    };

    public enum Function {
        SERVICE, RECORD, VISE, LAW, PROCESS, TODO
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, null);

        gridName = new String[]{
                getString(R.string.gird_1_in_fragment_1),
                getString(R.string.gird_2_in_fragment_1),
                getString(R.string.gird_3_in_fragment_1),
                getString(R.string.gird_4_in_fragment_1),
                getString(R.string.gird_5_in_fragment_1),
                getString(R.string.gird_6_in_fragment_1)
        };

        initNavigationView();
        initGridView();

        return view;
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) view.findViewById(R.id.nav_main_in_fragment_1);
        navigationView.setTitle("首页");
        navigationView.setBackView(0);
        navigationView.setRightView(0);
        navigationView.setClickCallback(new NavigationView.ClickCallback() {

            @Override
            public void onRightClick() {
                Log.i("main","点击了右侧按钮!");
            }

            @Override
            public void onBackClick() {
                Log.i("main","点击了左侧按钮!");
            }
        });
    }

    private GridView gridView;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;

    private void initGridView() {
        gridView = (GridView) view.findViewById(R.id.gridViewInFragment_1);
        //新建List
        data_list = new ArrayList<>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.grid_item_image,R.id.grid_item_text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.grid_item, from, to);
        //配置适配器
        gridView.setAdapter(sim_adapter);
        //点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (gridClass[position] != null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), gridClass[position]);
                    startActivity(intent);
                    MainActivity activity = (MainActivity) getActivity();
                    activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                } else {
                    Toast.makeText(getActivity(), "待开发", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public List<Map<String, Object>> getData(){
        //icon和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<gridIcon.length;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("image", gridIcon[i]);
            map.put("text", gridName[i]);
            data_list.add(map);
        }

        return data_list;
    }
}
