package com.example.guomn.test1.receiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import com.example.guomn.test1.MainActivity;
import com.example.guomn.test1.sevice.RefreshDataService;
import com.example.guomn.test1.sevice.TestService;

/**
 * Created by guomn on 2018/3/24.
 *
 * 利用广播实现app开机自启
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //启动Activity
        Intent bootIntent = new Intent(context, MainActivity.class);
        bootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(bootIntent);

        /* 服务开机自启动 */
        Intent service = new Intent(context, TestService.class);
        //为了避免被强制停止后接收不到广播
        service.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.startService(service);


        //开启应用，参数为需要自动启动的应用的包名
        Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("com.example.guomn.test1");
        context.startActivity(intent1);
    }

}