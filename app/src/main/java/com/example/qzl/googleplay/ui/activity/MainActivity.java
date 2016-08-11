package com.example.qzl.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.MenuItem;

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
    private ActionBarDrawerToggle mToggle;//抽屉的开关
    private DrawerLayout mDrawer;

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
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        initActionBar();
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

    //初始化actionBar
    private void initActionBar(){
        // 获取actionbar对象
        ActionBar actionBar = getSupportActionBar();
        // 左上角显示logo
        actionBar.setHomeButtonEnabled(true);//home处可以点击
        //actionBar.setDisplayShowHomeEnabled(true);//显示图标
        actionBar.setDisplayHomeAsUpEnabled(true);//显示左上角返回键，当和侧边栏结合时，显示三个杠图片

        //初始化抽屉的开关
        //参二是：抽屉对象，参三是左上角抽屉对象，参四：打开侧边栏描述，参五：关闭侧边栏描述(V4包)
        mToggle = new ActionBarDrawerToggle(this,mDrawer,R.drawable.ic_drawer_am, R.string.drawer_open,R.string.drawer_close);
        mToggle.syncState();//同步状态，将drawLayout和开关关联在一起
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //切换抽屉
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
