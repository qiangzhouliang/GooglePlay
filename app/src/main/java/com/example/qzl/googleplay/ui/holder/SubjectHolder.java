package com.example.qzl.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.domian.SubjectInfo;
import com.example.qzl.googleplay.http.HttpHelper;
import com.example.qzl.googleplay.utils.BitmapHelper;
import com.example.qzl.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 专题的holder
 * Created by Qzl on 2016-08-08.
 */
public class SubjectHolder extends BaseHolder<SubjectInfo> {

    private ImageView mIvPic;
    private TextView mTvTitle;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_subject);
        mIvPic = (ImageView) view.findViewById(R.id.iv_lis_pic);
        mTvTitle = (TextView) view.findViewById(R.id.tv_lis_title);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    //刷新数据
    @Override
    public void refreshView(SubjectInfo data) {
        mTvTitle.setText(data.des);
        mBitmapUtils.display(mIvPic, HttpHelper.URL + "image?name=" + data.url);
    }
}
