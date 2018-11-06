package com.xtp.library.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

/**
 * Created by tomato on 2018/7/11
 */
public class PermissionUtils {

    private PermissionUtils() {
    }

    public static boolean permissionChecksRequest(Activity activity, String[] permissions, int requestCode) {
        boolean isPermission = false;
        ArrayList<String> refusePermissionIndex = new ArrayList<>();
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else {
            String[] var5 = permissions;
            int var6 = permissions.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String permission = var5[var7];
                if (ActivityCompat.checkSelfPermission(activity, permission) == -1) {
                    refusePermissionIndex.add(permission);
                }
            }

            if (refusePermissionIndex.size() <= 0) {
                isPermission = true;
            } else {
                ActivityCompat.requestPermissions(activity, (String[]) refusePermissionIndex.toArray(new String[refusePermissionIndex.size()]), requestCode);
                isPermission = false;
            }

            return isPermission;
        }
    }

    public static boolean permissionChecks(Context context, String permissions) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else {
            return ActivityCompat.checkSelfPermission(context, permissions) != -1;
        }
    }
}
