package com.xtp.library.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.xtp.library.XLibrary;

import java.io.File;

import static android.content.Context.ACTIVITY_SERVICE;

public class PhoneUtil {
    public static String getPhoneInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("产品名称:").append(Build.PRODUCT);
        builder.append(", 硬件制造商:").append(Build.MANUFACTURER);
        builder.append(", 系统定制商:").append(Build.BRAND);
        builder.append(", 手机型号信息:").append(Build.MODEL);
        builder.append(", 硬件名称:").append(Build.HARDWARE);
        builder.append(", 系统版本号:").append(Build.VERSION.RELEASE);
        builder.append(", Android_SDK:").append(Build.VERSION.SDK_INT);
        builder.append(", 手机运行内存(RAM):").append(getFormatSize(getAvailableMemory())).append("/").append(getFormatSize(geTotalMemory()));
        builder.append(", 手机手机运行内存（ROM）:").append(getFormatSize(getAvailableROM())).append("/").append(getFormatSize(getTotalROM()));
        builder.append(", 当前使用网络:").append(getNetWorkState());
        return builder.toString();
    }

    private static String getNetWorkState() {
        if (Util.getNetWorkState(XLibrary.getApplication()) == Util.NET_TYPE_WIFI) {
            return "WIFI";
        } else if (Util.getNetWorkState(XLibrary.getApplication()) == Util.NET_TYPE_MOBILE) {
            return "手机网络";
        } else {
            return "无网络";
        }
    }

    /**
     * 获取可用空间
     *
     * @return
     */
    public static long getAvailableROM() {
        File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }


    /**
     * 获取全部空间
     *
     * @return
     */
    public static long getTotalROM() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getBlockCountLong();
        return availableBlocks * blockSize;
    }

    /**
     * 获取全部空间
     */
    public static String getFormatSize(long size) {
        double sizeKB = size / 1024.0;
        double sizeMB = size / 1024.0 / 1024;
        double sizeGB = size / 1024.0 / 1024 / 1024;

        if (sizeGB > 1) {
            return String.format("%.2f", sizeGB) + "GB";
        } else if (sizeMB > 1) {
            return String.format("%.2f", sizeMB) + "MB";
        } else if (sizeKB > 1) {
            return String.format("%.2f", sizeKB) + "KB";
        }
        return "1KB";
    }

    public static String getMyMemory() {
        ActivityManager activityManager = (ActivityManager) XLibrary.getApplication().getSystemService(ACTIVITY_SERVICE);
        //最大分配内存
        int memory = activityManager.getMemoryClass();
        System.out.println("memory: " + memory);
        //最大分配内存获取方法2
        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
        //当前分配的总内存
        float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
        //剩余内存
        float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));

        String MemoryStr = "maxMemory: " + maxMemory + " totalMemory:" + totalMemory + " freeMemory:" + freeMemory;

        return MemoryStr;
    }

    /**
     * 获取可用手机内存(RAM)
     */
    public static long getAvailableMemory() {
        ActivityManager am = (ActivityManager) XLibrary.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            return mi.availMem;
        } else {
            return 0;
        }
    }

    /**
     * 获取可用手机内存(RAM)
     */
    public static long geTotalMemory() {
        ActivityManager am = (ActivityManager) XLibrary.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            return mi.totalMem;
        } else {
            return 0;
        }
    }
}
