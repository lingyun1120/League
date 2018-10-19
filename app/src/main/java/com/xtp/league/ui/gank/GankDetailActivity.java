package com.xtp.league.ui.gank;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.http.ApiService;
import com.xtp.league.http.BaseObserver;
import com.xtp.league.http.GankDetailBean;
import com.xtp.league.util.GlideUtil;
import com.xtp.library.base.BaseActivity;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.http.RxSchedulers;

public class GankDetailActivity extends BaseActivity {

    private Toolbar tToolbar;
    private ImageView ivPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_detail_activity);

        initView();
        String date = getIntent().getStringExtra(Constant.KEY_DATE);
        String[] arr = date.split("-");
        requestGankDetail(arr[0], arr[1], arr[2]);
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

    private void requestGankDetail(String year, String month, String day) {
        RetrofitClient.getInstance()
                .obtainService(ApiService.class)
                .getDetail(year, month, day)
                .compose(RxSchedulers.<GankDetailBean>compose())
                .compose(this.<GankDetailBean>bindToLifecycle())
                .subscribe(new BaseObserver<GankDetailBean>() {
                    @Override
                    protected void onSuccess(GankDetailBean bean) {
                        Logger.e("");
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                    }
                });
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
