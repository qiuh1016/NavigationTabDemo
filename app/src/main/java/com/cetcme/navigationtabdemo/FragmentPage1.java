package com.cetcme.navigationtabdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import navigationView.NavigationView;

/**
 * Created by qiuhong on 8/24/16.
 */
public class FragmentPage1 extends BaseFragment {
    private View view;
    private String TAG = "FragmentPage1";

    private int[] gridIcon = { R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};
    private String[] gridName = { "业务办理", "记录查询", "电子签证", "法律法规", "业务流程", "待办任务"};

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, null);
        initNavigationView();
        initGridView();
        return view;
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) view.findViewById(R.id.nav_main_in_fragment_1);
        navigationView.setTitle("首页");
//        navigationView.setTitleTextColor(Color.WHITE);
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
                Log.i(TAG, "onItemClick: " + position);
                Log.i(TAG, "onItemClick: " + gridName[position]);

                Intent intent = new Intent(getActivity(), serviceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", gridName[position]);
                intent.putExtras(bundle);
                startActivity(intent);
                MainActivity activity = (MainActivity) getActivity();
                activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
            }
        });
    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<gridIcon.length;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("image", gridIcon[i]);
            map.put("text", gridName[i]);
            data_list.add(map);
        }

        return data_list;
    }
}
