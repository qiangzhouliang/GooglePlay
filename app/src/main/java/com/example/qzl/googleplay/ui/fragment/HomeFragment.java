package com.example.qzl.googleplay.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.ui.adapter.MyBaseAdapter;
import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 首页
 * Created by Qzl on 2016-08-06.
 */
public class HomeFragment extends BaseFragment {
    private ArrayList<String> data;

    //如果加载数据成功，就回调此方法,此方法在主线程
    @Override
    public View onCreateSuccessView() {
//        TextView view = new TextView(UIUtils.getContext());
//        //使用TextView显示当前类的类名
//        view.setText(getClass().getSimpleName());
        ListView view = new ListView(UIUtils.getContext());
        view.setAdapter(new HomeAdapter(data));
        return view;
    }

    //运行在子线程,可以直接执行耗时网络操作
    @Override
    public LoadingPage.ResultState onLoad() {
        //请求网络
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("测试数据："+i);
        }
        return LoadingPage.ResultState.STATE_SUCCESS;
    }

    class HomeAdapter extends MyBaseAdapter<String>{

        public HomeAdapter(ArrayList<String> data) {
            super(data);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                convertView = UIUtils.inflate(R.layout.list_item_home);
                holder = new ViewHolder();
                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_lih_context);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            String content = getItem(position);
            holder.tvContent.setText(content);
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvContent;
    }
}
