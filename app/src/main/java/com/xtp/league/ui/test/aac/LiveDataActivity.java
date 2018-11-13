package com.xtp.league.ui.test.aac;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.util.GlideUtil;
import com.xtp.library.base.BaseActivity;

/**
 * LiveData ViewModel
 */
public class LiveDataActivity extends BaseActivity {
    private Toolbar tToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_data_activity);

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
