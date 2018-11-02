package com.xtp.league.ui.test.aac;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.util.GlideUtil;
import com.xtp.library.base.mvvm.VMBaseActivity;

/**
 * LiveData ViewModel
 */
public class LiveDataActivity extends VMBaseActivity {
    private Toolbar tToolbar;
    private ImageView ivPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gank_detail_activity);

        initView();
    }

    private void initView() {
        tToolbar = findViewById(R.id.tToolbar);
        setSupportActionBar(tToolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("LiveData & ViewModel");
        tToolbar.setTitleTextColor(0xFFFFFFFF);

        mImmersionBar
                .titleBar(tToolbar)
                .statusBarDarkFont(false)
                .navigationBarEnable(false)
                .init();

        ivPicture = findViewById(R.id.ivPicture);

        GlideUtil.load(this, ivPicture, getIntent().getStringExtra(Constant.KEY_IMG));
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
