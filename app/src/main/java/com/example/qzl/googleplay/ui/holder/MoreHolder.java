package com.example.qzl.googleplay.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * Created by Qzl on 2016-08-07.
 */
public class MoreHolder extends BaseHolder<Integer> {
    //加载更多的几种状态
    //1 可以加载更多
    //2 加载更多失败
    //3 没有更多数据了

    public static final int STATE_MORE_MORE = 1;
    public static final int STATE_MORE_ERROR = 2;
    public static final int STATE_MORE_NONE = 3;
    private LinearLayout mLlLoadMore;
    private TextView mTv_loadError;

    public MoreHolder(boolean hasMore) {
        //如果有更多数据，状态为STATE_MORE_MORE，否则为STATE_MORE_NONE，将此状态传递给父类的打他，父类会同时刷新界面
        setData(hasMore ? STATE_MORE_MORE : STATE_MORE_NONE);//setData结束后，一定会调refreshView
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        mLlLoadMore = (LinearLayout) view.findViewById(R.id.ll_lim_more);
        mTv_loadError = (TextView) view.findViewById(R.id.tv_lim_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data) {
            case STATE_MORE_MORE:
                //显示加载更多
                mLlLoadMore.setVisibility(View.VISIBLE);
                mTv_loadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE:
                //隐藏加载更多
                mLlLoadMore.setVisibility(View.GONE);
                mTv_loadError.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                //显示加载失败的布局
                mLlLoadMore.setVisibility(View.GONE);
                mTv_loadError.setVisibility(View.VISIBLE);
                break;
        }
    }
}
