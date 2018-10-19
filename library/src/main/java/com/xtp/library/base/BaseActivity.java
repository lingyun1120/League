package com.xtp.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xtp.library.util.MemoryLeakUtil;
import com.xtp.library.util.bar.ImmersionBar;

public class BaseActivity extends RxAppCompatActivity {

    protected ImmersionBar mImmersionBar;

    public Context getThis() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d("----Activity onCreate---- " + this);
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Logger.d("----Activity onSaveInstanceState---- " + this);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Logger.d("----Activity onRestoreInstanceState---- " + this);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Logger.d("----Activity onDestroy---- " + this);
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();

        MemoryLeakUtil.HWLastSrvViewLeakFix();
//        MemoryLeakUtil.HwChangeButtonWindowCtrlFix(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onNetworkChanged(int netWorkStates) {
    }
}
