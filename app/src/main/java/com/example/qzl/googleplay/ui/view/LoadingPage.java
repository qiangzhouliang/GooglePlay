package com.example.qzl.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 根据当前状态来显示不同页面的自定义控件
 *  未加载 - 加载中 - 加载失败 - 数据为空 - 加载成功
 * Created by Qzl on 2016-08-06.
 */
public class LoadingPage extends FrameLayout {
    private static final int START_LOAD_UNDO = 1;//啥都不做
    private static final int START_LOAD_LOADING = 2;//加载中
    private static final int START_LOAD_ERROR = 3;//加载失败
    private static final int START_LOAD_EMPTY = 4;//数据为空
    private static final int START_LOAD_SUCCESS = 5;//加载成功

    private int mCurrentState = START_LOAD_UNDO;//表示当前状态
    private View mLoadingPage;//加载中的布局

    public LoadingPage(Context context) {
        super(context);
        //初始化布局
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        //初始化加载中的布局
        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);//将加载中的布局添加给当前的帧布局
        }
    }
}
