package com.xtp.league.http;

import com.xtp.league.pojo.BaseBean;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

public abstract class BaseObserver<T extends BaseBean> extends DisposableSubscriber<T> {

    @Override
    public void onNext(T value) {
        if (value.isSuccess()) {
            onSuccess(value);
        } else {
            onFailed(value.getErrorCode(), value.getErrorMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            onFailed(httpException.code(), httpException.message());
        } else {
            onFailed(-1, e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);

    protected abstract void onFailed(int code, String msg);
}