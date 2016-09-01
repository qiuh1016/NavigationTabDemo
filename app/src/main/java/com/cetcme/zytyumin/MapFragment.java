package com.cetcme.zytyumin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

/**
 * Created by qiuhong on 8/24/16.
 */
public class MapFragment extends BaseFragment implements  BaiduMap.OnMarkerClickListener {

    private View view;
    private MapView mapView;
    private BaiduMap baiduMap;

    private NavigationView navigationView;

    private Marker comMarker;
    private InfoWindow mInfoWindow;
    private boolean infoWindowIsShow = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, null, false);

        initNavigationView();
        initMapView();

        mapMark(new LatLng(30, 122), "浙嘉渔1234");
        mapMark(new LatLng(31, 123), "浙嘉渔1234");

        return view;
    }

    private void initNavigationView() {
        navigationView = (NavigationView) view.findViewById(R.id.nav_main_in_fragment_map);
        navigationView.setTitle("地图");
        navigationView.setRightView(R.drawable.icon_search);
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

    private void initMapView() {
        mapView = (MapView) view.findViewById(R.id.baiduMap_in_fragment_2);
        baiduMap = mapView.getMap();
        baiduMap.setOnMarkerClickListener(this);
    }

    private void mapMark(LatLng latLng, String shipInfo){

        mapStatus(latLng);

        //定义Maker坐标点
        LatLng point = latLng;
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_point);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        comMarker = (Marker) baiduMap.addOverlay(option);

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
        if (marker.equals(comMarker)) {
            if (infoWindowIsShow) {
                baiduMap.hideInfoWindow();
            } else {
                baiduMap.showInfoWindow(mInfoWindow);
            }
            infoWindowIsShow = !infoWindowIsShow;
        }
        return false;
    }
}
