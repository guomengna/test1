package com.example.guomn.test1.sevice;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by guomn on 2018/3/24.
 */

public class RefreshDataService extends Service {
    String tag = this.toString();
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        //

        Log.v(tag, "GPSService Started.");
    }

    @Override
    public void onDestroy() {

        Log.v(tag, "GPSService Ended.");
    }
}
