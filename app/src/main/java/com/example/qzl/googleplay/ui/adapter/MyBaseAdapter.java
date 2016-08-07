package com.example.qzl.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.qzl.googleplay.ui.holder.BaseHolder;
import com.example.qzl.googleplay.ui.holder.MoreHolder;

import java.util.ArrayList;

/**
 * 对adapter的一个封装
 * Created by Qzl on 2016-08-07.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private static final int TYPE_NOMAL = 0;//正常类型
    private static final int TYPE_MORE = 1;//加载更多类型

    private ArrayList<T> data;
    public MyBaseAdapter(ArrayList<T> data){
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size() + 1;//增加加载更多布局的数量
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回布局类型个数

    @Override
    public int getViewTypeCount() {
        return 2;//返回两种类型，分别是普通类型和下拉加载更多两种类型
    }

    //返回当前位置应该展示那种布局类型
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1){
            //最后一个
            return TYPE_MORE;
        }else {
            return getInnerType();
        }
    }
    //子类可以重写此方法来更改返回的布局类型
    public int getInnerType(){
        return TYPE_NOMAL;//默认就是普通类型
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null){
            //1 加载布局文件
            // 2 初始化控件，findViewById
            //3 打标记
            //判断是否是加载更多的类型
            if (getItemViewType(position) == TYPE_MORE){
                //加载更多
                holder = new MoreHolder();
            }else {
                holder = getHolder();//子类返回具体对象
            }
        }else {
            holder = (BaseHolder) convertView.getTag();
        }
        //4 刷新数据
        if (getItemViewType(position) != TYPE_MORE) {
            holder.setData(getItem(position));
        }else {
            //加载更多布局的数据

        }
        return holder.getRootView();
    }
    //返回当前页面的holder对象，必须子类实现
    public abstract BaseHolder<T> getHolder();
}
