package com.example.guomn.test1;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.widget.Toast;

import com.example.guomn.test1.sevice.GPSService;
import com.example.guomn.test1.sevice.TestService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "哈哈，我成功启动了！", Toast.LENGTH_LONG).show();
        Log.e("AutoRun","哈哈，我成功启动了！");
        System.out.println("MainActivity  OnCreate()....");
        System.out.println("准备开启服务");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                System.out.print("test");
                Log.i("TAG", "这个一条信息，位于Text位置");
            }
        }, 1000, 5000);

        Intent intent = new Intent(MainActivity.this, TestService.class);
        startService(intent);

        Intent intent1 = new Intent(MainActivity.this, GPSService.class);
        startService(intent1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }

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
}
