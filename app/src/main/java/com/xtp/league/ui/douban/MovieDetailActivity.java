package com.xtp.league.ui.douban;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.xtp.league.App;
import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.pojo.GankDetailBean;
import com.xtp.league.pojo.MovieDetailBean;
import com.xtp.league.util.GlideUtil;
import com.xtp.library.base.BaseActivity;
import com.xtp.library.util.GlideApp;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.xtp.library.util.GlideOptions.bitmapTransform;

public class MovieDetailActivity extends BaseActivity implements Observer<MovieDetailBean> {

    private Toolbar tToolbar;

    private ImageView ivBg;
    private ImageView ivPoster;

    private DoubanViewModel mVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.douban_movie_detail_activity);

        mVM = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getApplication()).create(DoubanViewModel.class);

        initView();
        initData();
    }

    private void initView() {
        tToolbar = findViewById(R.id.tToolbar);
        ivBg = findViewById(R.id.ivBg);
        ivPoster = findViewById(R.id.ivPoster);

        setSupportActionBar(tToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        mImmersionBar
                .titleBar(tToolbar)
                .statusBarDarkFont(false)
                .navigationBarEnable(false)
                .init();
    }

    public void initData() {
        GlideUtil.load(this, ivPoster, getIntent().getStringExtra(Constant.KEY_IMG));
        GlideApp.with(this)
                .asBitmap()
                .apply(bitmapTransform(new BlurTransformation(20, 7)))
                .load(getIntent().getStringExtra(Constant.KEY_IMG))
                .into(ivBg);
        mVM.getMovieDetail(this, getIntent().getStringExtra(Constant.KEY_ID)).observe(this, this);
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
    public void onChanged(MovieDetailBean bean) {
        Logger.e("-----> onChanged " + bean);

    }
}
