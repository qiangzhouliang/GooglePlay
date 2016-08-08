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
 * 应用holder
 * Created by Qzl on 2016-08-07.
 */
public class AppHolder extends BaseHolder<AppInfo> {

    private TextView mTvName,mTvSize,mTvDes;
    private ImageView mIvIcon;
    private RatingBar mRbStar;

    private BitmapUtils mBitmapUtils;
    @Override
    public View initView() {
        //1 加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        //2 初始化控件
        mTvName = (TextView) view.findViewById(R.id.tv_lih_name);
        mTvSize = (TextView) view.findViewById(R.id.tv_lih_size);
        mTvDes = (TextView) view.findViewById(R.id.tv_lih_des);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_lih_icon);
        mRbStar = (RatingBar) view.findViewById(R.id.rb_lih_star);

        //mBitmapUtils = new BitmapUtils(UIUtils.getContext());
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        mTvName.setText(data.name);
        mTvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        mTvDes.setText(data.des);
        mRbStar.setRating(data.stars);
        //加载图片来显示
        mBitmapUtils.display(mIvIcon, HttpHelper.URL + "image?name="+data.iconUrl);
    }

}
