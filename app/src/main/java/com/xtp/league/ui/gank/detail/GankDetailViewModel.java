package com.xtp.league.ui.gank.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.orhanobut.logger.Logger;
import com.xtp.league.http.ApiService;
import com.xtp.league.http.BaseObserver;
import com.xtp.league.pojo.GankDetailBean;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.http.RxSchedulers;

public class GankDetailViewModel extends ViewModel {

    private MutableLiveData<GankDetailBean> liveData = new MutableLiveData<>();

    public LiveData<GankDetailBean> getGankDetail(String year, String month, String day) {
        RetrofitClient.getInstance()
                .obtainService(ApiService.class)
                .getDetail(year, month, day)
                .compose(RxSchedulers.<GankDetailBean>compose())
                .subscribe(new BaseObserver<GankDetailBean>() {
                    @Override
                    protected void onSuccess(GankDetailBean bean) {
                        Logger.e("---> getGankDetail " + bean);
                        liveData.setValue(bean);
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                    }
                });
        return liveData;
    }
}
