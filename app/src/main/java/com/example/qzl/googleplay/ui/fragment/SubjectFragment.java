package com.example.qzl.googleplay.ui.fragment;

import android.view.View;

import com.example.qzl.googleplay.domian.SubjectInfo;
import com.example.qzl.googleplay.http.protovol.SubjectProtocol;
import com.example.qzl.googleplay.ui.adapter.MyBaseAdapter;
import com.example.qzl.googleplay.ui.holder.BaseHolder;
import com.example.qzl.googleplay.ui.holder.SubjectHolder;
import com.example.qzl.googleplay.ui.view.LoadingPage;
import com.example.qzl.googleplay.ui.view.MyListView;
import com.example.qzl.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * 专题
 * Created by Qzl on 2016-08-06.
 */
public class SubjectFragment extends BaseFragment {

    private ArrayList<SubjectInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new SubjectAdapter(mData));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        SubjectProtocol protocol = new SubjectProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder() {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol protocol = new SubjectProtocol();
            ArrayList<SubjectInfo> moreData = protocol.getData(getListSize());
            return moreData;
        }
    }
}
