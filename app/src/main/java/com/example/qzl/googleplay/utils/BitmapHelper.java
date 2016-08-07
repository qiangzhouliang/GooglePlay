package com.example.qzl.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * 单例，懒汉模式
 * Created by Qzl on 2016-08-07.
 */
public class BitmapHelper  {
    private static BitmapUtils mBitmapUtils = null;
    public static BitmapUtils getBitmapUtils(){
        if (mBitmapUtils == null){
            synchronized (BitmapHelper.class){
                if (mBitmapUtils == null) {
                    mBitmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return mBitmapUtils;
    }
}
