package com.xtp.league.ui.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.pojo.TestBean;
import com.xtp.league.ui.test.aac.LiveDataActivity;
import com.xtp.league.util.GlideUtil;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ItemHolder> {

    private Context context;

    private List<TestBean> mDatas;

    public TestAdapter(Context context, List<TestBean> data) {
        mDatas = data;
        this.context = context;
    }

    @NonNull
    @Override
    public TestAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.test_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TestAdapter.ItemHolder viewHolder, final int position) {
        GlideUtil.load(context, viewHolder.ivPicture, mDatas.get(position).img);
        viewHolder.tvDetail.setText( mDatas.get(position).title);
        viewHolder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LiveDataActivity.class);
                intent.putExtra(Constant.KEY_IMG, mDatas.get(position).img);
                Pair image = new Pair(viewHolder.ivPicture, "image");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, image);
                context.startActivity(intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private ImageView ivPicture;
        private TextView tvDetail;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvDetail = itemView.findViewById(R.id.tvDetail);
        }
    }
}
