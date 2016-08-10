package com.example.qzl.googleplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.domian.AppInfo;
import com.example.qzl.googleplay.http.HttpHelper;
import com.example.qzl.googleplay.utils.BitmapHelper;
import com.example.qzl.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 详情页-应用信息
 * Created by Qzl on 2016-08-09.
 */
public class DetailAppInfoHolder extends BaseHolder<AppInfo> {

    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvDownloadNum;
    private TextView mTvVersion;
    private TextView mTvDate;
    private TextView mTvSize;
    private RatingBar mRbStar;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_appinfo);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvDownloadNum = (TextView) view.findViewById(R.id.tv_download_num);
        mTvVersion = (TextView) view.findViewById(R.id.tv_version);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvSize = (TextView) view.findViewById(R.id.tv_size);
        mRbStar = (RatingBar) view.findViewById(R.id.rb_star);

        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        mBitmapUtils.display(mIvIcon, HttpHelper.URL+"image?name="+data.iconUrl);
        mTvName.setText(data.name);
        mTvDownloadNum.setText("下载量："+data.downloadNum);
        mTvVersion.setText("版本号："+data.version);
        mTvDate.setText(data.date);
        mTvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        mRbStar.setRating(data.stars);
    }
}
