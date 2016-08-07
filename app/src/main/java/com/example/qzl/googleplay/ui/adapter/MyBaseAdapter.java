package com.example.qzl.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 对adapter的一个封装
 * Created by Qzl on 2016-08-07.
 */
public class MyBaseAdapter<T> extends BaseAdapter {
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
        return null;
    }
}
