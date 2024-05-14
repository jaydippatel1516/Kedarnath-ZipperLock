package com.kedarnath.zipperlockscreen.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import com.kedarnath.zipperlockscreen.Activity.KZL_MainActivity;

public class OverlayPermissionIntentService extends IntentService {
    public static boolean isPermissionEnabled = false;

    public OverlayPermissionIntentService() {
        super("OverlayPermissionIntentService");
    }


    public void onHandleIntent(Intent intent) {
        getPackageName();
        if (Build.VERSION.SDK_INT >= 23) {
            while (!isPermissionEnabled) {
                if (Settings.canDrawOverlays(this)) {
                    isPermissionEnabled = true;
                }
            }
            KZL_MainActivity.RESUME_FROM_SERVICE = true;
            Intent intent2 = new Intent(this, KZL_MainActivity.class);
            intent2.addFlags(335544320);
            startActivity(intent2);
        }
    }
}
