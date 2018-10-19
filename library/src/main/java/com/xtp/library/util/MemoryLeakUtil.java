package com.xtp.library.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

import com.xtp.library.XLibrary;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 解决内存泄漏
 */
public class MemoryLeakUtil {
    private static Field field;
    private static boolean hasField = true;

    public static void HWLastSrvViewLeakFix() {
        if (!"HUAWEI".equals(Build.MANUFACTURER)) {
            return;
        }

        if (!hasField) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) XLibrary.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mLastSrvView"};
        for (String param : arr) {
            try {
                if (field == null) {
                    field = imm.getClass().getDeclaredField(param);
                }
                if (field == null) {
                    hasField = false;
                }
                if (field != null) {
                    field.setAccessible(true);
                    field.set(imm, null);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @SuppressLint("PrivateApi")
    public static void HwChangeButtonWindowCtrlFix(Activity activity) {
        try {
            if (!"HUAWEI".equals(Build.MANUFACTURER)) {
                return;
            }
            Class clazz = Class.forName("android.app.HwChangeButtonWindowCtrl");
            if (clazz == null) return;
            Field field = clazz.getDeclaredField("mInstanceMap");
            if (field == null) return;
            field.setAccessible(true);
            HashMap hashMap1 = (HashMap) field.get(field.getType().newInstance());
            HashMap h = (HashMap) field.getType().newInstance();
            for (Object key : hashMap1.keySet()) {
                Object o = hashMap1.get(key);
                Object value = ReflectUtil.getFieldValue(o, "mActivity");
                if (value != activity) {
                    h.put(key, hashMap1.get(key));
                }
            }
            field.set(hashMap1, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
