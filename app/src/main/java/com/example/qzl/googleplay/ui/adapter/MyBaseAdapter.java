package com.example.qzl.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.qzl.googleplay.ui.holder.BaseHolder;

import java.util.ArrayList;

/**
 * 对adapter的一个封装
 * Created by Qzl on 2016-08-07.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private ArrayList<T> data;
    public MyBaseAdapter(ArrayList<T> data){
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null){
            //1 加载布局文件
            // 2 初始化控件，findViewById
            //3 打标记
            holder = getHolder();//子类返回具体对象
        }else {
            holder = (BaseHolder) convertView.getTag();
        }
        //4 刷新数据
        holder.setData(getItem(position));
        return holder.getRootView();
    }
    //返回当前页面的holder对象，必须子类实现
    public abstract BaseHolder<T> getHolder();
}
