package com.xtp.league.ui.wan;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xtp.league.R;
import com.xtp.league.global.Constant;
import com.xtp.league.pojo.WanArticleBean;
import com.xtp.league.widget.RoundBackgroundColorSpan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        String str1 = mData.get(position).getSuperChapterName();
        String str2 = mData.get(position).getTitle();
        String str = str1 + str2;

        SpannableString spannableString = new SpannableString(str);
        int bgColor = Color.parseColor("#22ad79");
        int textColor = Color.parseColor("#22ad79");
        RoundBackgroundColorSpan span = new RoundBackgroundColorSpan(bgColor, textColor, 5);
        spannableString.setSpan(span, 0, str1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.tvTitle.setText(spannableString);
        viewHolder.tvTitle.setMovementMethod(LinkMovementMethod.getInstance());

        viewHolder.tvCategory.setText(mData.get(position).getSuperChapterName() + " / " + mData.get(position).getChapterName());
        viewHolder.tvAuthor.setText(mData.get(position).getAuthor());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date date = new Date();
        date.setTime(mData.get(position).getPublishTime());
        viewHolder.tvDate.setText(sdf.format(date));
        viewHolder.flRoot.setOnClickListener(v -> {
            ARouter.getInstance().build(Constant.AR_WEB_ACTIVITY)
                    .withString(Constant.KEY_URL, mData.get(position).getLink())
                    .navigation();
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout flRoot;
        private TextView tvTitle;
        private TextView tvCategory;
        private TextView tvAuthor;
        private TextView tvDate;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            flRoot = itemView.findViewById(R.id.flRoot);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDate = itemView.findViewById(R.id.tvDate);

        }
    }
}
