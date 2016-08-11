package com.example.qzl.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.qzl.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 自定义排行控件
 * Created by Qzl on 2016-08-10.
 */
public class MyFlowLayout extends ViewGroup {
    public int mUsedWidth;//当前行已使用的宽度
    private Line mLine;//当前行对象

    private ArrayList<Line> mLineList = new ArrayList<>();//维护所有行的集合

    private static final int MAX_LINE = 100;//最大行数

    //水平间距
    private int mHorizontalSpacing = UIUtils.dip2px(6);
    private int mVerticalSpacing = UIUtils.dip2px(8);//竖直间距

    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = l + getPaddingLeft();
        int top = t + getPaddingTop();

        //遍历所有行对象，设置每行位置
        for (int i = 0; i < mLineList.size(); i++) {
            Line line = mLineList.get(i);
            line.layout(left,top);
            top += line.mMaxHeight + mVerticalSpacing;//更新top值
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取有效宽度
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        //获取有效高度
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        //获取宽高模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();//获取所有子控件的数量
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //如果父控件是确定模式，子控件包裹内容，否则子控件模式和父控件一致
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, (widthMode == MeasureSpec.EXACTLY) ? MeasureSpec.AT_MOST : widthMode);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, (heightMode == MeasureSpec.EXACTLY) ? MeasureSpec.AT_MOST : heightMode);

            //开始测量
            childView.measure(childWidthMeasureSpec, heightMeasureSpec);
            //如果当前行对象为空，初始化一个对象
            if (mLine == null) {
                mLine = new Line();
            }
            int childWidth = childView.getMeasuredWidth();
            //当前已使用宽度要增加一个子控件宽度
            mUsedWidth += childWidth;
            //判断是否超出边界
            if (mUsedWidth < width) {
                //给当前行添加子控件
                mLine.addView(childView);
                //没有超出边界
                mUsedWidth += mHorizontalSpacing;//增加一个水平间距
                if (mUsedWidth > width) {
                    //增加水平间距后，超出了边界，此时应该换行
                    boolean success = newLine();
                    if (!success) {
                        break;//如果创建行失败，就结束循环，不在添加
                    }
                }
            } else {
                //已超出边界
                //1 当前没有任何元素，一旦添加当前子控件，就超出边界（子控件很长）
                if (mLine.getChildCount() == 0) {
                    //强制添加到当前行
                    mLine.addView(childView);
                    //换行
                    if (!newLine()) {
                        break;
                    }
                } else {
                    //2 当前有控件，一旦添加，就超出边界
                    //先换行
                    if (!newLine()) {
                        break;
                    }
                    mLine.addView(childView);
                    //更新已使用宽度
                    mUsedWidth += childWidth + mHorizontalSpacing;
                }
            }
        }

        //保存最后一行的行对象
        if (mLine != null && mLine.getChildCount() != 0 && !mLineList.contains(mLine)) {
            mLineList.add(mLine);
        }

        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);//控件整体宽度

        int totalHeight = 0;//控件整体高度
        for (int i = 0; i < mLineList.size(); i++) {
            Line line = mLineList.get(i);
            totalHeight += line.mMaxHeight;
        }

        totalHeight += (mLineList.size() - 1) * mVerticalSpacing;//增加竖直间距
        totalHeight += getPaddingTop() + getPaddingBottom();//增加上下边距

        //根据最新的宽高来测量控件的大小
        setMeasuredDimension(totalWidth,totalHeight);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //表示换行
    private boolean newLine() {
        mLineList.add(mLine);//保存上一行数据
        if (mLineList.size() < MAX_LINE) {
            //可以继续添加
            mLine = new Line();
            mUsedWidth = 0;//已使用宽度清零
            return true;//创建成功
        }
        return false;//创建失败
    }

    //每一行的对象封装
    class Line {

        private int mTotalWidth;//当前所有控件总宽度
        private int mMaxHeight;//当前控件的高度（以最高的控件为准）

        //当前行所有子控件集合
        private ArrayList<View> mChildViewList = new ArrayList<>();

        //添加一个子控件
        public void addView(View view) {
            mChildViewList.add(view);

            //宽度要增加
            mTotalWidth += view.getMeasuredWidth();

            int height = view.getMeasuredHeight();

            mMaxHeight = mMaxHeight < height ? height : mMaxHeight;
        }

        //获取孩子的数量
        public int getChildCount() {
            return mChildViewList.size();
        }
        //子控件 设置位置
        public void layout(int left,int top){
            int childCount = getChildCount();
            //将剩余空间分配给每个子控件
            int validWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();//屏幕总有效宽度
            //剩余宽度
            int surplusWidth = validWidth - mTotalWidth - (getChildCount() - 1) * mHorizontalSpacing;
            if (surplusWidth >= 0){
                //有剩余空间
                int space = (int) ((float)surplusWidth / getChildCount() +0.5f);//平均每个控件分配的大小

                //重新测量子控件
                for (int i = 0; i < childCount; i++) {
                    View childView = mChildViewList.get(i);

                    int measuredWidth = childView.getMeasuredWidth();
                    int measuredHeight = childView.getMeasuredHeight();

                    measuredWidth += space;//宽度增加

                    int makeMeasureSpecWidth = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
                    int makeMeasureSpecHeight = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
                    //重新测量控件
                    childView.measure(makeMeasureSpecWidth,makeMeasureSpecHeight);

                    //当控件比较矮时，需要居中展示，竖直方向需要向下有一定偏移
                    int topOffSet = (mMaxHeight - measuredHeight)/2;

                    if (topOffSet < 0){
                        topOffSet = 0;
                    }

                    //设置子控件的位置
                    childView.layout(left,top + topOffSet,left + measuredWidth,top + topOffSet + measuredHeight);
                    left += measuredWidth + mHorizontalSpacing;//更新left值
                }

            }else {
                //没有剩余空间
                //这个控件很长，占满整行
                View childView = mChildViewList.get(0);
                childView.layout(left,top,left + childView.getMeasuredWidth(),top + childView.getMeasuredHeight());
            }
        }
    }

}
