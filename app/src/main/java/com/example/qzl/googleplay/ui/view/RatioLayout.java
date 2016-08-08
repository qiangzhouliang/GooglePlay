package com.example.qzl.googleplay.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.utils.LogUtils;

/**
 * 自定义控件，按照比例来决定布局高度
 * Created by Qzl on 2016-08-08.
 */
public class RatioLayout extends FrameLayout {

    private float mRatio;

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性值
//        attrs.getAttributeFloatValue("","ratio",-1);
        //当自定义属性时，系统会自动生成属性相关id，此id通过R.styleable来引用
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        // id = 属性名_具体属性字段名称（此id系统自动生成）
        mRatio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
        typedArray.recycle();//回收typedArray，提高性能
        LogUtils.d("RatioLayout 中 ：ratio = "+ mRatio);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.d("RatioLayout中：widthMeasureSpec："+widthMeasureSpec+"heightMeasureSpec"+heightMeasureSpec);
        //1.获取宽度
        //2. 根据宽度和比例ratio，计算控件的高度
        //3. 重新测量控件
        int width = MeasureSpec.getSize(widthMeasureSpec);//获取宽度值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获取宽度模式
        //MeasureSpec.AT_MOST; 至多模式，控件有多大就显示多大 wrap_content
        //MeasureSpec.EXACTLY; 确定值，类似于宽高写死成dip，match_parent
        //MeasureSpec.UNSPECIFIED; 未指定模式，动态来测量

        int height = MeasureSpec.getSize(heightMeasureSpec);//获取高度值
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取高度模式
        //宽度确定,高度不确定，Ratio合法，才要计算高度值
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && mRatio > 0){
            //图片宽度 = 控件宽度 - 左侧内边距 - 右侧内边距
            int imageWidth = width - getPaddingLeft() - getPaddingRight();
            //图片高度 = 图片宽度/宽高比例
            int imageHeight = (int) (imageWidth / mRatio + 0.5);
            //控件高度 = 图片高度 + 上侧内边距 + 下册内边距
            height = imageHeight + getPaddingTop() + getPaddingBottom();
            //根据最新的高度来重新生成heightMeasureSpec（高度模式确定），
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }
        //按照最新的高度来测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
