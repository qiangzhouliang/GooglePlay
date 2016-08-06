package com.example.qzl.googleplay.ui.fragment;

import android.view.View;

import com.example.qzl.googleplay.ui.view.LoadingPage;

/**
 * 游戏
 * Created by Qzl on 2016-08-06.
 */
public class GameFragment extends BaseFragment {

    @Override
    public View onCreateSuccessView() {
        return null;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_EMPTY;
    }
}
