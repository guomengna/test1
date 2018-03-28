package com.example.guomn.test1.sevice;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * Created by guomn on 2018/3/23.
 */

public class GPSService extends Service {
    // 1000ms
    private static final long minTime = 1000;

    // 最小变更距离10m
    private static final float minDistance = 0;

    String tag = this.toString();

    private LocationManager locationManager;

    private Location location;

    private String locationProvider;

    private final IBinder mBinder = new GPSServiceBinder();

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

        if (location != null) {
            //获取当前位置，这里只用到了经纬度
            String string = "纬度为：" + location.getLatitude() + ",经度为："
                    + location.getLongitude();
            Log.i("TAG", string);
        } else {
            Log.i("TAG", "位于GPSService中，位置为空");
        }
    }


    @Override
    public void onDestroy() {
//        endService();
//        Log.i(tag, "GPSService Ended.");
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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

}



