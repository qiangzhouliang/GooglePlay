package com.example.qzl.googleplay.ui.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.domian.AppInfo;
import com.example.qzl.googleplay.utils.UIUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * 详情页应用描述
 * Created by Qzl on 2016-08-10.
 */
public class DetailDesHolder extends BaseHolder<AppInfo> {

    private TextView mTvDes;
    private TextView mTvAuthor;
    private ImageView mIvArrow;
    private RelativeLayout mRlToggle;
    private LinearLayout.LayoutParams mParams;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_desinfo);
        mTvDes = (TextView) view.findViewById(R.id.tv_detail_des);
        mTvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
        mIvArrow = (ImageView) view.findViewById(R.id.iv_arrow);

        mRlToggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);

        mRlToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return view;
    }

    private boolean isOpen = false;
    private void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();
        ValueAnimator animator = null;
        if (isOpen){
            //关闭
            isOpen = false;
            if (longHeight > shortHeight) {//只有描述信息大于7行才启动动画
                animator = ValueAnimator.ofInt(longHeight, shortHeight);
            }
        }else {
            //打开
            isOpen = true;
            if (longHeight > shortHeight) {//只有描述信息大于7行才启动动画
                animator = ValueAnimator.ofInt(shortHeight, longHeight);
            }
        }
        if (animator != null) {
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int height = (int) valueAnimator.getAnimatedValue();
                    mParams.height = height;
                    mTvDes.setLayoutParams(mParams);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    //ScrollView要滑动到最低部
                    final ScrollView scrollView = getScrollView();
                    //为了运行更加安全和稳定，可以将滑动到底部方法放到消息队列中执行
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    if (isOpen){
                        mIvArrow.setImageResource(R.drawable.arrow_up);
                    }else {
                        mIvArrow.setImageResource(R.drawable.arrow_down);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.setDuration(500);
            animator.start();
        }
    }

    @Override
    public void refreshView(AppInfo data) {
        mTvDes.setText(data.des);
        mTvAuthor.setText(data.author);

        //设置默认显示七行的高度值
        int shortHeight = getShortHeight();
        mParams = (LinearLayout.LayoutParams) mTvDes.getLayoutParams();
        mParams.height = shortHeight;
        mTvDes.setLayoutParams(mParams);

    }
    //获取7行textView的高度
    public int getShortHeight(){
        //模拟一个TextView，设置最大行数为7行，计算改虚拟TextView的高度，从而知道tvDes在展示7行时，应该多高
        int width = mTvDes.getMeasuredWidth();//宽度
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getData().des);//设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//文字大小一致
        view.setMaxLines(7);//最大行数为7行

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变，是一个确定值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);//高度包裹内容，当包裹内容时，参一表示尺寸的最大值，暂写2000，也可以是屏幕高度

        //开始测量
        view.measure(widthMeasureSpec,heightMeasureSpec);
        return view.getMeasuredHeight();//返回测量后的高度
    }

    //获取完整textView的高度
    public int getLongHeight(){
        //模拟一个TextView，设置最大行数为7行，计算改虚拟TextView的高度，从而知道tvDes在展示7行时，应该多高
        int width = mTvDes.getMeasuredWidth();//宽度
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getData().des);//设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//文字大小一致
//        view.setMaxLines(7);//最大行数为7行

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变，是一个确定值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);//高度包裹内容，当包裹内容时，参一表示尺寸的最大值，暂写2000，也可以是屏幕高度

        //开始测量
        view.measure(widthMeasureSpec,heightMeasureSpec);
        return view.getMeasuredHeight();//返回测量后的高度
    }

    //获取ScrollView,一层一层往上找，直到找到，注意：一定要保证父控件获祖宗控件有ScrollView
    private ScrollView getScrollView(){
        ViewParent parent = mTvDes.getParent();
        while (!(parent instanceof ScrollView)){
            parent = parent.getParent();
        }
        return (ScrollView)parent;
    }
}
