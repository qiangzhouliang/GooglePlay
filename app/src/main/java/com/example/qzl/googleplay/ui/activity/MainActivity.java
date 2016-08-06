package com.example.qzl.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.ui.fragment.BaseFragment;
import com.example.qzl.googleplay.ui.fragment.FragmentFactory;
import com.example.qzl.googleplay.ui.view.PagerTab;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 主页面
 * 当项目和appcompat关联在一起时，就必须在清单文件中设置Theme.AppCompat的主题，否则，奔溃
 */
public class MainActivity extends BaseActivity {

    private PagerTab mPagerTab;
    private ViewPager mViewPager;
    private MyAdapter mAdapter;
    private String[] mTabNames;//加载页签标题的数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPagerTab = (PagerTab) findViewById(R.id.pt_am_pagetTab);
        mViewPager = (ViewPager) findViewById(R.id.vp_am_viewPager);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPagerTab.setViewPager(mViewPager);//将指针和viewpager绑定在一起
        //用来切换界面
        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.createFragment(position);
                //开始加载数据
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * FragmentPagerAdapter : 是PagerAdapter的子类，如果viewPager的页面是fragment的话，就继承此类
     */
    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UIUtils.getStringArray(R.array.tab_names);
        }

        //返回页签的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }

        //返回当前页面位置的fragment对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
//            fragment.loadData();
            return fragment;
        }
        //fragment的数量
        @Override
        public int getCount() {
            return mTabNames.length;
        }
    }
}
