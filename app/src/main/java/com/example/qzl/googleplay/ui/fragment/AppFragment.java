package com.example.qzl.googleplay.ui.fragment;

import android.view.View;

import com.example.qzl.googleplay.ui.view.LoadingPage;

/**
 * 应用
 * Created by Qzl on 2016-08-06.
 */
public class AppFragment extends BaseFragment {

    //只有成功才走此方法
    @Override
    public View onCreateSuccessView() {

        return null;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
