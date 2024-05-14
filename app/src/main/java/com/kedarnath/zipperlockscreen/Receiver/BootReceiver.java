package com.kedarnath.zipperlockscreen.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.kedarnath.zipperlockscreen.R;
import com.kedarnath.zipperlockscreen.Service.LockScreenService;

public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.e("BOOT_RECEIVER_LOCK", "BootReceiverOnReceive");
        if (context.getSharedPreferences(String.valueOf(context.getPackageName()), 0).getBoolean(context.getResources().getString(R.string.lock_on_key), false)) {
            Intent intent2 = new Intent(context, LockScreenService.class);
            intent2.setAction(LockScreenService.ACTION_START_FOREGROUND_SERVICE);
            try {
                if (Build.VERSION.SDK_INT >= 26) {
                    context.startForegroundService(intent2);
                } else {
                    context.startService(intent2);
                }
            } catch (Exception unused) {
            }
        }
    }
}
