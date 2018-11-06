package com.xtp.league.ui.gank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xtp.league.R;
import com.xtp.league.http.ApiService;
import com.xtp.league.http.BaseObserver;
import com.xtp.league.pojo.GankBeautyBean;
import com.xtp.library.base.BaseFragment;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.http.RxSchedulers;
import com.xtp.library.util.RxUtil;
import com.xtp.library.util.bar.ImmersionBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GankFragment extends BaseFragment {

    private ImmersionBar mImmersionBar;
    private RecyclerView rvView;
    private GankBeautyAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private int mPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gank_fragment, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        rvView = view.findViewById(R.id.rvView);

        mAdapter = new GankBeautyAdapter(getContext());
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(linear);
        rvView.setAdapter(mAdapter);
        rvView.setHasFixedSize(true);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                mPage = 1;
                requestGankWelfare();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                mPage++;
                requestGankWelfare();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestGankWelfare();
    }

    private void requestGankWelfare() {
        RetrofitClient.getInstance()
                .obtainService(ApiService.class)
                .getBeauty(15, mPage)
                .compose(RxSchedulers.<GankBeautyBean>compose())
                .as(RxUtil.<GankBeautyBean>bindLifecycle(this))
                .subscribe(new BaseObserver<GankBeautyBean>() {
                    @Override
                    protected void onSuccess(GankBeautyBean bean) {
                        refreshLayout.finishRefresh(true);
                        refreshLayout.finishLoadMore(true);
                        if (mPage == 1) {
                            mAdapter.setData(bean.getResults());
                        } else {
                            mAdapter.addData(bean.getResults());
                        }
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                        refreshLayout.finishRefresh(false);
                        refreshLayout.finishLoadMore(false);
                    }
                });

    }
}
