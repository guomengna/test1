package com.example.guomn.test1.sevice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
/**
 * Created by guomn on 2018/3/23.
 */

public class GPSService extends Service {
    // 2000ms
    private static final long minTime = 2000;

    // 最小变更距离10m
    private static final float minDistance = 10;

    String tag = this.toString();

    private LocationManager locationManager;

    private LocationListener locationListener;

    private final IBinder mBinder = new GPSServiceBinder();

    public void startService() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSServiceListener();
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance,locationListener);
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
        //
        startService();
        Log.v(tag, "GPSService Started.");
    }

    @Override
    public void onDestroy() {
        endService();
        Log.v(tag, "GPSService Ended.");
    }

    public class GPSServiceBinder extends Binder {
        GPSService getService() {
            return GPSService.this;
        }
    }
}
