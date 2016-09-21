package com.cetcme.rcldandroidZhejiang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.cetcme.rcldandroidZhejiang.MyClass.CustomDialog;
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
public class OfflineManagerFragment extends Fragment implements MKOfflineMapListener {

    /**
     * 已下载的离线地图信息列表
     */
    private ArrayList<MKOLUpdateElement> localMapList = null;
    private LocalMapAdapter lAdapter = null;

    private MKOfflineMap mOffline = null;

    private View view;
    private Context context;
    private MyUpdateOfflineMapStateReceiver myUpdateOfflineMapStateReceiver;

    private String TAG = "OfflineManagerFragment";


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offline_manager, container, false);
        context = this.getActivity();
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        initView();
        initBroadcast();
        return view;
    }


    private void initView() {
        // 获取已下过的离线地图信息
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }

        ListView localMapListView = (ListView) view.findViewById(R.id.localmaplist);
        lAdapter = new LocalMapAdapter();
        localMapListView.setAdapter(lAdapter);

    }

    private void initBroadcast() {
        myUpdateOfflineMapStateReceiver = new MyUpdateOfflineMapStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.updateOfflineMapState");
        context.registerReceiver(myUpdateOfflineMapStateReceiver, filter);
    }

    /**
     * 开始下载
     *
     */
    public void start(int cityID) {
        mOffline.start(cityID);
        Toast.makeText(context, "开始下载离线地图. cityID: " + cityID, Toast.LENGTH_SHORT)
                .show();
        updateView();
    }

    /**
     * 暂停下载
     *
     */
    public void stop(int cityID) {
        mOffline.pause(cityID);
        Toast.makeText(context, "暂停下载离线地图. cityID: " + cityID, Toast.LENGTH_SHORT)
                .show();
        updateView();
    }

    /**
     * 删除离线地图
     *
     */
    public void remove(int cityID, String cityName) {
        removeOfflineMap(cityID, cityName);
    }

    private void removeOfflineMap(final int cityID, final String cityName) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("是否删除\"" + cityName +"\"?");
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mOffline.remove(cityID);
                Toast.makeText(context, "删除离线地图: " + cityName, Toast.LENGTH_SHORT).show();
                updateView();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 更新状态显示
     */
    public void updateView() {
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }
        lAdapter.notifyDataSetChanged();
    }


    public class MyUpdateOfflineMapStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            updateView();
        }
    }


//    @Override
//    public void onPause() {
//        int cityID = Integer.parseInt(cityIDTextView.getText().toString());
//        MKOLUpdateElement temp = mOffline.getUpdateInfo(cityID);
//        if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
//            mOffline.pause(cityID);
//        }
//        super.onPause();
//    }

    @Override
    public void onDestroy() {
        /**
         * 退出时，销毁离线地图模块
         */
        mOffline.destroy();
        context.unregisterReceiver(myUpdateOfflineMapStateReceiver);
        super.onDestroy();
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
                    updateView();
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

    /**
     * 离线地图管理列表适配器
     */
    public class LocalMapAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return localMapList.size();
        }

        @Override
        public Object getItem(int index) {
            return localMapList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup arg2) {
            MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
            view = View.inflate(context, R.layout.offline_localmap_list, null);
            initViewItem(view, e);
            return view;
        }

        void initViewItem(View view, final MKOLUpdateElement e) {
            Button remove = (Button) view.findViewById(R.id.remove);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView update = (TextView) view.findViewById(R.id.update);
            TextView ratio = (TextView) view.findViewById(R.id.ratio);
            DonutProgress donutProgress = (DonutProgress) view.findViewById(R.id.donut_progress);

            donutProgress.setUnfinishedStrokeWidth(DensityUtil.dip2px(context, 2));
            donutProgress.setFinishedStrokeWidth(DensityUtil.dip2px(context, 2));
            donutProgress.setTextSize(DensityUtil.dip2px(context, 7));
            donutProgress.setProgress(e.ratio);

            ratio.setText(e.ratio + "%");
            title.setText(e.cityName);
            if (e.update) {
                update.setText("可更新");
            } else {
                update.setText("最新");
            }

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    removeOfflineMap(e.cityID, e.cityName);
                }
            });
        }

    }

}