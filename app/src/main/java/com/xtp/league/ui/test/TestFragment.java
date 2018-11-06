package com.xtp.league.ui.test;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xtp.league.R;
import com.xtp.league.pojo.TestBean;
import com.xtp.library.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends BaseFragment {

    private RecyclerView rvList;
    private TestAdapter mAdapter;
    private RefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);

        rvList = view.findViewById(R.id.rvList);

        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(linear);
        rvList.setHasFixedSize(true);

        List<TestBean> list = new ArrayList<>();
        list.add(new TestBean("LiveData & ViewModel", "https://upload-images.jianshu.io/upload_images/1018039-9fee758afafb394c.png", ""));
        list.add(new TestBean("Android Jetpack-LiveData", "https://upload-images.jianshu.io/upload_images/1018039-9fee758afafb394c.png", ""));
        mAdapter = new TestAdapter(getActivity(), list);
        rvList.setAdapter(mAdapter);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                rvList.setAdapter(mAdapter);
                refreshLayout.finishRefresh(2000);
            }
        });

        return view;
    }
}
