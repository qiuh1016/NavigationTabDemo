package com.cetcme.rcldandroidZhejiang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
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
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import com.cetcme.rcldandroidZhejiang.IconPager.BaseFragment;
import com.cetcme.rcldandroidZhejiang.MyClass.NavigationView;
import com.cetcme.rcldandroidZhejiang.MyClass.Ship;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuhong on 8/24/16.
 */
public class MapFragment extends BaseFragment implements  BaiduMap.OnMarkerClickListener {

    private View view;
    private TextureMapView mapView;
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

                if (user.getBoolean("hasLogin", false)) {

                    if (ships.size() == 0) {
                        Toast.makeText(getActivity(), "您的名下没有船只", Toast.LENGTH_SHORT).show();
                        return;
                    }

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
//        mapView = (MapView) view.findViewById(R.id.baiduMap_in_fragment_2);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout_map_fragment);
        BaiduMapOptions baiduMapOptions = new BaiduMapOptions()
                .overlookingGesturesEnabled(false)
                .rotateGesturesEnabled(false)
                .zoomControlsEnabled(true);
        mapView = new TextureMapView(this.getActivity(), baiduMapOptions);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mapView.setLayoutParams(params);

        linearLayout.addView(mapView);
        baiduMap = mapView.getMap();
        baiduMap.setOnMarkerClickListener(this);

        /**
         * 设置默认中心点
         */
        LatLng centerPoint = new LatLng(30, 122);
        MapStatus mapStatus = new MapStatus.Builder().target(centerPoint).zoom(8)
                .build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mapStatus);
        baiduMap.setMapStatus(mapStatusUpdate);

    }

    private void mapMark(Ship ship){

        //定义Maker坐标点
        LatLng point = new LatLng(ship.latitude, ship.longitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_point);
        //构建MarkerOption，用于在地图上添加Marker
        Bundle bundle = new Bundle();
//        bundle.putString("shipName", ship.name);
//        bundle.putString("shipNumber", ship.number);
//        bundle.putBoolean("deviceInstall", ship.deviceInstall);
        bundle.putSerializable("ship", ship);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .extraInfo(bundle)
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
        MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(8) //15
                .build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mapStatus);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        /**
         * 判断是否登陆
         */
        if (user.getBoolean("hasLogin", false)) {
            Bundle bundle = marker.getExtraInfo();
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(getActivity(), ShipInfoActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            MainActivity activity = (MainActivity) getActivity();
            startActivity(intent);
            activity.overridePendingTransition(R.anim.push_up_in_no_alpha, R.anim.stay);
        }
        return false;

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
        Log.i(TAG, "drawMapMark: "+ ships.toString());
        if (ships.size() == 0) {
            mapStatus(new LatLng(30, 122));
            return;
        }
        double lat = 0.0;
        double lng = 0.0;
        int count = ships.size();
        for (int i = 0; i < count; i++) {
            mapMark(ships.get(i));
            lat += ships.get(i).latitude;
            lng += ships.get(i).longitude;
        }

        LatLng mediaPoint = new LatLng(lat / count, lng / count);
//        mapStatus(mediaPoint);

//        mapStatus(new LatLng(30, 122));

    }

}
