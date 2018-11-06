package com.xtp.library.util;

import android.annotation.SuppressLint;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xtp.library.R;
import com.xtp.library.XLibrary;

public class ToastUtil {

    private static Toast mToast = null;

    @SuppressLint("ShowToast")
    public static void showToast(@StringRes int strId) {
        View toastRoot = LayoutInflater.from(XLibrary.getApplication()).inflate(R.layout.common_toast_layout, null);
        TextView mTextView = toastRoot.findViewById(R.id.tvContent);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        mTextView.setText(strId);
        mTextView.setGravity(Gravity.CENTER);

        if (null != mToast) {
            mToast.setView(toastRoot);
        } else {
            mToast = new Toast(XLibrary.getApplication());
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setView(toastRoot);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToast(String str) {
        View toastRoot = LayoutInflater.from(XLibrary.getApplication()).inflate(R.layout.common_toast_layout, null);
        TextView mTextView = toastRoot.findViewById(R.id.tvContent);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        mTextView.setText(str);
        mTextView.setGravity(Gravity.CENTER);

        if (null != mToast) {
            mToast.setView(toastRoot);
        } else {
            mToast = new Toast(XLibrary.getApplication());
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setView(toastRoot);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToast(String str, int length) {
        View toastRoot = LayoutInflater.from(XLibrary.getApplication()).inflate(R.layout.common_toast_layout, null);
        TextView mTextView = toastRoot.findViewById(R.id.tvContent);
        mTextView.setText(str);
        mTextView.setGravity(Gravity.CENTER);

        if (null != mToast) {
            mToast.setView(toastRoot);
        } else {
            mToast = new Toast(XLibrary.getApplication());
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(length);
            mToast.setView(toastRoot);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToast(@StringRes int strId, int length) {
        View toastRoot = LayoutInflater.from(XLibrary.getApplication()).inflate(R.layout.common_toast_layout, null);
        TextView mTextView = toastRoot.findViewById(R.id.tvContent);
        mTextView.setText(strId);
        mTextView.setGravity(Gravity.CENTER);

        if (null != mToast) {
            mToast.setView(toastRoot);
        } else {
            mToast = new Toast(XLibrary.getApplication());
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(length);
            mToast.setView(toastRoot);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToast(@StringRes int strId, int length, @DrawableRes int draId) {
        View toastRoot = LayoutInflater.from(XLibrary.getApplication()).inflate(R.layout.common_toast_layout, null);
        TextView mTextView = toastRoot.findViewById(R.id.tvContent);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(draId, 0, 0, 0);
        mTextView.setCompoundDrawablePadding(Util.dip2px(5));
        mTextView.setText(strId);
        mTextView.setGravity(Gravity.CENTER);

        if (null != mToast) {
            mToast.setView(toastRoot);
        } else {
            mToast = new Toast(XLibrary.getApplication());
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(length);
            mToast.setView(toastRoot);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showToast(String str, int length, @DrawableRes int draId) {
        View toastRoot = LayoutInflater.from(XLibrary.getApplication()).inflate(R.layout.common_toast_layout, null);
        TextView mTextView = toastRoot.findViewById(R.id.tvContent);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(draId, 0, 0, 0);
        mTextView.setCompoundDrawablePadding(Util.dip2px(5));
        mTextView.setText(str);

        if (null != mToast) {
            mToast.setView(toastRoot);
        } else {
            mToast = new Toast(XLibrary.getApplication());
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(length);
            mToast.setView(toastRoot);
        }
        mToast.show();
    }
}
