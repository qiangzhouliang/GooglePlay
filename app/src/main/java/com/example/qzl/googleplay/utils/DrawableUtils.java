package com.example.qzl.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 圆角矩形
 * Created by Qzl on 2016-08-08.
 */
public class DrawableUtils {
    //获取一个形状 圆角矩形，颜色随机的
    public static GradientDrawable getGradientDrable(int color, int radius){
        //xml中定义的shape标签对应此类
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radius);//圆角半径
        shape.setColor(color);//颜色
        return shape;
    }
    //获取状态选择器
    public static StateListDrawable getSelector(Drawable normal, Drawable press){
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed},press);//按下图片
        selector.addState(new int[]{},normal);//默认图片
        return selector;
    }

    //获取状态选择器
    public static StateListDrawable getSelector(int normal, int press,int radius){
        GradientDrawable bgNormal = getGradientDrable(normal, radius);
        GradientDrawable bgPress = getGradientDrable(press, radius);
        StateListDrawable selector = getSelector(bgNormal, bgPress);
        return selector;
    }
}
