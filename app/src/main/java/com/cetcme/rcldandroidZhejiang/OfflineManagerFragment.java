package com.cetcme.rcldandroidZhejiang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
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
import java.util.List;
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

    private Toast deleteToast;

    private String TAG = "OfflineManagerFragment";


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offline_manager, container, false);
        context = this.getActivity();
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        initView();
        initBroadcast();
        initButtonClick();
        deleteToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        return view;
    }

    private void initView() {
        // 获取已下过的离线地图信息
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }

        if (localMapList.size() == 0) {
            showNoDataLayout(true);
        }

        ListView localMapListView = (ListView) view.findViewById(R.id.localmaplist);
        lAdapter = new LocalMapAdapter();
        localMapListView.setAdapter(lAdapter);

        localMapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MKOLUpdateElement e = localMapList.get(position);
                if (e.status == MKOLUpdateElement.SUSPENDED) {
                    start(e.cityID, e.cityName);
                } else if (e.status == MKOLUpdateElement.DOWNLOADING) {
                    stop(e.cityID, e.cityName);
                }
            }
        });

    }

    private void initBroadcast() {
        myUpdateOfflineMapStateReceiver = new MyUpdateOfflineMapStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.updateOfflineMapState");
        context.registerReceiver(myUpdateOfflineMapStateReceiver, filter);
    }

    private void initButtonClick() {
        view.findViewById(R.id.allDeleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allDelete();
            }
        });

        view.findViewById(R.id.allStopButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allStop();
            }
        });

        view.findViewById(R.id.allDownloadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allDownload();
            }
        });
    }

    /**
     * 开始下载
     *
     */
    private void start(int cityID, String cityName) {
        mOffline.start(cityID);
        Toast.makeText(context, "开始下载离线地图: " + cityName, Toast.LENGTH_SHORT).show();
        updateView();
    }

    private class downloadOfflineMapTask extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            mOffline.start(params[0]);
            return  true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }

    }

    /**
     * 暂停下载
     *
     */
    private void stop(int cityID, String cityName) {
        mOffline.pause(cityID);
        Toast.makeText(context, "暂停下载离线地图: " + cityName, Toast.LENGTH_SHORT).show();
        updateView();
    }

    /**
     * 删除离线地图
     */
    private void removeOfflineMap(final int cityID, final String cityName) {
        QHDialog removeDialog = new QHDialog(getActivity(), "提示", "是否删除\"" + cityName +"\"?");
        removeDialog.setPositiveButton("删除", R.drawable.single_select_logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mOffline.remove(cityID);
                deleteToast.setText("删除离线地图: " + cityName);
                deleteToast.show();
                updateView();
            }
        });
        removeDialog.setNegativeButton("取消", null);
        removeDialog.show();
    }

    /**
     * 更新状态显示
     */
    public void updateView() {
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }

        if (localMapList.size() == 0) {
            showNoDataLayout(true);
        } else {
            showNoDataLayout(false);
        }
        lAdapter.notifyDataSetChanged();
    }

    private void showNoDataLayout(boolean show) {
        LinearLayout noDataLayout = (LinearLayout) view.findViewById(R.id.no_data_layout);
        LinearLayout localMapLayout = (LinearLayout) view.findViewById(R.id.localmap_layout);
        noDataLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        localMapLayout.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public class MyUpdateOfflineMapStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            updateView();
        }
    }

    private void allDelete() {
        QHDialog removeDialog = new QHDialog(getActivity(), "提示", "是否删除所有已下载的离线地图?");
        removeDialog.setPositiveButton("删除", R.drawable.single_select_logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                for (MKOLUpdateElement e: localMapList) {
                    mOffline.remove(e.cityID);
                }
                updateView();
            }
        });
        removeDialog.setNegativeButton("取消", null);
        removeDialog.show();
    }

    private void allStop() {
        for (MKOLUpdateElement e: localMapList) {
            if (e.status == MKOLUpdateElement.DOWNLOADING) {
                mOffline.pause(e.cityID);
            }
            updateView();
        }
    }

    private void allDownload() {
        for (MKOLUpdateElement e: localMapList) {
            if (e.status == MKOLUpdateElement.SUSPENDED) {
                mOffline.start(e.cityID);
            }
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
            TextView removeTextView = (TextView) view.findViewById(R.id.removeTextView);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView update = (TextView) view.findViewById(R.id.update);
            DonutProgress donutProgress = (DonutProgress) view.findViewById(R.id.donut_progress);

            donutProgress.setUnfinishedStrokeWidth(DensityUtil.dip2px(context, 2));
            donutProgress.setFinishedStrokeWidth(DensityUtil.dip2px(context, 2));
            donutProgress.setTextSize(DensityUtil.dip2px(context, 7));
            donutProgress.setProgress(e.ratio);

            if (e.ratio == 100) {
                donutProgress.setVisibility(View.INVISIBLE);
            }
            title.setText(e.cityName);
            if (e.update) {
                update.setText("可更新");
                update.setVisibility(View.VISIBLE);
            } else {
                update.setText("最新");
                update.setVisibility(View.INVISIBLE);
            }

            removeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    removeOfflineMap(e.cityID, e.cityName);
                }
            });
        }

    }

}