package com.xtp.library.util;

import android.graphics.drawable.Drawable;

import com.xtp.library.XLibrary;


public class ResourceUtil {

    public static int getLayoutId(String paramString) {
        return XLibrary.getApplication().getResources().getIdentifier(paramString, "layout", XLibrary.getApplication().getPackageName());
    }

    public static int getStringId(String paramString) {
        return XLibrary.getApplication().getResources().getIdentifier(paramString, "string", XLibrary.getApplication().getPackageName());
    }

    public static String getString(String paramString) {
        return XLibrary.getApplication().getResources().getString(getStringId(paramString));
    }

    public static int getDrawableId(String paramString) {
        return XLibrary.getApplication().getResources().getIdentifier(paramString, "drawable", XLibrary.getApplication().getPackageName());
    }

    public static Drawable getDrawable(String paramString) {
        return XLibrary.getApplication().getResources().getDrawable(getDrawableId(paramString));
    }

    public static Drawable getDrawable(int id) {
        return XLibrary.getApplication().getResources().getDrawable(id);
    }

    public static int getStyleId(String paramString) {
        return XLibrary.getApplication().getResources().getIdentifier(paramString, "style", XLibrary.getApplication().getPackageName());
    }

    public static int getId(String paramString) {
        return XLibrary.getApplication().getResources().getIdentifier(paramString, "id", XLibrary.getApplication().getPackageName());
    }

    public static int getColorId(String paramString) {
        return XLibrary.getApplication().getResources().getIdentifier(paramString, "color", XLibrary.getApplication().getPackageName());
    }

    public static int getArrayId(String paramString) {
        return XLibrary.getApplication().getResources().getIdentifier(paramString, "array", XLibrary.getApplication().getPackageName());
    }

    public static String getString(int resId) {
        return XLibrary.getApplication().getResources().getString(resId);
    }

    public static String getString(int resId, Object... formatStrs) {
        return XLibrary.getApplication().getResources().getString(resId, formatStrs);
    }

    public static int getColor(int resId) {
        return XLibrary.getApplication().getResources().getColor(resId);

    }
}