package com.example.qzl.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.domian.CategoryInfo;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 分类模块标题holder
 * Created by Qzl on 2016-08-09.
 */
public class TitleHolder extends BaseHolder<CategoryInfo>{

    private TextView mTvTitle;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        mTvTitle.setText(data.title);
    }
}
