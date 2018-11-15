package com.xtp.league.ui.wan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.pojo.DoubanTopBean;
import com.xtp.league.pojo.WanArticleBean;
import com.xtp.league.ui.gank.detail.GankDetailActivity;
import com.xtp.league.util.GlideUtil;
import com.xtp.library.util.GlideApp;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.xtp.library.util.GlideOptions.bitmapTransform;

public class WanArticleAdapter extends RecyclerView.Adapter<WanArticleAdapter.ItemHolder> {

    private Context context;

    private List<WanArticleBean.DataBean.DatasBean> mData;

    public WanArticleAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setData(List<WanArticleBean.DataBean.DatasBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<WanArticleBean.DataBean.DatasBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WanArticleAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.wan_article_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final WanArticleAdapter.ItemHolder viewHolder, final int position) {
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private FrameLayout flRoot;
        private ImageView ivBg;
        private ImageView ivPoster;
        private TextView tvTitle;
        private TextView tvDirector;
        private TextView tvActors;
        private TextView tvDate;
        private TextView tvGenres;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            flRoot = itemView.findViewById(R.id.flRoot);
            ivBg = itemView.findViewById(R.id.ivBg);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDirector = itemView.findViewById(R.id.tvDirector);
            tvActors = itemView.findViewById(R.id.tvActors);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvGenres = itemView.findViewById(R.id.tvGenres);
        }
    }
}
