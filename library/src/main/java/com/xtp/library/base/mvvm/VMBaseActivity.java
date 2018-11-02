package com.xtp.library.base.mvvm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.xtp.library.util.bar.ImmersionBar;

public class VMBaseActivity extends AppCompatActivity {
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
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
