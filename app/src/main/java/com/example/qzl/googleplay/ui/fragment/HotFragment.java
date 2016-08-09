package com.example.qzl.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qzl.googleplay.http.protocol.HotProtocol;
import com.example.qzl.googleplay.ui.view.FlowLayout;
import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.utils.DrawableUtils;
import com.example.qzl.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * 排行
 * Created by Qzl on 2016-08-06.
 */
public class HotFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {
        //支持上下滑动
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flow = new FlowLayout(UIUtils.getContext());
        int padding = UIUtils.dip2px(10);
        flow.setPadding(padding,padding,padding,padding);//设置内边距
        flow.setHorizontalSpacing(UIUtils.dip2px(6));//水平间距
        flow.setVerticalSpacing(8);//竖直间距
        for (int i = 0; i < mData.size(); i++) {
            final String keyword = mData.get(i);
            TextView view = new TextView(UIUtils.getContext());
            view.setText(keyword);
            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);//18sp
            view.setPadding(padding,padding,padding,padding);
            view.setGravity(Gravity.CENTER);
            //随机颜色
            // r g b  0-255->30-230,颜色值不能太小获太大，从而避免颜色值过亮或者过暗
            Random random = new Random();
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            int color = 0xffcecece;//按下后偏白的背景色
//            GradientDrawable bgNormal = DrawableUtils.getGradientDrable(Color.rgb(r, g, b), UIUtils.dip2px(6));
//            GradientDrawable bgPress = DrawableUtils.getGradientDrable(color, UIUtils.dip2px(6));
//            StateListDrawable selector = DrawableUtils.getSelector(bgNormal, bgPress);
            StateListDrawable selector = DrawableUtils.getSelector(Color.rgb(r, g, b), color, UIUtils.dip2px(6));
            view.setBackgroundDrawable(selector);
            flow.addView(view);
            //只有设置点击事件，状态选择器才起作用
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), keyword, Toast.LENGTH_SHORT).show();
                }
            });
        }
        scrollView.addView(flow);

        return scrollView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HotProtocol protocol = new HotProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }
}
