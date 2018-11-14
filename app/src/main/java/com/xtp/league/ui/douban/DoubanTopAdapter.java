package com.xtp.league.ui.douban;

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

public class DoubanTopAdapter extends RecyclerView.Adapter<DoubanTopAdapter.ItemHolder> {

    private Context context;

    private List<DoubanTopBean.SubjectsBean> mData;

    public DoubanTopAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setData(List<DoubanTopBean.SubjectsBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<DoubanTopBean.SubjectsBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DoubanTopAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.douban_top_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DoubanTopAdapter.ItemHolder viewHolder, final int position) {
        GlideUtil.load(context, viewHolder.ivPoster, mData.get(position).getImages().getMedium());

        GlideApp.with(context)
                .asBitmap()
                .apply(bitmapTransform(new BlurTransformation(20, 7)))
                .load(mData.get(position).getImages().getSmall())
                .into(viewHolder.ivBg);
        viewHolder.tvTitle.setText(mData.get(position).getTitle() + " " + mData.get(position).getRating().getAverage() + "分");
        viewHolder.tvDirector.setText(getDirectorStr(mData.get(position)));
        viewHolder.tvActors.setText(getActorStr(mData.get(position)));
        viewHolder.tvGenres.setText(getGenresStr(mData.get(position)));
        viewHolder.tvDate.setText("上映日期：" + mData.get(position).getYear());

        viewHolder.flRoot.setOnClickListener(view -> {
            Intent intent = new Intent(context, GankDetailActivity.class);
            intent.putExtra(Constant.KEY_IMG, mData.get(position).getImages().getLarge());

            Pair image = new Pair(viewHolder.ivPoster, "image");
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, image);
            context.startActivity(intent, optionsCompat.toBundle());
        });
    }

    private String getDirectorStr(DoubanTopBean.SubjectsBean subjectsBean) {
        StringBuilder ret = new StringBuilder();
        for (DoubanTopBean.SubjectsBean.DirectorsBean directorsBean : subjectsBean.getDirectors()) {
            if (TextUtils.isEmpty(ret.toString())) {
                ret.append("导演：").append(directorsBean.getName());
            } else {
                ret.append(" / ").append(directorsBean.getName());
            }
        }
        return ret.toString();
    }


    private String getActorStr(DoubanTopBean.SubjectsBean subjectsBean) {
        StringBuilder ret = new StringBuilder();
        for (DoubanTopBean.SubjectsBean.CastsBean bean : subjectsBean.getCasts()) {
            if (TextUtils.isEmpty(ret.toString())) {
                ret.append("主演：").append(bean.getName());
            } else {
                ret.append(" / ").append(bean.getName());
            }
        }
        return ret.toString();
    }

    private String getGenresStr(DoubanTopBean.SubjectsBean subjectsBean) {
        StringBuilder ret = new StringBuilder();
        for (String bean : subjectsBean.getGenres()) {
            if (TextUtils.isEmpty(ret.toString())) {
                ret.append("类型：").append(bean);
            } else {
                ret.append(" / ").append(bean);
            }
        }
        return ret.toString();
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
