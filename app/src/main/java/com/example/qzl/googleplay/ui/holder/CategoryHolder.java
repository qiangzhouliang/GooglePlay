package com.example.qzl.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.domian.CategoryInfo;
import com.example.qzl.googleplay.http.HttpHelper;
import com.example.qzl.googleplay.utils.BitmapHelper;
import com.example.qzl.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Qzl on 2016-08-09.
 */
public class CategoryHolder extends BaseHolder<CategoryInfo> {

    private TextView mTvName1, mTvName2, mTvName3;
    private ImageView mIvIcon1, mIvIcon2, mIvIcon3;
    private LinearLayout mLlGrid1, mLlGrid2, mLlGrid3;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_category);
        mTvName1 = (TextView) view.findViewById(R.id.tv_name1);
        mTvName2 = (TextView) view.findViewById(R.id.tv_name2);
        mTvName3 = (TextView) view.findViewById(R.id.tv_name3);

        mIvIcon1 = (ImageView) view.findViewById(R.id.iv_icon1);
        mIvIcon2 = (ImageView) view.findViewById(R.id.iv_icon2);
        mIvIcon3 = (ImageView) view.findViewById(R.id.iv_icon3);

        mLlGrid1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
        mLlGrid2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
        mLlGrid3 = (LinearLayout) view.findViewById(R.id.ll_grid3);

        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        mTvName1.setText(data.name1);
        mTvName2.setText(data.name2);
        mTvName3.setText(data.name3);

        mBitmapUtils.display(mIvIcon1, HttpHelper.URL + "image?name=" + data.url1);
        mBitmapUtils.display(mIvIcon2, HttpHelper.URL + "image?name=" + data.url2);
        mBitmapUtils.display(mIvIcon3, HttpHelper.URL + "image?name=" + data.url3);
    }
}
