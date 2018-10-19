package com.xtp.league.ui.gank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtp.league.R;
import com.xtp.league.http.GankDetailBean;

public class GankAdapter extends RecyclerView.Adapter<GankAdapter.ItemHolder> {

    private Context context;

    private GankDetailBean mDetailBean;

    public GankAdapter(Context context) {
        this.context = context;
    }

    public void setData(GankDetailBean bean) {
        mDetailBean = bean;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GankAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.gank_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GankAdapter.ItemHolder viewHolder, final int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
