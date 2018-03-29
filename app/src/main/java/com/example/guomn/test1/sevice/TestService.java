package com.example.guomn.test1.sevice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.guomn.test1.MainActivity;
import com.example.guomn.test1.R;

/**
 * Created by guomn on 2018/3/23.
 */

public class TestService extends Service {

    Notification notification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return Service.START_STICKY;
        //设置为前台进程
//        PendingIntent pendingintent = PendingIntent.getActivity(this, 0,
//                new Intent(this, MainActivity.class), 0);
//        notification = new Notification.Builder(this)
//                .setAutoCancel(true)
//                .setContentTitle("test")
//                .setContentText("test请保持程序在后台运行")
//                .setContentIntent(pendingintent)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setWhen(System.currentTimeMillis())
//                .build();
//        startForeground(1, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(TestService.this, TestService.class);
        startService(intent);
        Intent gpsservice=new Intent(this,GPSService.class);
        this.startService(gpsservice);
//        stopForeground(true);
    }

    private void methodInMyService() {
        Toast.makeText(getApplicationContext(), "服务里的方法执行了。。。", Toast.LENGTH_SHORT).show();
    }

    /**
     * 该类用于在onBind方法执行后返回的对象，
     * 该对象对外提供了该服务里的方法
     */
    private class MyBinder extends Binder implements IMyBinder {

        @Override
        public void invokeMethodInMyService() {
            methodInMyService();
        }
    }


}

