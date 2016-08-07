package com.example.qzl.googleplay.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.qzl.googleplay.domian.AppInfo;
import com.example.qzl.googleplay.http.protovol.HomeProtocal;
import com.example.qzl.googleplay.ui.adapter.MyBaseAdapter;
import com.example.qzl.googleplay.ui.holder.BaseHolder;
import com.example.qzl.googleplay.ui.holder.HomeHolder;
import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 首页
 * Created by Qzl on 2016-08-06.
 */
public class HomeFragment extends BaseFragment {
    //private ArrayList<String> data;
    private ArrayList<AppInfo> data;

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
        //请求网络，HttpClient, HttpUrlConnection, XUtils
//        data = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            data.add("测试数据："+i);
//        }
        HomeProtocal protocal = new HomeProtocal();
        data = protocal.getData(0);//加载第一页数据
        System.out.println("data = "+data);
        return check(data);//校验数据并返回
    }

    class HomeAdapter extends MyBaseAdapter<AppInfo>{

        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }
        //返回具体的holder对象
        @Override
        public BaseHolder<AppInfo> getHolder() {
            return new HomeHolder();
        }

        //此方法在子线程
        @Override
        public ArrayList<AppInfo> onLoadMore() {
//            ArrayList<AppInfo> moreData = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                moreData.add("测试更多数据："+i);
//            }
//            SystemClock.sleep(2000);
            HomeProtocal protocal = new HomeProtocal();
            //20,40,60
            //下一页数据位置等于当前集合的大小
            ArrayList<AppInfo> moreData = protocal.getData(getListSize());
            return moreData;
        }
        //        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null){
//                //1 加载布局文件
//                convertView = UIUtils.inflate(R.layout.list_item_home);
//                holder = new ViewHolder();
//                //2 初始化控件 findViewById
//                holder.tvContent = (TextView) convertView.findViewById(R.id.tv_lih_context);
//                //3 打一个标记
//                convertView.setTag(holder);
//            }else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            //4 根据数据来刷新界面
//            String content = getItem(position);
//            holder.tvContent.setText(content);
//            return convertView;
//        }
    }

//    static class ViewHolder{
//        public TextView tvContent;
//    }
}
