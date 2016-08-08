package com.example.qzl.googleplay.ui.fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.qzl.googleplay.http.protovol.RecommendProtocol;
import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.ui.view.fly.StellarMap;
import com.example.qzl.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * 推荐
 * Created by Qzl on 2016-08-06.
 */
public class RecommendFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {
        StellarMap stellar = new StellarMap(UIUtils.getContext());
        stellar.setAdapter(new RecommendAdapter());
        //随机的方式,将控件划分为9行6列的随机方式
        stellar.setRegularity(6, 9);
        int padding = UIUtils.dip2px(10);
        //设置内边距
        stellar.setInnerPadding(padding, padding, padding, padding);
        //设置默认页面,第一组数据
        stellar.setGroup(0, true);
        return stellar;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        RecommendProtocol protocol = new RecommendProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class RecommendAdapter implements StellarMap.Adapter {

        //返回组的个数
        @Override
        public int getGroupCount() {
            return 2;
        }

        //返回某组成员个数
        @Override
        public int getCount(int group) {
            int count = mData.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                //最后一页，将除不尽，余下来的数量追加在最后一页，保证数据完整不丢失
                count += mData.size() % getGroupCount();
            }
            return count;
        }

        //初始化布局
        @Override
        public View getView(int group, int position, View convertView) {
            //因为position每组都会从0开始，所以需要将前面几组数据的个数加起来，才能确定确定当前组数据的角标位置
            position += (group) * getCount(group - 1);
            String keyword = mData.get(position);
            TextView view = new TextView(UIUtils.getContext());
            view.setText(keyword);
            //随机大小(16-25之间)
            Random random = new Random();
            int size = 16 + random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
            //随机颜色
            // r g b  0-255->30-230,颜色值不能太小获太大，从而避免颜色值过亮或者过暗
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);
            view.setTextColor(Color.rgb(r,g,b));
            return view;
        }

        //返回下一组的id
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            System.out.println("isZoomIn = " + isZoomIn);
            if (isZoomIn) {
                //往下滑加载上一页
                if (group > 0) {
                    group--;
                } else {
                    //跳到最后一页
                    group = getGroupCount() - 1;
                }
            } else {
                //往上滑加载下一页
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    //跳到最后一页
                    group = 0;
                }
            }
            return group;
        }
    }
}
