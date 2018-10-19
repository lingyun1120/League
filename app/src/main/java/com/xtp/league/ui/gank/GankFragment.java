package com.xtp.league.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtp.league.R;
import com.xtp.league.http.ApiService;
import com.xtp.league.http.BaseObserver;
import com.xtp.league.http.GankBeautyBean;
import com.xtp.library.base.BaseFragment;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.http.RxSchedulers;
import com.xtp.library.util.bar.ImmersionBar;

public class GankFragment extends BaseFragment {

    private ImmersionBar mImmersionBar;
    private RecyclerView rvView;
    private GankBeautyAdapter mAdapter;

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
        LinearLayoutManager linear = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvView.setLayoutManager(linear);
        rvView.setAdapter(mAdapter);
        rvView.setHasFixedSize(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestGankWelfare();
    }

    private void requestGankWelfare() {
        RetrofitClient.getInstance()
                .obtainService(ApiService.class)
                .getBeauty(20, 1)
                .compose(RxSchedulers.<GankBeautyBean>compose())
                .compose(this.<GankBeautyBean>bindToLifecycle())
                .subscribe(new BaseObserver<GankBeautyBean>() {
                    @Override
                    protected void onSuccess(GankBeautyBean bean) {
                        mAdapter.setData(bean.getResults());
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                    }
                });
    }
}
