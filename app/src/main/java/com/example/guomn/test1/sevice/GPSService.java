package com.example.guomn.test1.sevice;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.guomn.test1.MainActivity;
import com.example.guomn.test1.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * Created by guomn on 2018/3/23.
 *
 * 经纬度转换为位置信息，国家名字和街道名字获取不到
 */

public class GPSService extends Service {
    // 1000ms
    private static final long minTime = 5000;

    // 最小变更距离10m
    private static final float minDistance = 0;

    String tag = this.toString();

    private LocationManager locationManager;

    private Location location;

    private String locationProvider;

    private final IBinder mBinder = new GPSServiceBinder();

    Notification notification;
    private double lat,lng;

    public void startService() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new GPSServiceListener();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
            //获取到GPS_PROVIDER
//            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，如果设置为高精度，依然获取不了location。
            criteria.setAltitudeRequired(false);//不要求海拔
            criteria.setBearingRequired(false);//不要求方位
            criteria.setCostAllowed(true);//允许有花费
            criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

            //从可用的位置提供器中，匹配以上标准的最佳提供器
            locationProvider = locationManager.getBestProvider(criteria, true);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onCreate: 没有权限 ");
                return;
            }
            location = locationManager.getLastKnownLocation(locationProvider);
            Log.d(TAG, "onCreate: " + (location == null) + "..");
            if (location != null) {
                Log.i(TAG, "onCreate: location");
                //不为空,显示地理位置经纬度
                showLocation(location);
            }else{
                Log.i(TAG, "这里是GPSService,位置为空");
            }
            //监视地理位置变化
            locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, locationListener);
        }
    }

    public void endService() {
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startService();
        Log.i(tag, "GPSService Started.");

//        if (location != null) {
//            //获取当前位置，这里只用到了经纬度
//            String string = "纬度为：" + location.getLatitude() + ",经度为："
//                    + location.getLongitude();
//
//        } else {
//            Log.i("TAG", "位于GPSService中，位置为空");
//        }
    }
    private void updateWithNewLocation(Location location) {
        String coordinate;
        String addressStr = "no address \n";
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            coordinate = "Latitude：" + lat + "\nLongitude：" + lng;
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat,lng, 1);
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i)).append(" ");
                    }
                /*sb.append(address.getCountryName());
                Log.i("location", "address.getCountryName()==" + address.getCountryName());//国家名*/
                    sb.append(address.getCountryCode()).append(" ");//国家代码
                    Log.i("location", "address.getCountryCode()==" + address.getCountryCode());//国家代码
                    sb.append(address.getCountryName()).append(" ");//国家名称
                    Log.i("location", "address.getCountryName()==" + address.getCountryName());//国家名称
                    sb.append(address.getAdminArea());//省份
                    Log.i("location", "address.getAdminArea()==" + address.getAdminArea());//省份
                    sb.append(address.getLocality()).append(" ");
                    Log.i("location", "address.getLocality()==" + address.getLocality());//城市名
                    sb.append(address.getSubLocality());
                    Log.i("location", "address.getSubLocality()=2=" + address.getSubLocality());//---区名
                    sb.append(address.getThoroughfare());//街道名字，，获取不到
                    Log.i("location", "address.getThoroughfare()=2=" + address.getThoroughfare());
                    sb.append(address.getFeatureName());//建筑名
                    Log.i("location", "address.getFeatureName()=2=" + address.getFeatureName());//建筑名
                    addressStr = sb.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //如果用户没有允许app访问位置信息 则默认取沈阳浑南经纬度的数据
            lat = 41.654057;
            lng = 123.420503;
            coordinate = "Latitude：" + lat + "\nLongitude：" + lng;
        }
        Log.i("location", "经纬度为===" + coordinate);
        Log.i("location", "地址为====" + addressStr);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent gpsservice=new Intent(this,GPSService.class);
        this.startService(gpsservice);
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
//        stopForeground(true);

    }

    public class GPSServiceBinder extends Binder {
        GPSService getService() {
            return GPSService.this;
        }
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: " + provider + ".." + Thread.currentThread().getName());
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider + ".." + Thread.currentThread().getName());
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + ".." + Thread.currentThread().getName());
            //如果位置发生变化,重新显示
            showLocation(location);
        }
    };

    private void showLocation(Location location) {
        Log.d(TAG,"定位成功------->"+"location------>经度为：" + location.getLatitude() + "\n纬度为" + location.getLongitude());
        //把经纬度转换成位置
        updateWithNewLocation(location);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        notification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("test")
                .setContentText("test请保持程序在后台运行")
                .setContentIntent(pendingintent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build();
        startForeground(1, notification);
        return super.onStartCommand(intent, flags, startId);

    }

}



