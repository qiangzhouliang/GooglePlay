package com.example.qzl.googleplay.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.http.HttpHelper;
import com.example.qzl.googleplay.utils.BitmapHelper;
import com.example.qzl.googleplay.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * 首页轮播条holder
 * Created by Qzl on 2016-08-09.
 */
public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

    private ViewPager mViewPager;
    private ArrayList<String> data;
    private LinearLayout mLlContainer;

    private int mPreviousPos;//上个圆点位置

    @Override
    public View initView() {
        //创建根布局，相对布局
        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
        //初始化布局参数，根布局上层控件是litView，所以要使用listView定义的LayoutParams
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
        rlRoot.setLayoutParams(params);

        //viewPager
        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlRoot.addView(mViewPager, vpParams);//吧viewPager添加给相对布局

        //初始化指示器
        mLlContainer = new LinearLayout(UIUtils.getContext());
        mLlContainer.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置内边距
        int padding = UIUtils.dip2px(10);
        mLlContainer.setPadding(padding, padding, padding, padding);

        //添加规则，设置展示位置
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//底部对其
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);//右对齐

        //添加布局
        rlRoot.addView(mLlContainer, llParams);
        return rlRoot;
    }

    @Override
    public void refreshView(final ArrayList<String> data) {
        this.data = data;
        //填充viewPager的数据
        mViewPager.setAdapter(new HomeHeaderAdapter());
        mViewPager.setCurrentItem(data.size() * 10000);

        //初始化指示器
        for (int i = 0; i < data.size(); i++) {
            ImageView point = new ImageView(UIUtils.getContext());
            point.setImageResource(R.drawable.indicator_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i == 0) {
                //第一个默认选中
                point.setImageResource(R.drawable.indicator_selected);
            } else {
                params.leftMargin = UIUtils.dip2px(4);
            }
            point.setLayoutParams(params);
            mLlContainer.addView(point);
        }

        //设置viewpager的监听事件
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % data.size();
                //当前点被选中
                ImageView point = (ImageView) mLlContainer.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);

                //上个点变为不选中
                ImageView prePoint = (ImageView) mLlContainer.getChildAt(mPreviousPos);
                prePoint.setImageResource(R.drawable.indicator_normal);
                mPreviousPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //UIUtils.getHandler().postDelayed();
        HomeHeaderTask task = new HomeHeaderTask();
        task.start();
    }

    class HomeHeaderTask implements Runnable {
        //开始启动
        public void start() {
            //移出之前发送的所有的消息，避免消息重发
            UIUtils.getHandler().removeCallbacksAndMessages(null);

            UIUtils.getHandler().postDelayed(this, 3000);
        }

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);
            //继续发消息，实现内循环
            UIUtils.getHandler().postDelayed(this, 3000);
        }
    }

    class HomeHeaderAdapter extends PagerAdapter {

        private BitmapUtils mBitmapUtils;

        public HomeHeaderAdapter() {
            mBitmapUtils = BitmapHelper.getBitmapUtils();
        }

        @Override
        public int getCount() {
            //return data.size();
            return Integer.MAX_VALUE;//实现轮播
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % data.size();
            String url = data.get(position);
            ImageView view = new ImageView(UIUtils.getContext());
            mBitmapUtils.display(view, HttpHelper.URL + "image?name=" + url);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
