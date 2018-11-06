package com.xtp.library.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.xtp.library.XLibrary;

import java.lang.reflect.Method;

import static android.Manifest.permission.WRITE_SETTINGS;

public class ScreenUtil {

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) XLibrary.getApplication().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return XLibrary.getApplication().getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) XLibrary.getApplication().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return XLibrary.getApplication().getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * Return the density of screen.
     *
     * @return the density of screen
     */
    public static float getScreenDensity() {
        return XLibrary.getApplication().getResources().getDisplayMetrics().density;
    }

    /**
     * Return the screen density expressed as dots-per-inch.
     *
     * @return the screen density expressed as dots-per-inch
     */
    public static int getScreenDensityDpi() {
        return XLibrary.getApplication().getResources().getDisplayMetrics().densityDpi;
    }

    public static void showSystemUI(Activity activity) {
        View mDecorView = activity.getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    public static void hideSystemUI(Activity activity) {
        View mDecorView = activity.getWindow().getDecorView();
        hideSystemUI(mDecorView);
    }

    public static void hideSystemUI(View mDecorView) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public static boolean isStatusBarShown(Activity ac) {
        Rect frame = new Rect();
        ac.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int sbar = frame.top;
        return frame.top > 0;
    }

    /**
     * 获取导航栏的高度
     **/
    public static int getNavigationBarHeight() {
        Resources res = XLibrary.getApplication().getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getInternalDimensionSize(res, "navigation_bar_height");
        }
        return 0;
    }

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    public static int getNavigationBarHeightHori() {
        Resources res = XLibrary.getApplication().getResources();
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getInternalDimensionSize(res, "navigation_bar_width");
        }
        return result;
    }

    //通过此方法获取资源对应的像素值
    public static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Set full screen.
     *
     * @param activity The mActivity.
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Set non full screen.
     *
     * @param activity The mActivity.
     */
    public static void setNonFullScreen(@NonNull final Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Toggle full screen.
     *
     * @param activity The mActivity.
     */
    public static void toggleFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & fullScreenFlag) == fullScreenFlag) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * Return whether screen is full.
     *
     * @param activity The mActivity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    /**
     * Set the screen to landscape.
     *
     * @param activity The mActivity.
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * Set the screen to portrait.
     *
     * @param activity The mActivity.
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * Return whether screen is landscape.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLandscape() {
        return XLibrary.getApplication().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Return whether screen is portrait.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait() {
        return XLibrary.getApplication().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Return the rotation of screen.
     *
     * @param activity The mActivity.
     * @return the rotation of screen
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity The mActivity.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * Return the bitmap of screen.
     *
     * @param activity          The mActivity.
     * @param isDeleteStatusBar True to delete status bar, false otherwise.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.setWillNotCacheDrawing(false);
        Bitmap bmp = decorView.getDrawingCache();
        if (bmp == null) return null;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * Return whether screen is locked.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isScreenLock() {
        KeyguardManager km =
                (KeyguardManager) XLibrary.getApplication().getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * Set the duration of sleep.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration The duration.
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                XLibrary.getApplication().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * Return the duration of sleep.
     *
     * @return the duration of sleep.
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    XLibrary.getApplication().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * Return whether device is tablet.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTablet() {
        return (XLibrary.getApplication().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Adapt the screen for vertical slide.
     *
     * @param activity        The mActivity.
     * @param designWidthInPx The size of design diagram's width, in pixel.
     */
    public static void adaptScreen4VerticalSlide(final Activity activity, final int designWidthInPx) {
        adaptScreen(activity, designWidthInPx, true);
    }

    /**
     * Adapt the screen for horizontal slide.
     *
     * @param activity         The mActivity.
     * @param designHeightInPx The size of design diagram's height, in pixel.
     */
    public static void adaptScreen4HorizontalSlide(final Activity activity, final int designHeightInPx) {
        adaptScreen(activity, designHeightInPx, false);
    }

    /**
     * Reference from: https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     */
    private static void adaptScreen(final Activity activity,
                                    final int sizeInPx,
                                    final boolean isVerticalSlide) {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = XLibrary.getApplication().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / (float) sizeInPx;
        } else {
            activityDm.density = activityDm.heightPixels / (float) sizeInPx;
        }
        activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density);
        activityDm.densityDpi = (int) (160 * activityDm.density);

        appDm.density = activityDm.density;
        appDm.scaledDensity = activityDm.scaledDensity;
        appDm.densityDpi = activityDm.densityDpi;
    }

    /**
     * Cancel adapt the screen.
     *
     * @param activity The mActivity.
     */
    public static void cancelAdaptScreen(final Activity activity) {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = XLibrary.getApplication().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = systemDm.density;
        activityDm.scaledDensity = systemDm.scaledDensity;
        activityDm.densityDpi = systemDm.densityDpi;

        appDm.density = systemDm.density;
        appDm.scaledDensity = systemDm.scaledDensity;
        appDm.densityDpi = systemDm.densityDpi;
    }

    /**
     * Return whether adapt screen.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAdaptScreen() {
        final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
        final DisplayMetrics appDm = XLibrary.getApplication().getResources().getDisplayMetrics();
        return systemDm.density != appDm.density;
    }

    /**
     * Reference from: https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     */
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void setCustomDensity(Activity activity, final int designSize) {
        DisplayMetrics displayMetrics = XLibrary.getApplication().getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = displayMetrics.density;
            sNoncompatScaledDensity = displayMetrics.scaledDensity;
            XLibrary.getApplication().registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = XLibrary.getApplication().getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        float targetDensity = displayMetrics.widthPixels * 1.0f / designSize; //设计图360dp
        float targetScaledDensity = targetDensity * 1.0f * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);
        displayMetrics.density = targetDensity;
        displayMetrics.scaledDensity = targetScaledDensity;
        displayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
