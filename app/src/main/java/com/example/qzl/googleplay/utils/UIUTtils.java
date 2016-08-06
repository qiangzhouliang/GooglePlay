package com.example.qzl.googleplay.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.example.qzl.googleplay.global.GoogleApplication;

/**
 * Created by Qzl on 2016-08-06.
 */
public class UIUTtils {
    /**
     * 获取context的方法
     * @return
     */
    public static Context getContext(){
        return GoogleApplication.getmContext();
    }

    public static Handler getHandler(){
        return GoogleApplication.getmHandler();
    }

    public static int getMainThreadId(){
        return GoogleApplication.getmMainThreadId();
    }

    ///////////////////////加载资源文件/////////////////////////////////
    //获取字符串
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }
    //获取字符串数组
    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }
    //获取图片
    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }
    //获取颜色
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }
    //获取尺寸
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);//返回集体的像素值
    }

    ///////////////////////dp和px的转换/////////////////////////////////
    public static int dip2px(float dip){
        float density = getContext().getResources().getDisplayMetrics().density;//获得设备密度
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px){
        float density = getContext().getResources().getDisplayMetrics().density;//获得设备密度
        return  px / density ;
    }
    ///////////////////////加载布局文件/////////////////////////////////
    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }

    ///////////////////////判断是否运行在主线程/////////////////////////////////
    public static boolean isRunOnUIThread(){
        //获取当前线程id，如果当前线程id和主线程id相同，那么当前就是主线程
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()){
            return true;
        }
        return false;
    }
    //运行在主线程的方法
    public static void runOnUIThread(Runnable r){
        if (isRunOnUIThread()){
            //已经是主线程，直接运行
            r.run();
        }else {
            //如果只子线程，我们就借助handler运行在主线程
            getHandler().post(r);
        }
    }
}
