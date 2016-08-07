package com.example.qzl.googleplay.ui.holder;

import android.view.View;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * Created by Qzl on 2016-08-07.
 */
public class MoreHolder extends BaseHolder<Integer> {
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        return view;
    }

    @Override
    public void refreshView(Integer data) {

    }
}
