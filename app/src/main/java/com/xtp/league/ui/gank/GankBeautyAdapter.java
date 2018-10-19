package com.xtp.league.ui.gank;

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
import com.xtp.league.http.GankBeautyBean;
import com.xtp.league.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class GankBeautyAdapter extends RecyclerView.Adapter<GankBeautyAdapter.ItemHolder> {

    private Context context;

    private List<GankBeautyBean.ResultsBean> mData;

    public GankBeautyAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setData(List<GankBeautyBean.ResultsBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GankBeautyAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.gank_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GankBeautyAdapter.ItemHolder viewHolder, final int position) {
        GlideUtil.load(context, viewHolder.ivPicture, mData.get(position).getUrl());
        viewHolder.tvDetail.setText(mData.get(position).getPublishedAt().split("T")[0]);
        viewHolder.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GankDetailActivity.class);
                intent.putExtra(Constant.KEY_IMG, mData.get(position).getUrl());
                intent.putExtra(Constant.KEY_DATE, mData.get(position).getPublishedAt().split("T")[0]);

                Pair image = new Pair(viewHolder.ivPicture, "image");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, image);
                context.startActivity(intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
