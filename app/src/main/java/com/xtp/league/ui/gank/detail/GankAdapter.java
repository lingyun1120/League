package com.xtp.league.ui.gank.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.xtp.league.R;
import com.xtp.league.pojo.GankBean;
import com.xtp.league.pojo.GankDetailBean;
import com.xtp.library.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.ItemHolder> {

    private Context context;

    private List<GankBean> mDatas;

    public GankAdapter(Context context) {
        this.context = context;
        mDatas = new ArrayList<>();
    }

    public void setData(GankDetailBean bean) {
        if (bean == null || bean.getResults() == null) return;
        if (bean.getResults().getAndroid() != null) {
            mDatas.addAll(bean.getResults().getAndroid());
        }
        if (bean.getResults().getIOS() != null) {
            mDatas.addAll(bean.getResults().getIOS());
        }
        if (bean.getResults().getWeb() != null) {
            mDatas.addAll(bean.getResults().getWeb());
        }
        if (bean.getResults().getRecommend() != null) {
            mDatas.addAll(bean.getResults().getRecommend());
        }
        if (bean.getResults().getResource() != null) {
            mDatas.addAll(bean.getResults().getResource());
        }
        if (bean.getResults().getApp() != null) {
            mDatas.addAll(bean.getResults().getApp());
        }
        if (bean.getResults().getVideo() != null) {
            mDatas.addAll(bean.getResults().getVideo());
        }
        if (bean.getResults().getWelfare() != null) {
            mDatas.addAll(bean.getResults().getWelfare());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GankAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.gank_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GankAdapter.ItemHolder viewHolder, final int position) {
        GankBean bean = mDatas.get(position);

        viewHolder.tvTitle.setText(bean.getType());
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        GlideApp.with(context).load(R.drawable.rss).apply(requestOptions).into(viewHolder.ivType);
        if (bean.getType().equals("Android")) {
            GlideApp.with(context).load(R.drawable.android).apply(requestOptions).into(viewHolder.ivType);
        }
        if (bean.getType().equals("iOS")) {
            GlideApp.with(context).load(R.drawable.ios).apply(requestOptions).into(viewHolder.ivType);
        }
        if (bean.getType().equals("App")) {
            GlideApp.with(context).load(R.drawable.app).apply(requestOptions).into(viewHolder.ivType);
        }
        if (bean.getType().equals("前端")) {
            GlideApp.with(context).load(R.drawable.html).apply(requestOptions).into(viewHolder.ivType);
        }
        if (bean.getType().equals("休息视频")) {
            GlideApp.with(context).load(R.drawable.youtube).apply(requestOptions).into(viewHolder.ivType);
        }
        if (bean.getType().equals("福利")) {
            GlideApp.with(context).load(R.drawable.wiki).apply(requestOptions).into(viewHolder.ivType);
        }

        viewHolder.tvDate.setText(bean.getPublishedAt());
        viewHolder.tvDesc.setText(bean.getDesc());
        if (bean.getImages() != null && !bean.getImages().isEmpty()) {
            viewHolder.images.get(0).setImageResource(R.drawable.gank_item_image_bg);
            viewHolder.images.get(0).setVisibility(View.INVISIBLE);
            viewHolder.images.get(1).setImageResource(R.drawable.gank_item_image_bg);
            viewHolder.images.get(1).setVisibility(View.INVISIBLE);
            viewHolder.images.get(2).setImageResource(R.drawable.gank_item_image_bg);
            viewHolder.images.get(2).setVisibility(View.INVISIBLE);
            viewHolder.llImages.setVisibility(View.VISIBLE);
            int size = bean.getImages().size() > 3 ? 3 : bean.getImages().size();
            for (int index = 0; index < size; index++) {
                viewHolder.images.get(index).setVisibility(View.VISIBLE);
                GlideApp.with(context)
                        .load(bean.getImages().get(index))
                        .into(viewHolder.images.get(index));
            }
        } else {
            viewHolder.llImages.setVisibility(View.GONE);
        }

        viewHolder.llRoot.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(bean.getUrl());
            intent.setData(content_url);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout llRoot;
        private ImageView ivType;
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvDesc;
        private LinearLayout llImages;
        private List<ImageView> images = new ArrayList<>();

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            llRoot = itemView.findViewById(R.id.llRoot);
            ivType = itemView.findViewById(R.id.ivType);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            llImages = itemView.findViewById(R.id.llImages);
            images.add(itemView.findViewById(R.id.ivPicture1));
            images.add(itemView.findViewById(R.id.ivPicture2));
            images.add(itemView.findViewById(R.id.ivPicture3));
        }
    }
}
