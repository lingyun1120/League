package com.xtp.league.ui.douban;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xtp.league.App;
import com.xtp.league.R;
import com.xtp.league.pojo.DoubanTopBean;
import com.xtp.library.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoubanFragment extends BaseFragment implements Observer<DoubanTopBean> {

    private RecyclerView rvView;
    private DoubanTopAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private int mStart = 0;
    private int mCount = 10;

    private DoubanViewModel mVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVM = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getApplication()).create(DoubanViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.douban_fragment, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        rvView = view.findViewById(R.id.rvView);

        mAdapter = new DoubanTopAdapter(getContext());
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(linear);
        rvView.setAdapter(mAdapter);
        rvView.setHasFixedSize(true);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                mStart = 0;
                requestDoubanTop();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                mStart = mStart + mCount;
                requestDoubanTop();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestDoubanTop();
    }

    private void requestDoubanTop() {
        mVM.getTopMovies(this, mStart, mCount).observe(this, this);
    }

    @Override
    public void onChanged(DoubanTopBean bean) {
        Logger.e("-----> onChanged " + bean.getStart());
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore(true);
        if (mStart == 0) {
            mAdapter.setData(bean.getSubjects());
        } else {
            mAdapter.addData(bean.getSubjects());
        }
    }
}
