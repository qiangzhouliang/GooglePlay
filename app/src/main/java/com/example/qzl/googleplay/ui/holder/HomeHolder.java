package com.example.qzl.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 首页holder
 * Created by Qzl on 2016-08-07.
 */
public class HomeHolder extends BaseHolder<String> {

    private TextView mTvContent;

    @Override
    public View initView() {
        //1 加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        //2 初始化控件
        mTvContent = (TextView) view.findViewById(R.id.tv_lih_context);
        return view;
    }

    @Override
    public void refreshView(String data) {
        mTvContent.setText(data);
    }

}
