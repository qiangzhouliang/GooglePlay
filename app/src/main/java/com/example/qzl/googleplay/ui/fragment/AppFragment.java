package com.example.qzl.googleplay.ui.fragment;

import android.view.View;

import com.example.qzl.googleplay.domian.AppInfo;
import com.example.qzl.googleplay.http.protovol.AppProtocol;
import com.example.qzl.googleplay.ui.adapter.MyBaseAdapter;
import com.example.qzl.googleplay.ui.holder.AppHolder;
import com.example.qzl.googleplay.ui.holder.BaseHolder;
import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.ui.view.MyListView;
import com.example.qzl.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 应用
 * Created by Qzl on 2016-08-06.
 */
public class AppFragment extends BaseFragment {
    private ArrayList<AppInfo> data;
    //只有成功才走此方法
    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new AppAdapter(data));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        AppProtocol protocol = new AppProtocol();
        data = protocol.getData(0);//0表示第一页数据
        return check(data);
    }

    class AppAdapter extends MyBaseAdapter<AppInfo>{

        public AppAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder() {
            return new AppHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            AppProtocol protocol = new AppProtocol();
            ArrayList<AppInfo> moreData = protocol.getData(getListSize());//0表示第一页数据
            return moreData;
        }
    }
}
