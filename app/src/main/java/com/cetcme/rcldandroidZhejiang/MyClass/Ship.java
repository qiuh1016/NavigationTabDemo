package com.cetcme.rcldandroidZhejiang.MyClass;

import java.io.Serializable;

/**
 * Created by qiuhong on 9/5/16.
 */
public class Ship implements Serializable {

    public String name;
    public String number;
    public double latitude;
    public double longitude;
    public boolean deviceInstall;

    public Ship() {

    }

    public Ship(String name, String number, double latitude, double longitude, boolean deviceInstall) {
        this.name = name;
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deviceInstall = deviceInstall;
    }

//    public String getName() {
//        return this.name;
//    }
//
//    public String getNumber() {
//        return this.number;
//    }

//    public LatLng getLocation() {
//        return this.location;
//    }

//    public boolean getDeviceInstall() {
//        return this.deviceInstall;
//    }

}
