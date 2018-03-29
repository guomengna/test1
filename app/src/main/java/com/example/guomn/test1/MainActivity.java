package com.example.guomn.test1;


import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.example.guomn.test1.sevice.GPSService;
import com.example.guomn.test1.sevice.TestService;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    private LocationManager mLocationManager;//位置管理器

    private LocationProvider provider;
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "哈哈，我成功启动了！", Toast.LENGTH_LONG).show();
        Log.e("AutoRun","哈哈，我成功启动了！");
        System.out.println("MainActivity  OnCreate()....");
        System.out.println("准备开启服务");

        int i = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]);
        if (i != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 321);
        }

        Intent intent = new Intent(MainActivity.this, TestService.class);
        startService(intent);

        Intent intent1 = new Intent(MainActivity.this, GPSService.class);
        startService(intent1);

//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.print("test");
//                Log.i("TAG", "这是一条信息，位于Text位置");
//            }
//        }, 1000, 5000);

//        if(ContextCompat.checkSelfPermission(MainActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//            Log.i("TAG","运行定位程序");
//            //获取到位置管理器实例
//            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            //获取到GPS_PROVIDER
//            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////            if (location != null) {
////                //获取当前位置，这里只用到了经纬度
////                String string = "纬度为：" + location.getLatitude() + ",经度为："
////                        + location.getLongitude();
////                Log.i("TAG", string);
////            }else{
////                Log.i("TAG","位置为空");
////            }
//
//            //侦听位置发生变化，2000毫秒更新一次，位置超过8米也更新一次
//            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000, 0, locationListener);
//
//            //更新位置信息显示到TextView
//            updata(location);
//        }else {
//            Log.i("TAG","没有运行定位程序，因为API23条件不满足");
//        }

//        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//            //获取到位置管理器实例
//            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//            //获取到GPS_PROVIDER
//            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////            //  获取passive Location Provider
////            LocationProvider passiveProvider =mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
////            //  获取gps Location Provider
////            LocationProvider gpsProvider =mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
////            //  获取network Location Provider
////            LocationProvider passiveProvider =mLocationManager.getLastKnownLocation (LocationManager.NETWORK_PROVIDER);
//            if (location != null) {
//                //获取当前位置，这里只用到了经纬度
//                String string = "纬度为：" + location.getLatitude() + ",经度为："
//                        + location.getLongitude();
//                Log.i("TAG", string);
//            }
//
//            //侦听位置发生变化，2000毫秒更新一次，位置超过8米也更新一次
//            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000, 8, locationListener);
//
//            //更新位置信息显示到TextView
//            updata(location);
//
//            Intent intent = new Intent(MainActivity.this, TestService.class);
//            startService(intent);
//
//            Intent intent1 = new Intent(MainActivity.this, GPSService.class);
//            startService(intent1);
//        }
    }

//    private void getlocationGps(){
//        //此处的判定是主要问题，API23之后需要先判断之后才能调用locationManager中的方法
//
//        //包括这里的getLastKnewnLocation方法和requestLocationUpdates方法
//        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
//            //获取定位服务
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            //获取当前可用的位置控制器
//            List<String> list = locationManager.getProviders(true);
//
//            if (list.contains(LocationManager.GPS_PROVIDER)) {
//                //是否为GPS位置控制器
//                provider=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
//                //是否为网络位置控制器
//                provider = locationManager.getLastKnownLocation (LocationManager.NETWORK_PROVIDER);
//
//            } else {
//                Toast.makeText(this, "请检查网络或GPS是否打开",Toast.LENGTH_LONG).show();
//                return;
//            }
//            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (location != null) {
//                //获取当前位置，这里只用到了经纬度
//                String string = "纬度为：" + location.getLatitude() + ",经度为："
//                        + location.getLongitude();
//            }
//            //绑定定位事件，监听位置是否改变
//            //第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
//            //第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器
//            locationManager.requestLocationUpdates(provider, 2000, 2, locationListener);
//        }
//    }


    private void updata(Location location){
        if(location != null){
            StringBuilder sb = new StringBuilder();
            sb.append("实时的位置信息:\n");
            sb.append("经度:");
            sb.append(location.getLongitude());
            sb.append("\n纬度:");
            sb.append(location.getLatitude());
            sb.append("\b高度:");
            sb.append(location.getAltitude());
            sb.append("\n速度：");
            sb.append(location.getSpeed());
            sb.append("\n方向：");
            sb.append(location.getBearing());
            Log.i("TAG", sb.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(MainActivity.this, TestService.class);
        startService(intent);

        Intent intent1 = new Intent(MainActivity.this, GPSService.class);
        startService(intent1);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            Log.d("weijie", "onRequestPermissionsResult: ");
        }
    }
//    void getLocation(){
//        LocationManager mLocationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
//
//        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5*60*1000, 1, locationListener);
//        }
//    }

    public LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updata(location);
            mLocationManager.removeUpdates(locationListener);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

//    //关闭时解除监听器
//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
//        //api23需要这样写
//        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
//            if (mLocationManager != null) {
//                mLocationManager.removeUpdates(locationListener);
//            }
//        }
//    }

    /**
     * 返回app运行状态
     * 1:程序在前台运行
     * 2:程序在后台运行
     * 3:程序未启动
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public int getAppSatus(Context context, String pageName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(pageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(pageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(MainActivity.this, TestService.class);
        startService(intent);

        Intent intent1 = new Intent(MainActivity.this, GPSService.class);
        startService(intent1);
    }
}
