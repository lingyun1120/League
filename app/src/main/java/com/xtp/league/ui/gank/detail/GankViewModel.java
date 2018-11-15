package com.xtp.league.ui.gank.detail;

import com.orhanobut.logger.Logger;
import com.xtp.league.http.ApiService;
import com.xtp.league.http.BaseObserver;
import com.xtp.league.pojo.GankDetailBean;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.http.RxUtil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GankViewModel extends ViewModel {

    private MutableLiveData<GankDetailBean> listData = new MutableLiveData<>();

    public LiveData<GankDetailBean> getGankDetail(String year, String month, String day) {
        RetrofitClient.getInstance()
                .obtainService(ApiService.GANK_URL, ApiService.class)
                .getDetail(year, month, day)
                .compose(RxUtil.<GankDetailBean>compose())
                .subscribe(new BaseObserver<GankDetailBean>() {
                    @Override
                    protected void onSuccess(GankDetailBean bean) {
                        Logger.e("---> getGankDetail " + bean);
                        listData.setValue(bean);
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                    }
                });
        return listData;
    }
}
