package com.cetcme.zytyumin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cetcme.zytyumin.MyClass.Ship;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cetcme.zytyumin.IconPager.BaseFragment;
import com.cetcme.zytyumin. MyClass.NoScrollGridView;
import com.cetcme.zytyumin.MyClass.NavigationView;

import org.w3c.dom.Text;

/**
 * Created by qiuhong on 8/24/16.
 */
public class HomepageFragment extends BaseFragment {
    private View view;
    private String TAG = "HomepageFragment";

    private SharedPreferences user;

    private GridView gridView;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;

    private Toast toast;
    private MyLoginStateReceiver myLoginStateReceiver;

    private List<Ship> ships;

    private int[] gridIcon = {
            R.drawable.homepage_service,
            R.drawable.homepage_record,
            R.drawable.homepage_visa,
            R.drawable.homepage_low,
            R.drawable.homepage_process,
            R.drawable.homepage_todo
    };

    private String[] gridName;

    private Class[] gridClass = {
            ServiceActivity.class,
            RecordActivity.class,
            VisaActivity.class,
            null,
            ProcessActivity.class,
            TodoActivity.class
    };

    private Banner banner;

    private String[] images = new String[] {
            "http://www.cnfm.gov.cn/tpxwsyyzw/201607/W020160729535413736589.jpg",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/W020160613350121243228.jpg",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/W020160607311179962227.jpg",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201605/W020160530525181788332.jpg",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/W020160629399731306479.jpg"
    };

    private String[] urls = new String[] {
            "https://view.inews.qq.com/a/NEW201608260218860A",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201607/t20160729_5222942.htm",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/t20160613_5167471.htm",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/t20160607_5163334.htm",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201605/t20160530_5154751.htm",
            "http://www.cnfm.gov.cn/tpxwsyyzw/201606/t20160629_5190352.htm"
    };

    private String[] titles = new String[] {
            "学习习近平总书记“七一”重要讲话加快推进渔业转型升级",
            "于康震：以科技为支撑 以市场为导向 实现有质量的渔业转型升级发展",
            "水生生物增殖放流活动在全国范围同步举行",
            "中国秘鲁举行双边渔业会谈",
            "渔业渔政管理局开展定点扶贫村结对帮扶工作"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, null);
        user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        gridName = new String[]{
                getString(R.string.gird_1_in_fragment_homepage),
                getString(R.string.gird_2_in_fragment_homepage),
                getString(R.string.gird_3_in_fragment_homepage),
                getString(R.string.gird_4_in_fragment_homepage),
                getString(R.string.gird_5_in_fragment_homepage),
                getString(R.string.gird_6_in_fragment_homepage)
        };

        initNavigationView();
        initGridView();
        initBanner();
        initLoginBroadcast();

        toast = Toast.makeText(getActivity(), "您的名下没有船只", Toast.LENGTH_SHORT);

        return view;
    }

    private NavigationView navigationView;

    private void initNavigationView() {
        navigationView = (NavigationView) view.findViewById(R.id.nav_main_in_fragment_homepage);
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
        gridView = (NoScrollGridView) view.findViewById(R.id.gridView_in_fragment_homepage);
        //新建List
        data_list = new ArrayList<>();
        //获取数据
        getData();
        //新建适配器
        final String [] from ={"image","text"};
        int [] to = {R.id.grid_item_image, R.id.grid_item_text};
        MyGridAdapter sim_adapter = new MyGridAdapter(getActivity(), data_list, R.layout.grid_item, from, to);
        //配置适配器
        gridView.setAdapter(sim_adapter);
        //点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**
                 * 为登陆则跳出登陆界面
                 */
                if (!user.getBoolean("hasLogin",false)) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    MainActivity activity = (MainActivity) getActivity();
                    activity.overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
                    return;
                }

                /**
                 * 已登陆则正常显示功能界面
                 */
                if (gridClass[position] != null) {


                    if (position == 2) {
                        /**
                         * 打开电子签证界面前进行判断船只数量
                         */

                        if (ships == null) {
                            Log.i(TAG, "onItemClick: ships null");
                            toast.show();
                            return;
                        }
                        
                        if (ships.size() > 1) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), ShipActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("openFromMapFragment", false);
                            bundle.putSerializable("ships", (Serializable) ships);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            MainActivity activity = (MainActivity) getActivity();
                            activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                        } else if (ships.size() == 1){
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), gridClass[position]);
                            Bundle bundle = new Bundle();
                            bundle.putString("shipName", ships.get(0).name);
                            bundle.putString("shipNumber", ships.get(0).number);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            MainActivity activity = (MainActivity) getActivity();
                            activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                        } else {
                            toast.show();
                        }

                    } else if (position == 5){
                        /**
                         * 待办业务
                         */
                        Bundle bundle = new Bundle();
                        bundle.putInt("Check_Drawing_Examine_Opinion_Count", ((MainActivity) getActivity()).getTodoNumbers()[0]);
                        bundle.putInt("Check_Detect_Info_Detail_Inspection_Count", ((MainActivity) getActivity()).getTodoNumbers()[1]);
                        bundle.putInt("Check_Detect_Info_Opinion_Count", ((MainActivity) getActivity()).getTodoNumbers()[2]);
                        Intent intent = new Intent(getActivity(), TodoActivity.class);
                        intent.putExtras(bundle);
                        MainActivity activity = (MainActivity) getActivity();
                        startActivity(intent);
                        activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), gridClass[position]);
                        startActivity(intent);
                        MainActivity activity = (MainActivity) getActivity();
                        activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                    }

                } else {
                    Toast.makeText(getActivity(), "即将上线", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public List<Map<String, Object>> getData(){

        for(int i = 0; i < gridIcon.length; i++){
            Map<String, Object> map = new HashMap<>();
            map.put("image", gridIcon[i]);
            map.put("text", gridName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    private void initBanner() {
        banner = (Banner) view.findViewById(R.id.banner_in_fragment_homepage);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImages(images);
        banner.setBannerTitle(titles);
        banner.setDelayTime(8000);
        banner.setScrollerTime(350);
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                Log.i(TAG, "OnBannerClick: " + position);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("url", urls[position - 1]);
                bundle.putString("title", "新闻");
                intent.putExtras(bundle);
                intent.setClass(getActivity(), WebActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initLoginBroadcast() {
        myLoginStateReceiver = new MyLoginStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.loginFlag");
        getActivity().registerReceiver(myLoginStateReceiver, filter);
    }

    public class MyLoginStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {

            Log.i(TAG, "onReceive: get login flag");
            Bundle bundle = arg1.getExtras();
            Boolean loginFlag = bundle.getBoolean("loginFlag");

            if (loginFlag) {
                ships = ((MainActivity) getActivity()).getShips();
            }

        }
    }

    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myLoginStateReceiver);
    }

    class MyGridAdapter extends SimpleAdapter {

        public Context context;
        public int resource;

        /**
         * Constructor
         *
         * @param context  The context where the View associated with this SimpleAdapter is running
         * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
         *                 Maps contain the data for each row, and should include all the entries specified in
         *                 "from"
         * @param resource Resource identifier of a view layout that defines the views for this list
         *                 item. The layout file should include at least those named views defined in "to"
         * @param from     A list of column names that will be added to the Map associated with each
         *                 item.
         * @param to       The views that should display column in the "from" parameter. These should all be
         *                 TextViews. The first N views in this list are given the values of the first N columns
         */
        public MyGridAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                Log.i(TAG, "getView: " + position);

                convertView = inflater.inflate(R.layout.grid_item, parent, false);

                TextView textView = (TextView) convertView.findViewById(R.id.grid_item_text);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
                textView.setText(gridName[position]);
                imageView.setImageResource(gridIcon[position]);

                LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.grid_item_layout);
                linearLayout.setPadding(30,30,30,30);

                switch (position) {
                    case 0:
                        linearLayout.setBackgroundResource(R.drawable.grid_item_background_top_left);
                        break;
                    case 2:
                        linearLayout.setBackgroundResource(R.drawable.grid_item_background_top_right);
                        break;
                    case 3:
                        linearLayout.setBackgroundResource(R.drawable.grid_item_background_bottom_left);
                        break;
                    case 5:
                        linearLayout.setBackgroundResource(R.drawable.grid_item_background_bottom_right);
                        break;
                    default:
                        linearLayout.setBackgroundResource(R.drawable.grid_item_background_no_radius);
                        break;
                }

            }

            return convertView;
        }

    }
}
