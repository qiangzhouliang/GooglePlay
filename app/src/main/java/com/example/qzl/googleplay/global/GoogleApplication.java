package com.example.qzl.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 自定义application，进行全局初始化
 * Created by Qzl on 2016-08-06.
 */
public class GoogleApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mMainThreadId;//当前线程id（此处是主线程id）

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        //当前线程id（此处是主线程id）
        mMainThreadId = android.os.Process.myTid();
    }

    public static Context getmContext() {
        return mContext;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static int getmMainThreadId() {
        return mMainThreadId;
    }
}
