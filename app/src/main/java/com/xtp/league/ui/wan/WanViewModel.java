package com.xtp.league.ui.wan;

import com.xtp.league.http.ApiService;
import com.xtp.league.http.BaseObserver;
import com.xtp.league.pojo.WanArticleBean;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.http.RxUtil;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WanViewModel extends ViewModel {

    private MutableLiveData<WanArticleBean> listData = new MutableLiveData<>();

    public LiveData<WanArticleBean> getWanArticleList(LifecycleOwner owner, int page) {
        RetrofitClient.getInstance()
                .obtainService(ApiService.WAN_URL, ApiService.class)
                .getArticleList(page)
                .compose(RxUtil.compose())
                .as(RxUtil.bindLifecycle(owner))
                .subscribe(new BaseObserver<WanArticleBean>() {
                    @Override
                    protected void onSuccess(WanArticleBean bean) {
                        listData.setValue(bean);
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                    }
                });
        return listData;
    }
}
