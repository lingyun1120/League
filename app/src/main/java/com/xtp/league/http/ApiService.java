package com.xtp.league.http;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("data/福利/{count}/{page}")
    Flowable<GankBeautyBean> getBeauty(@Path("count") int count, @Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Flowable<GankDetailBean> getDetail(@Path("year") String year, @Path("month") String month, @Path("day") String day);
}
