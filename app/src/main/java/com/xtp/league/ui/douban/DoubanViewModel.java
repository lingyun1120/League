package com.xtp.league.ui.douban;

import com.xtp.league.http.ApiService;
import com.xtp.league.http.BaseObserver;
import com.xtp.league.pojo.DoubanTopBean;
import com.xtp.league.pojo.MovieDetailBean;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.http.RxUtil;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DoubanViewModel extends ViewModel {

    private MutableLiveData<DoubanTopBean> listData = new MutableLiveData<>();
    private MutableLiveData<MovieDetailBean> detailData = new MutableLiveData<>();

    public LiveData<DoubanTopBean> getTopMovies(LifecycleOwner owner, int start, int count) {
        RetrofitClient.getInstance()
                .obtainService(ApiService.DOUBAN_URL, ApiService.class)
                .getMovieTop(start, count)
                .compose(RxUtil.compose())
                .as(RxUtil.bindLifecycle(owner))
                .subscribe(new BaseObserver<DoubanTopBean>() {
                    @Override
                    protected void onSuccess(DoubanTopBean bean) {
                        listData.setValue(bean);
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                    }
                });
        return listData;
    }

    public LiveData<MovieDetailBean> getMovieDetail(LifecycleOwner owner, String id) {
        RetrofitClient.getInstance()
                .obtainService(ApiService.DOUBAN_URL, ApiService.class)
                .getMovieDetail(id)
                .compose(RxUtil.compose())
                .as(RxUtil.bindLifecycle(owner))
                .subscribe(new BaseObserver<MovieDetailBean>() {
                    @Override
                    protected void onSuccess(MovieDetailBean bean) {
                        detailData.setValue(bean);
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                    }
                });
        return detailData;
    }
}
