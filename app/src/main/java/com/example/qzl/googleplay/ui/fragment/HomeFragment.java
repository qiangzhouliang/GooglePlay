package com.example.qzl.googleplay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 首页
 * Created by Qzl on 2016-08-06.
 */
public class HomeFragment extends BaseFragment {

    //如果加载数据成功，就回调此方法,此方法在主线程
    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UIUtils.getContext());
        //使用TextView显示当前类的类名
        view.setText(getClass().getSimpleName());
        return view;
    }

    //运行在子线程,可以直接执行耗时网络操作
    @Override
    public LoadingPage.ResultState onLoad() {
        //请求网络
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
