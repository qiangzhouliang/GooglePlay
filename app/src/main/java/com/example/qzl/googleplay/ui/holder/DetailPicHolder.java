package com.example.qzl.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.domian.AppInfo;
import com.example.qzl.googleplay.http.HttpHelper;
import com.example.qzl.googleplay.utils.BitmapHelper;
import com.example.qzl.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * 应用详情页—截图
 * Created by Qzl on 2016-08-10.
 */
public class DetailPicHolder extends BaseHolder<AppInfo> {
    private ImageView[] mIvPics;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_picinfo);
        mIvPics = new ImageView[5];
        mIvPics[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        mIvPics[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        mIvPics[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        mIvPics[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        mIvPics[4] = (ImageView) view.findViewById(R.id.iv_pic5);

        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<String> screen = data.screen;
        for (int i = 0; i < 5; i++) {
            if (i < screen.size()){
                mBitmapUtils.display(mIvPics[i], HttpHelper.URL + "image?name=" + screen.get(i));
            }else {
                mIvPics[i].setVisibility(View.GONE);
            }
        }
    }
}
