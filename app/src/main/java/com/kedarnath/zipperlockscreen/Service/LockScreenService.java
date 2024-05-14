package com.kedarnath.zipperlockscreen.Service;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.kedarnath.zipperlockscreen.R;
import com.kedarnath.zipperlockscreen.Receiver.LockScreenReceiver;


public class LockScreenService extends Service {
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    BroadcastReceiver mReceiver;
    private boolean started;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        ((KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock("IN").disableKeyguard();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        this.mReceiver = new LockScreenReceiver();
        registerReceiver(this.mReceiver, intentFilter);
        this.started = false;
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        String action;
        super.onStartCommand(intent, i, i2);
        if (!(intent == null || (action = intent.getAction()) == null)) {
            char c = 65535;
            int hashCode = action.hashCode();
            if (hashCode != -1964342113) {
                if (hashCode == 1969030125 && action.equals(ACTION_STOP_FOREGROUND_SERVICE)) {
                    c = 1;
                }
            } else if (action.equals(ACTION_START_FOREGROUND_SERVICE)) {
                c = 0;
            }
            switch (c) {
                case 0:
                    if (!this.started) {
                        this.started = true;
                        startForeGroundService();
                        break;
                    }
                    break;
                case 1:
                    this.started = false;
                    stopForegroundService();
                    break;
            }
        }
        return START_STICKY;
    }

    public void onDestroy() {
        if (this.started) {
            startForeGroundService();
        } else {
            unregisterReceiver(this.mReceiver);
        }
        super.onDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (this.started) {
            startForeGroundService();
        }
    }

    private void startForeGroundService() {
        String string = getString(R.string.app_name);
        String string2 = getString(R.string.lock_screen_sevice);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("some_channel_id", "Some Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
            Notification build = new NotificationCompat.Builder(this, "some_channel_id").setContentTitle(string).setContentText(string2).setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).build();
            notificationManager.notify(9874, build);
            startForeground(9874, build);
        }
    }

    private void stopForegroundService() {
        stopForeground(true);
        stopSelf();
    }
}
