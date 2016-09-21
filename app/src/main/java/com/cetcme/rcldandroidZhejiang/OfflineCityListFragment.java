package com.cetcme.rcldandroidZhejiang;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.qiuhong.qhlibrary.Dialog.QHDialog;
import com.qiuhong.qhlibrary.Utils.DensityUtil;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineCityListFragment extends Fragment implements  MKOfflineMapListener{

    private String TAG = "OfflineCityListFragment";

    private MKOfflineMap mOffline = null;

    private View view;
    private Context context;

    @BindView(R.id.cityid)
    TextView cityIDTextView;

    @BindView(R.id.city)
    EditText cityNameTextView;

    @BindView(R.id.state)
    TextView stateView;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offline_city_list, container, false);
        context = this.getActivity();
        ButterKnife.bind(this, view);
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        initView();
        initButtonClick();
        return view;
    }

    private void initView() {

        ListView hotCityList = (ListView) view.findViewById(R.id.hotcitylist);
        final ArrayList<Map<String, Object>> hotCities = new ArrayList<>();
        // 获取热闹城市列表
        ArrayList<MKOLSearchRecord> records1 = mOffline.getHotCityList();
        if (records1 != null) {
            for (MKOLSearchRecord r : records1) {
                Map<String, Object> map = new Hashtable<>();
                map.put("cityName", r.cityName);
                map.put("cityID", r.cityID);
                map.put("dataSize", this.formatDataSize(r.size));
                hotCities.add(map);
            }
        }
        SimpleAdapter hAdapter = new SimpleAdapter(context, hotCities,
                R.layout.offline_map_list,
                new String[] {"cityName", "dataSize"},
                new int[] {R.id.name, R.id.size});
        hotCityList.setAdapter(hAdapter);
        hotCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityIDTextView.setText(hotCities.get(position).get("cityID").toString());
                cityNameTextView.setText(hotCities.get(position).get("cityName").toString());
            }
        });

        ListView allCityList = (ListView) view.findViewById(R.id.allcitylist);
        // 获取所有支持离线地图的城市
        final ArrayList<Map<String, Object>> allCities = new ArrayList<>();
        ArrayList<MKOLSearchRecord> records2 = mOffline.getOfflineCityList();
        if (records1 != null) {
            for (MKOLSearchRecord r : records2) {
                Map<String, Object> map = new Hashtable<>();
                map.put("cityName", r.cityName);
                map.put("cityID", r.cityID);
                map.put("dataSize", this.formatDataSize(r.size));
                allCities.add(map);
            }
        }
        SimpleAdapter aAdapter = new SimpleAdapter(context, allCities,
                R.layout.offline_map_list,
                new String[] {"cityName", "dataSize"},
                new int[] {R.id.name, R.id.size});
        allCityList.setAdapter(aAdapter);
        allCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityIDTextView.setText(allCities.get(position).get("cityID").toString());
                cityNameTextView.setText(allCities.get(position).get("cityName").toString());
            }
        });

    }

    private void initButtonClick() {
        view.findViewById(R.id.search_button_in_offline_map_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        view.findViewById(R.id.start_button_in_offline_map_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    /**
     * 搜索离线需市
     *
     */
    public void search() {
        ArrayList<MKOLSearchRecord> records = mOffline.searchCity(cityNameTextView
                .getText().toString());
        if (records == null || records.size() != 1) {
            return;
        }
        cityIDTextView.setText(String.valueOf(records.get(0).cityID));
    }

    /**
     * 开始下载
     *
     */
    public void start() {
        int cityID = Integer.parseInt(cityIDTextView.getText().toString());
        mOffline.start(cityID);
        Toast.makeText(context, "开始下载离线地图. cityID: " + cityID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        int cityID = Integer.parseInt(cityIDTextView.getText().toString());
        MKOLUpdateElement temp = mOffline.getUpdateInfo(cityID);
        if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
            mOffline.pause(cityID);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public String formatDataSize(int size) {
        String ret;
        if (size < (1024 * 1024)) {
            ret = String.format("%dKB", size / 1024);
        } else {
            ret = String.format("%.1fMB", size / (1024 * 1024.0));
        }
        return ret;
    }

    @Override
    public void onDestroy() {
        /**
         * 退出时，销毁离线地图模块
         */
        mOffline.destroy();
        super.onDestroy();
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
                    stateView.setText(String.format("%s : %d%%", update.cityName, update.ratio));
                    sendUpdateOfflineMapStateBroadcast();
                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d(TAG, String.format("add offlinemap num:%d", state));
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                // MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
            default:
                break;
        }

    }

    private void sendUpdateOfflineMapStateBroadcast() {
        Intent intent = new Intent();
        intent.setAction("com.updateOfflineMapState");
        context.sendBroadcast(intent);
    }
}
