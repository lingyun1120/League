package com.xtp.league.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.xtp.library.util.GlideApp;

/**
 * 默认的策略是DiskCacheStrategy.AUTOMATIC
 * DiskCacheStrategy有五个常量：
 * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
 * DiskCacheStrategy.NONE 不使用磁盘缓存
 * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
 * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
 * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
 */

public class GlideUtil {

    public static void load(Context context, @NonNull ImageView view, String url) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .into(view);
    }

    public static void load(Context context, @NonNull ImageView view, String url, @DrawableRes int placeholder) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .placeholder(placeholder)
                .fallback(placeholder)
                .into(view);
    }

    public static void load(Context context, @NonNull ImageView view, String url, @DrawableRes int placeholder, @DrawableRes int error) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .fallback(placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

    public static void loadPortrait(Context context, @NonNull ImageView view, String url, @DrawableRes int placeholder) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .format(DecodeFormat.PREFER_RGB_565)
                .circleCrop()
                .timeout(10000)
                .placeholder(placeholder)
                .fallback(placeholder)
                .into(view);
    }

    public static void loadGif(Context context, @NonNull ImageView view, int drawable) {
        GlideApp.with(context)
                .asGif()
                .load(drawable)
                .into(view);
    }
}
