package com.cetcme.zytyumin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import com.cetcme.zytyumin.IconPager.BaseFragment;
import com.cetcme.zytyumin.MyClass.NavigationView;
import com.cetcme.zytyumin.MyClass.Ship;

import org.mozilla.javascript.tools.debugger.Main;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuhong on 8/24/16.
 */
public class MapFragment extends BaseFragment implements  BaiduMap.OnMarkerClickListener {

    private View view;
    private MapView mapView;
    private BaiduMap baiduMap;

    private SharedPreferences user;

    private String TAG = "MapFragment";

    private Marker comMarker;
    private InfoWindow mInfoWindow;
    private boolean infoWindowIsShow = true;

    private MyLoginStateReceiver myLoginStateReceiver;

    private List<Ship> ships;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, null, false);

        user = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        initNavigationView();
        initMapView();
        initLoginBroadcast();

        if (user.getBoolean("hasLogin", false)) {
            drawMapMark();
        }

        return view;
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_main_in_fragment_map);
        navigationView.setTitle("地图");
        navigationView.setRightView(R.drawable.icon_list);
        navigationView.setClickCallback(new NavigationView.ClickCallback() {

            @Override
            public void onRightClick() {
                Log.i("main","点击了右侧按钮!");

                if (ships.size() == 0) {
                    Toast.makeText(getActivity(), "您的名下没有船只", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user.getBoolean("hasLogin", false)) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ShipActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("openFromMapFragment", true);
                    bundle.putSerializable("ships", (Serializable) ships);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    MainActivity activity = (MainActivity) getActivity();
                    activity.overridePendingTransition(R.anim.push_left_in_no_alpha, R.anim.push_left_out_no_alpha);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    MainActivity activity = (MainActivity) getActivity();
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
                }
            }

            @Override
            public void onBackClick() {
                Log.i("main","点击了左侧按钮!");
            }
        });
    }

    private void initMapView() {
        mapView = (MapView) view.findViewById(R.id.baiduMap_in_fragment_2);
        baiduMap = mapView.getMap();
        baiduMap.setOnMarkerClickListener(this);
    }

    private void mapMark(LatLng latLng, String shipInfo){

        //定义Maker坐标点
        LatLng point = latLng;
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_point);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .title(shipInfo)
                .position(point)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);

        /*

        //创建InfoWindow展示的view
        Button button = new Button(getActivity());
        button.setBackgroundResource(R.drawable.infowindow_white);
        button.setTextSize(13);
        button.setGravity(Gravity.CENTER);
        button.setPadding(20,20,20,40);
        button.setText(shipInfo);
        button.setTextColor(0xFF7D7D7D);
        button.setGravity(Gravity.LEFT);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiduMap.hideInfoWindow();
                infoWindowIsShow = false;
            }
        });

        if (infoWindowIsShow) {
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            mInfoWindow = new InfoWindow(button, point, -bitmap.getBitmap().getHeight());

            //显示InfoWindow
            baiduMap.showInfoWindow(mInfoWindow);
            infoWindowIsShow = true;
        }

        */


    }

    private void mapStatus(LatLng latLng) {
        //设置中心点 和显示范围
        MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(9) //15
                .build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mapStatus);
        baiduMap.setMapStatus(mapStatusUpdate);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        String shipName = marker.getTitle();

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("shipName", shipName); //"浙三渔04529"
        intent.putExtras(bundle);
        intent.setClass(getActivity(), ShipInfoActivity.class);
        startActivity(intent);
        return false;

//        if (marker.equals(comMarker)) {
//            if (infoWindowIsShow) {
//                baiduMap.hideInfoWindow();
//            } else {
//                baiduMap.showInfoWindow(mInfoWindow);
//            }
//            infoWindowIsShow = !infoWindowIsShow;
//        }
//        return false;
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
                drawMapMark();
            } else {
                baiduMap.clear();
            }

        }
    }

    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myLoginStateReceiver);
    }

    private void drawMapMark() {

        ships = ((MainActivity) getActivity()).getShips();
        if (ships.size() == 0) {
            mapStatus(new LatLng(30, 122));
            return;
        }
        double lat = 0.0;
        double lng = 0.0;
        int count = ships.size();
        for (int i = 0; i < count; i++) {
            mapMark(new LatLng(ships.get(i).latitude, ships.get(i).longitude), ships.get(i).name);
            lat += ships.get(i).latitude;
            lng += ships.get(i).longitude;
        }

        LatLng mediaPoint = new LatLng(lat / count, lng / count);
        mapStatus(mediaPoint);

    }

}
