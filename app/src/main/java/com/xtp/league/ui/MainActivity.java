package com.xtp.league.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.xtp.league.R;
import com.xtp.league.ui.gank.GankFragment;
import com.xtp.league.ui.test.TestFragment;
import com.xtp.library.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static final String FRAGMENT_GANK_TAG = "main_fragment_gank";
    public static final String FRAGMENT_TEST_TAG = "main_fragment_test";

    private ViewPager vpPages;
    private MainPagerAdapter mPagerAdapter;

    private GankFragment mGankFragment;
    private TestFragment mTestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setSupportActionBar((Toolbar) findViewById(R.id.tToolbar));

        mImmersionBar
                .titleBar(findViewById(R.id.tToolbar))
                .statusBarDarkFont(true, 0.2f)
                .navigationBarEnable(false)
                .init();

        initView();
        initFragments();
    }

    private void initView() {
        vpPages = findViewById(R.id.vpPages);
    }

    private void initFragments() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_GANK_TAG);
        if (fragment == null) {
            mGankFragment = new GankFragment();
        } else {
            mGankFragment = (GankFragment) fragment;
        }
        Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TEST_TAG);
        if (fragment1 == null) {
            mTestFragment = new TestFragment();
        } else {
            mTestFragment = (TestFragment) fragment1;
        }
        List<Fragment> list = new ArrayList<>();
        list.add(mGankFragment);
        list.add(mTestFragment);

        List<String> tags = new ArrayList<>();
        tags.add(FRAGMENT_GANK_TAG);
        tags.add(FRAGMENT_TEST_TAG);

        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), list, tags);
        vpPages.setAdapter(mPagerAdapter);
    }
}
