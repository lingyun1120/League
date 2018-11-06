package com.xtp.league.ui.gank.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.xtp.league.App;
import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.http.ApiService;
import com.xtp.league.http.BaseObserver;
import com.xtp.league.pojo.GankDetailBean;
import com.xtp.league.util.GlideUtil;
import com.xtp.library.base.BaseActivity;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.http.RxSchedulers;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class GankDetailActivity extends BaseActivity {

    private Toolbar tToolbar;
    private ImageView ivPicture;

    private GankDetailViewModel mVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_detail_activity);

        mVM = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getApplication()).create(GankDetailViewModel.class);

        initView();
        initData();
    }

    private void initView() {
        tToolbar = findViewById(R.id.tToolbar);
        setSupportActionBar(tToolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        mImmersionBar
                .titleBar(tToolbar)
                .statusBarDarkFont(false)
                .navigationBarEnable(false)
                .init();

        ivPicture = findViewById(R.id.ivPicture);

        GlideUtil.load(this, ivPicture, getIntent().getStringExtra(Constant.KEY_IMG));
    }

    public void initData() {
        String date = getIntent().getStringExtra(Constant.KEY_DATE);
        final String[] arr = date.split("-");
        ivPicture.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVM.getGankDetail(arr[0], arr[1], arr[2]).observe(GankDetailActivity.this, new Observer<GankDetailBean>() {
                    @Override
                    public void onChanged(@Nullable GankDetailBean gankDetailBean) {
                        Logger.e("-----> onChanged " + gankDetailBean);
                    }
                });
            }
        }, 3000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
