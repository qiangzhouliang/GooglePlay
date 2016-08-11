package com.example.qzl.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.example.qzl.googleplay.R;
import com.example.qzl.googleplay.domian.AppInfo;
import com.example.qzl.googleplay.http.protocol.HomeDetailProtocol;
import com.example.qzl.googleplay.ui.holder.DeatilSafeHolder;
import com.example.qzl.googleplay.ui.holder.DetailAppInfoHolder;
import com.example.qzl.googleplay.ui.holder.DetailDesHolder;
import com.example.qzl.googleplay.ui.holder.DetailDownloadHolder;
import com.example.qzl.googleplay.ui.holder.DetailPicHolder;
import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.utils.UIUtils;

/**
 * 应用详情页
 */
public class HomeDetailActivity extends AppCompatActivity {

    private LoadingPage mLoadingPage;
    private String mPackageName;
    private AppInfo mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingPage = new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };

        setContentView(mLoadingPage);//直接将一个view对象设置给activity

        //获取从HomeFragment传递过来的包名
        mPackageName = getIntent().getStringExtra("packageName");
        //开始加载网络数据
        mLoadingPage.loadData();

        initActionBar();
    }

    //初始化actionBar
    private void initActionBar(){
        // 获取actionbar对象
        ActionBar actionBar = getSupportActionBar();
        // 左上角显示logo
        actionBar.setHomeButtonEnabled(true);//home处可以点击
        //actionBar.setDisplayShowHomeEnabled(true);//显示图标
        actionBar.setDisplayHomeAsUpEnabled(true);//显示左上角返回键，当和侧边栏结合时，显示三个杠图片

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public View onCreateSuccessView(){
        //初始化成功的布局
        View view = UIUtils.inflate(R.layout.page_home_detail);
        //初始化应用信息模块
        FrameLayout flDetailAppInfo = (FrameLayout) view.findViewById(R.id.fl_detail_appdetail);
        //动态给帧布局填充页面
        DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
        flDetailAppInfo.addView(appInfoHolder.getRootView());
        appInfoHolder.setData(mData);

        //初始化安全描述模块
        FrameLayout flDetailSafe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
        DeatilSafeHolder safeHolder = new DeatilSafeHolder();
        //设置布局
        flDetailSafe.addView(safeHolder.getRootView());
        //设置数据
        safeHolder.setData(mData);

        //初始化截图模块
        HorizontalScrollView hscPic = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pic);
        DetailPicHolder picHolder = new DetailPicHolder();
        hscPic.addView(picHolder.getRootView());
        picHolder.setData(mData);

        //初始化描述模块
        FrameLayout flDetailDes = (FrameLayout) view.findViewById(R.id.fl_detail_des);
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        flDetailDes.addView(detailDesHolder.getRootView());
        detailDesHolder.setData(mData);

        //初始化下载模块
        FrameLayout flDetailDownload = (FrameLayout) view.findViewById(R.id.fl_detail_download);
        DetailDownloadHolder downloadHolder = new DetailDownloadHolder();
        flDetailDownload.addView(downloadHolder.getRootView());
        downloadHolder.setData(mData);
        return view;
    }

    public LoadingPage.ResultState onLoad(){
        //请求网络，加载数据
        HomeDetailProtocol protocol = new HomeDetailProtocol(mPackageName);
        mData = protocol.getData(0);
        if (mData != null) {
            return LoadingPage.ResultState.STATE_SUCCESS;
        }else {
            return LoadingPage.ResultState.STATE_ERROR;
        }
    }
}
