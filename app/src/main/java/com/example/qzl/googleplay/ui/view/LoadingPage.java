package com.example.qzl.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 根据当前状态来显示不同页面的自定义控件
 * 未加载 - 加载中 - 加载失败 - 数据为空 - 加载成功
 * Created by Qzl on 2016-08-06.
 */
public abstract class LoadingPage extends FrameLayout {
    private static final int START_LOAD_UNDO = 1;//啥都不做
    private static final int START_LOAD_LOADING = 2;//加载中
    private static final int START_LOAD_ERROR = 3;//加载失败
    private static final int START_LOAD_EMPTY = 4;//数据为空
    private static final int START_LOAD_SUCCESS = 5;//加载成功

    private int mCurrentState = START_LOAD_UNDO;//表示当前状态
    private View mLoadingPage;//加载中的布局
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

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
        //初始化加载失败布局
        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);
            //点击重试事件
            Button btnRetry = (Button) mErrorPage.findViewById(R.id.btn_pe_retry);
            btnRetry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //重新加载数据
                    loadData();
                }
            });
            addView(mErrorPage);
        }
        //初始化数据为空的布局
        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }

        showRightPage();
    }

    /**
     * 根据当前状态决定显示那个布局
     */
    private void showRightPage() {
        /*if (mCurrentState == START_LOAD_UNDO || mCurrentState == START_LOAD_LOADING){
            mLoadingPage.setVisibility(VISIBLE);
        }else {
            mLoadingPage.setVisibility(GONE);
        }*/
        mLoadingPage.setVisibility((mCurrentState == START_LOAD_UNDO || mCurrentState == START_LOAD_LOADING) ? VISIBLE : GONE);
        mErrorPage.setVisibility(mCurrentState == START_LOAD_ERROR ? VISIBLE : GONE);
        mEmptyPage.setVisibility(mCurrentState == START_LOAD_EMPTY ? VISIBLE : GONE);
        //当成功布局为空并且当前状态为空，我们才加载成功布局
        if (mSuccessPage == null && mCurrentState == START_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }
        if (mSuccessPage != null) {
            mSuccessPage.setVisibility(mCurrentState == START_LOAD_SUCCESS ? VISIBLE : GONE);
        }
    }

    /**
     * 开始加载数据
     */
    public void loadData() {
        if (mCurrentState != START_LOAD_LOADING) {//如果当前没有加载，就开始加载数据
            mCurrentState = START_LOAD_LOADING;
            new Thread() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    //运行在主线程
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                mCurrentState = resultState.getState();//网络加载结束后，更新网络状态
                                //根据最新的状态来刷新页面
                                showRightPage();
                            }
                        }
                    });
                }
            }.start();
        }
    }

    /**
     * 加载成功后显示的布局
     *
     * @return
     */
    public abstract View onCreateSuccessView();

    /**
     * 加载网络数据,返回值来表示请求网络结束后的状态
     *
     * @return
     */
    public abstract ResultState onLoad();

    public enum ResultState {
        STATE_SUCCESS(START_LOAD_SUCCESS),
        STATE_EMPTY(START_LOAD_EMPTY),
        STATE_ERROR(START_LOAD_ERROR);
        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
