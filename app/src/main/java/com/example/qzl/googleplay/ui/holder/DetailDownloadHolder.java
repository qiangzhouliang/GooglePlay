package com.example.qzl.googleplay.ui.holder;

import android.view.View;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.domian.AppInfo;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 详情页-下载模块
 * Created by Qzl on 2016-08-11.
 */
public class DetailDownloadHolder extends BaseHolder<AppInfo> {
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

    }
}
