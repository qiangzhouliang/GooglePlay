package com.example.qzl.googleplay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 游戏页面
 * Created by Qzl on 2016-08-06.
 */
public class GameFragment extends BaseFragment {

    @Override
    public View onCreateSuccessView() {
        TextView textView = new TextView(UIUtils.getContext());
        textView.setText("GameFragment");
        return textView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
