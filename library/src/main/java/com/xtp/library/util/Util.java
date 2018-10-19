package com.xtp.library.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.xtp.library.XLibrary;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Util {

    public static int getVersionCode(Context con) {
        int verCode = -1;
        try {
            String packageName = con.getPackageName();
            Logger.i("packageName =" + packageName);
            verCode = con.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verCode;
    }

    public static String getVersionName(Context con) {
        try {
            PackageManager manager = con.getPackageManager();
            PackageInfo info = manager.getPackageInfo(con.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int dip2px(float dipValue) {
        if (XLibrary.getApplication() == null || XLibrary.getApplication().getResources() == null) {
            return (int) dipValue;
        }
        final float scale = XLibrary.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        if (XLibrary.getApplication() == null || XLibrary.getApplication().getResources() == null) {
            return (int) pxValue;
        }
        final float scale = XLibrary.getApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        if (context == null || context.getResources() == null) {
            return (int) dipValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        if (context == null || context.getResources() == null) {
            return (int) pxValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean isNetworkConnected() {
        if (XLibrary.getApplication() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) XLibrary.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null && mConnectivityManager.getActiveNetworkInfo() != null) {
                return mConnectivityManager.getActiveNetworkInfo().isAvailable();
            }
        }
        return false;
    }

    public static final int NET_TYPE_NONE = 0;

    public static final int NET_TYPE_WIFI = 1;

    public static final int NET_TYPE_MOBILE = 2;

    /**
     * 检查网络状况
     *
     * @return state:0-无网，1-wifi,2-mobile.
     */
    @SuppressLint("MissingPermission")
    public static int getNetWorkState(Context context) {
        if (context == null) {
            return NET_TYPE_NONE;
        }
        int state = NET_TYPE_NONE;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifiNetInfo != null) {
                if (wifiNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                    state = NET_TYPE_WIFI;
                } else {
                    state = NET_TYPE_NONE;
                }
            }
            if (mobNetInfo != null && state == 0) {
                if (mobNetInfo.getState() == NetworkInfo.State.CONNECTED) {
                    state = NET_TYPE_MOBILE;
                } else {
                    state = NET_TYPE_NONE;
                }
            }
        }
        return state;
    }

    /**
     * 复制到剪贴板
     *
     * @param str
     */
    public static void copy(Context context, String str) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(str);
    }

    public static void assertBackgroundThread() {
        if (isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on a background thread");
        }
    }

    public static void assertUiThread() {
        if (!isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on a UI thread");
        }
    }

    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean closeSoftkeyBoard(Context con, EditText et) {
        if (con == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && et != null) {
            return imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    public static boolean hideSoftkeyBoard(Context con, EditText et) {
        if (con == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && et != null) {
            return imm.hideSoftInputFromWindow(et.getWindowToken(), 0); //强制隐藏键盘
        }
        return false;
    }

    public static void showSoftkeyBoardDelay(final Context con, long delay) {
        Timer mTimer;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            public void run() {
                if (con == null) {
                    return;
                }

                ((InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, delay);
    }

    /**
     * 判断SD卡是否正常
     *
     * @return true-正常的
     */
    public static boolean isSdCardenable() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean saveBitmaptoFile(Bitmap bitmap, String filePath, Bitmap.CompressFormat format, int quality) {

        boolean bRet = true;
        if (null == bitmap || bitmap.isRecycled() || null == filePath) {
            return false;
        }

        File f = new File(filePath);
        f.getParentFile().mkdirs();

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            bRet = false;
            e.printStackTrace();
            return false;
        }

        bitmap.compress(format, quality, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            bRet = false;
            e.printStackTrace();
        }

        try {
            fOut.close();
        } catch (IOException e) {
            bRet = false;
            e.printStackTrace();
        }
        return bRet;
    }

    public static void showSoftkeyBoard(final Context con) {
        Timer mTimer;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            public void run() {
                if (con == null) {
                    return;
                }
                ((InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 500);
    }

    /**
     * 如果有>6个数字就返回true
     */
    public static boolean hasTooMuchNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                count = count + 1;
            }
        }
        return count > 6;
    }

    public static boolean isUiThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void openSoftKeyBoard(Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void openSoftKeyBoard(Context context, View view) {
        if (context == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static String getDeviceId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidId == null || "9774d56d682e549c".equals(androidId))
            androidId = UUID.randomUUID().toString();
        return signDeviceUID(androidId);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    // 下面这段可以对deviceUID做自校验，原来n位UID变为18位
    private static String signDeviceUID(String deviceUID) {
        if (deviceUID == null || deviceUID.length() <= 0)
            return null;
        byte[] SDMAP = "01239a48bcd567ef".getBytes();
        int len = deviceUID.length();
        int sum = 0;
        for (int i = 0; i < len; i += 2) {
            char c = deviceUID.charAt(i);
            sum += c;
        }
        deviceUID += (char) SDMAP[sum % 16];
        sum += deviceUID.charAt(0);
        sum += deviceUID.charAt(len > 8 ? 8 : len - 1);
        deviceUID += (char) SDMAP[sum % 16];
        return deviceUID;
    }

    /**
     * 获取栈顶activity
     */
    public static String getTopActvityName(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getClassName();
    }

    /**
     * 获取栈底activity
     */
    public static String getBaseActvityName(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).baseActivity;
        return cn.getShortClassName();
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        if (context == null || context.getResources() == null) {
            return (int) pxValue;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        if (context == null || context.getResources() == null) {
            return (int) spValue;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static Drawable getDrawable(String urlpath) {
        Drawable d = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            d = Drawable.createFromStream(in, "background.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static int getStatebarHeight(Activity ac) {
        int x = 0, sbar = 0;
        Rect frame = new Rect();
        ac.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        sbar = frame.top;
        if (sbar == 0) {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = ac.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return sbar;
    }


    public static boolean unpackZip(String zipPath) {
        Logger.i("unpackZip:" + zipPath);
        long start = System.currentTimeMillis();
        ZipInputStream zis;
        String dir = new File(zipPath).getParent();
        try {
            String filename;
            zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipPath)));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;
            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();
                if (ze.isDirectory()) {
                    File fmd = new File(dir + "/" + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(dir + "/" + filename);

                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }
                fout.close();
                zis.closeEntry();
            }
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Logger.i("unzip " + " need " + (System.currentTimeMillis() - start));
        return true;
    }

    public static void unZip(Context context, String assetName, String outputDirectory, boolean isReWrite) throws IOException {
        // 创建解压目标目录
        File file = new File(outputDirectory);

        if (file.exists()) {
            deleteDir(outputDirectory);
            file.mkdirs();
        } else {
            file.mkdirs();
        }

        // 打开压缩文件
        InputStream inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        // 使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        // 解压时字节计数
        int count = 0;
        // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            // 如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者是文件不存在
                if (isReWrite || !file.exists()) {
                    file.mkdir();
                }
            } else {
                // 如果是文件
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                Logger.e("---> unZip " + file.getAbsolutePath());
                // 文件需要覆盖或者文件不存在，则解压文件
                if (isReWrite || !file.exists()) {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            // 定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    /**
     * 对文件或文件目录进行压缩
     *
     * @param srcPath     要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath     压缩文件保存的路径。注意：zipPath不能是srcPath路径下的子文件夹
     * @param zipFileName 压缩文件名
     * @throws Exception
     */
    public static void zip(String srcPath, String zipPath, String zipFileName)
            throws Exception {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(zipPath) || TextUtils.isEmpty(zipFileName)) {
            throw new Exception("PARAMETER_IS_NULL");
        }
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            File srcFile = new File(srcPath);

            // 判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
            if (srcFile.isDirectory() && zipPath.indexOf(srcPath) != -1) {
                throw new Exception(
                        "zipPath must not be the child directory of srcPath.");
            }

            // 判断压缩文件保存的路径是否存在，如果不存在，则创建目录
            File zipDir = new File(zipPath);
            if (!zipDir.exists() || !zipDir.isDirectory()) {
                zipDir.mkdirs();
            }

            // 创建压缩文件保存的文件对象
            String zipFilePath = zipPath + zipFileName;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                // 检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
                SecurityManager securityManager = new SecurityManager();
                securityManager.checkDelete(zipFilePath);
                // 删除已存在的目标文件
                zipFile.delete();
            }

            cos = new CheckedOutputStream(new FileOutputStream(zipFile),
                    new CRC32());
            zos = new ZipOutputStream(cos);

            // 如果只是压缩一个文件，则需要截取该文件的父目录
            String srcRootDir = srcPath;
            if (srcFile.isFile()) {
                int index = srcPath.lastIndexOf(File.separator);
                if (index != -1) {
                    srcRootDir = srcPath.substring(0, index);
                }
            }
            // 调用递归压缩方法进行目录或文件压缩
            zip(srcRootDir, srcFile, zos);
            zos.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归压缩文件夹
     *
     * @param srcRootDir 压缩文件夹根目录的子路径
     * @param file       当前递归压缩的文件或目录对象
     * @param zos        压缩文件存储对象
     * @throws Exception
     */
    private static void zip(String srcRootDir, File file, ZipOutputStream zos)
            throws Exception {
        if (file == null) {
            return;
        }

        // 如果是文件，则直接压缩该文件
        if (file.isFile()) {
            int count, bufferLen = 1024;
            byte data[] = new byte[bufferLen];

            // 获取文件相对于压缩文件夹根目录的子路径
            String subPath = file.getAbsolutePath();
            int index = subPath.indexOf(srcRootDir);
            if (index != -1) {
                subPath = subPath.substring(srcRootDir.length() + File.separator.length());
            }
            ZipEntry entry = new ZipEntry(subPath);
            zos.putNextEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            while ((count = bis.read(data, 0, bufferLen)) != -1) {
                zos.write(data, 0, count);
            }
            bis.close();
            zos.closeEntry();
        }
        // 如果是目录，则压缩整个目录
        else {
            // 压缩目录中的文件或子目录
            File[] childFileList = file.listFiles();
            for (int n = 0; n < childFileList.length; n++) {
                childFileList[n].getAbsolutePath().indexOf(file.getAbsolutePath());
                zip(srcRootDir, childFileList[n], zos);
            }
        }
    }

    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWithFile(dir);
    }

    public static void deleteDirWithFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWithFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {

        }
        return bitmap;
    }

    public static Drawable getPropDrawable(Context context, int propId) {
        if (propId > 0 && propId < 20) {
            int resID = context.getResources().getIdentifier("icon_room_gift" + propId, "drawable", "com.melot.bang");
            Drawable image = context.getResources().getDrawable(resID);
            return image;
        }
        return null;
    }


    /**
     * 转成货币格式,eg:1,000,121
     */
    public static String getMonFormatStr(long money) {
        return NumberFormat.getNumberInstance().format(money);
    }

    /**
     * 新型号可用反射调用Build.hasSmartBar()来判断有无SmartBar
     *
     * @return
     */
    public static boolean hasSmartBar() {
        try {
            Method method = Class.forName("android.os.Build").getMethod("hasSmartBar");
            return ((Boolean) method.invoke(null)).booleanValue();
        } catch (Exception e) {
        }

        if (Build.DEVICE.equals("mx2")) {
            return true;
        } else if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9")) {
            return false;
        }
        return false;
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) XLibrary.getApplication().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) XLibrary.getApplication().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


    public static String getPortrait64(String portrait) {
        return getImageUrl(portrait, 64);
    }

    public static String getPortrait90(String portrait) {
        return getImageUrl(portrait, 90);
    }

    public static String getPortrait128(String portrait) {
        return getImageUrl(portrait, 128);
    }

    public static String getImageUrl(String img, int dp) {
        if (img != null && !img.isEmpty()) {
            if (!img.contains("?x-oss-process=image/resize")) {
                return img + "?x-oss-process=image/resize,w_" + dip2px(dp);
            }
        }
        return img;
    }


    /**
     * @param r 模糊半径 [1,50] r 越大图片越模糊。
     * @param s 正态分布的标准差	[1,50] s 越大图片越模糊。
     */
    public static String getPortraitWithBlur(String portrait, int size, int r, int s) {
        if (portrait != null && !portrait.isEmpty()) {
            if (!portrait.contains("?x-oss-process=image/resize")) {
                return portrait + "?x-oss-process=image/resize,w_" + size + "/blur,r_" + r + ",s_" + s;
            }
        }
        return portrait;
    }
}
