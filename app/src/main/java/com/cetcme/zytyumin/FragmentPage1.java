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

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IconPager.BaseFragment;
import MyClass.NoScrollGridView;
import MyClass.NavigationView;

/**
 * Created by qiuhong on 8/24/16.
 */
public class FragmentPage1 extends BaseFragment {
    private View view;
    private String TAG = "FragmentPage1";

    private GridView gridView;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;

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
            ShipActivity.class,
            null,
            null,
            null
    };

    private Banner banner;
//    String[] images= new String[] {"http://img.dahe.cn/qf/2016/8/24/1212BRZXKT.jpg", "http://photocdn.sohu.com/20160825/Img465863888.jpg", "http://img3.qianzhan123.com/news/201608/19/20160825-23aea49bce08ba25_600x5000.jpg"};
//    String[] titles=new String[]{"法国警察强制要求穆斯林妇女脱下泳衣", "中国外长3年来首次访日", "张一山新戏《家有儿女初长成》"};

    private String[] images = new String[] {
            "http://www.cnfm.gov.cn/tpxwsyyzw/201607/W020160729535413736589.jpg",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/W020160613350121243228.jpg",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/W020160607311179962227.jpg",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201605/W020160530525181788332.jpg",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/W020160629399731306479.jpg"
    };

    private String[] titles = new String[] {
            "学习习近平总书记“七一”重要讲话加快推进渔业转型升级",
            "于康震：以科技为支撑 以市场为导向 实现有质量的渔业转型升级发展",
            "水生生物增殖放流活动在全国范围同步举行",
            "中国秘鲁举行双边渔业会谈",
            "渔业渔政管理局开展定点扶贫村结对帮扶工作"
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
        initBanner();

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

    private void initGridView() {
        gridView = (NoScrollGridView) view.findViewById(R.id.gridViewInFragment_1);
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

    private void initBanner() {
        banner = (Banner) view.findViewById(R.id.banner_in_fragment_1);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImages(images);
        banner.setBannerTitle(titles);
        banner.setDelayTime(8000);
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                Log.i(TAG, "OnBannerClick: " + position);
            }
        });
    }
}
