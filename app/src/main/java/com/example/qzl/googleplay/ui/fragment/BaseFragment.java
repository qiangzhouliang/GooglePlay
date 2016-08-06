package com.example.qzl.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * Created by Qzl on 2016-08-06.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage mLoadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        TextView view = new TextView(UIUtils.getContext());
//        //使用TextView显示当前类的类名
//        view.setText(getClass().getSimpleName());
        mLoadingPage = new LoadingPage(UIUtils.getContext()){

            @Override
            public View onCreateSuccessView() {
                //注意：此处一定要调运BaseFragment的onCreateSuccessView，否则，栈溢出
                return BaseFragment.this.onCreateSuccessView();
            }
        };
        return mLoadingPage;
    }
    //加载成功的布局必须有子类来实现
    public abstract View onCreateSuccessView();
}
