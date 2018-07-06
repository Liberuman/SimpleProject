/*
* Copyright 2015 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.sxu.baselibrary.commonutils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.sxu.baselibrary.R;

/*******************************************************************************
 * Description: 动态申请权限
 *
 * Author: Freeman
 *
 * Date: 2018/6/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PermissionUtil {

    private final static int PERMISSION_REQUEST_CODE = 1000;
    private static String permissionDesc;
    private static String permissionSettingDesc;
    private static OnPermissionRequestListener requestListener;

    public static boolean checkPermission(Activity context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[] {permission}, PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    public static boolean checkPermission(Activity context, String[] permission) {
        if (permission == null || permission.length == 0) {
            return true;
        }

        for (int i = 0; i < permission.length; i++) {
            if (!checkPermission(context, permission[i])) {
                return false;
            }
        }

        return true;
    }

    public static void requestCallback(final Activity context, int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestListener != null) {
                requestListener.onGranted();
                requestListener = null;
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    permissions != null && permissions.length > 0 ? permissions[0] : "")) {
                Toast.makeText(context, permissionDesc, Toast.LENGTH_LONG).show();
            } else {
                showSettingPermissionDialog(context);
            }
        }
    }

    private static void showSettingPermissionDialog(final Context context) {
        new AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog)
                .setMessage(permissionSettingDesc)
                .setNegativeButton("去设置",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                                if (intent.resolveActivity(context.getPackageManager()) != null) {
                                    context.startActivity(intent);
                                } else {
                                    Toast.makeText(context, "无法打开应用详情，请手动开启权限~", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .show();
    }

    public static void setPermissionRequestListener(String desc, String settingDesc, OnPermissionRequestListener listener) {
        permissionDesc = desc;
        permissionSettingDesc = settingDesc;
        requestListener = listener;
    }

    public interface OnPermissionRequestListener {
        void onGranted();
    }
}