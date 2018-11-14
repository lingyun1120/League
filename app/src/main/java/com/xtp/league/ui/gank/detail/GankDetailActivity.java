package com.xtp.league.ui.gank.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.Logger;
import com.xtp.league.App;
import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.pojo.GankDetailBean;
import com.xtp.league.util.GlideUtil;
import com.xtp.library.base.BaseActivity;
import com.xtp.library.util.Util;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GankDetailActivity extends BaseActivity implements Observer<GankDetailBean> {

    private AppBarLayout ablAppBar;
    private Toolbar tToolbar;
    private ImageView ivPicture;
    private RecyclerView rvList;

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
        ablAppBar = findViewById(R.id.ablAppBar);
        rvList = findViewById(R.id.rvList);

        setSupportActionBar(tToolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        mImmersionBar
                .titleBar(tToolbar)
                .statusBarDarkFont(true)
                .navigationBarEnable(false)
                .init();

        ablAppBar.addOnOffsetChangedListener((appBarLayout, i) -> {
            float ratio = Math.abs(i) * 1.0f / Util.dip2px(360);
        });

        ivPicture = findViewById(R.id.ivPicture);

        GlideUtil.load(this, ivPicture, getIntent().getStringExtra(Constant.KEY_IMG));
    }

    public void initData() {
        String date = getIntent().getStringExtra(Constant.KEY_DATE);
        if (TextUtils.isEmpty(date)) return;
        final String[] arr = date.split("-");
        mVM.getGankDetail(arr[0], arr[1], arr[2]).observe(GankDetailActivity.this, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChanged(GankDetailBean gankDetailBean) {
        Logger.e("-----> onChanged " + gankDetailBean);
        GankAdapter mAdapter = new GankAdapter(GankDetailActivity.this);
        LinearLayoutManager linear = new LinearLayoutManager(GankDetailActivity.this);
        rvList.setLayoutManager(linear);
        rvList.setAdapter(mAdapter);
        rvList.setHasFixedSize(true);
        mAdapter.setData(gankDetailBean);
    }
}
