package com.example.qzl.googleplay.ui.fragment;

import android.view.View;

import com.example.qzl.googleplay.domian.CategoryInfo;
import com.example.qzl.googleplay.http.protocol.CategoryProtocol;
import com.example.qzl.googleplay.ui.adapter.MyBaseAdapter;
import com.example.qzl.googleplay.ui.holder.BaseHolder;
import com.example.qzl.googleplay.ui.holder.CategoryHolder;
import com.example.qzl.googleplay.ui.holder.TitleHolder;
import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.ui.view.MyListView;
import com.example.qzl.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 分类
 * Created by Qzl on 2016-08-06.
 */
public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new CategoryAdapter(mData));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocol protocol = new CategoryProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class CategoryAdapter extends MyBaseAdapter<CategoryInfo> {

        public CategoryAdapter(ArrayList<CategoryInfo> data) {
            super(data);
        }

        //返回listView类型个数
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;//在原来的基础上增加一种标题
        }

        @Override
        public int getInnerType(int position) {
            //判断是否是标题类型还是普通分类类型
            CategoryInfo info = mData.get(position);
            if (info.isTitle) {
                //返回标题类型
                return super.getInnerType(position) + 1;//在原来的基础上再加1；：注意：将TYPE_NORMAL修改为1
            } else {
                //返回普通类型
                return super.getInnerType(position);
            }
        }

        //判断是否有加载更多
        @Override
        public boolean hasMore() {
            return false;//没有更多数据，需要隐藏加载更多的布局
        }

        @Override
        public BaseHolder<CategoryInfo> getHolder(int position) {
            //判断是标题类型还是普通类型来返回不同的holder
            CategoryInfo info = mData.get(position);
            if (info.isTitle){
                return new TitleHolder();
            }else {
                return new CategoryHolder();
            }
        }

        @Override
        public ArrayList<CategoryInfo> onLoadMore() {
            return null;
        }
    }
}
