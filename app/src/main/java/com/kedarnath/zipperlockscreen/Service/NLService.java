package com.kedarnath.zipperlockscreen.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.provider.Telephony;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.ArrayList;

public class NLService extends NotificationListenerService {
    static NLService nlService;
    public static NLServiceListener nlServiceListener;
    public NLServiceReceiver nlServiceReceiver;
    ArrayList<String> packageDials;
    String packageName;
    String packageSms;

    public interface NLServiceListener {
        void onNotification(int i, int i2);
    }

    public void onCreate() {
        super.onCreate();
        this.nlServiceReceiver = new NLServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        this.packageName = getPackageName();
        intentFilter.addAction(this.packageName);
        registerReceiver(this.nlServiceReceiver, intentFilter);
        nlService = this;
        this.packageDials = new ArrayList<>();
        this.packageDials = getPackagesOfDialerApps(this);
        this.packageSms = getPackageOfSms(this);
    }

    public static NLService get() {
        return nlService;
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.nlServiceReceiver);
    }

    private ArrayList<String> getPackagesOfDialerApps(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 0)) {
            arrayList.add(resolveInfo.activityInfo.applicationInfo.packageName);
        }
        return arrayList;
    }

    private String getPackageOfSms(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Telephony.Sms.getDefaultSmsPackage(context);
        }
        String string = Settings.Secure.getString(getContentResolver(), "sms_default_application");
        PackageManager packageManager = getApplicationContext().getPackageManager();
        return packageManager.resolveActivity(packageManager.getLaunchIntentForPackage(string), 0).activityInfo.packageName;
    }

    private void notificationOn(String str) {
        int i = 0;
        while (i < this.packageDials.size()) {
            try {
                if (str.equalsIgnoreCase(this.packageDials.get(i))) {
                    Intent intent = new Intent(this.packageName);
                    intent.putExtra("notificationStatus", 0);
                    intent.putExtra("notificationType", 0);
                    sendBroadcast(intent);
                    return;
                }
                i++;
            } catch (Exception unused) {
                return;
            }
        }
        if (this.packageSms.equalsIgnoreCase(str)) {
            Intent intent2 = new Intent(this.packageName);
            intent2.putExtra("notificationStatus", 0);
            intent2.putExtra("notificationType", 1);
            sendBroadcast(intent2);
        }
    }

    private void notificationOff(String str) {
        int i = 0;
        while (i < this.packageDials.size()) {
            try {
                if (str.equalsIgnoreCase(this.packageDials.get(i))) {
                    Intent intent = new Intent(this.packageName);
                    intent.putExtra("notificationStatus", 1);
                    intent.putExtra("notificationType", 0);
                    sendBroadcast(intent);
                    return;
                }
                i++;
            } catch (Exception unused) {
                return;
            }
        }
        if (this.packageSms.equalsIgnoreCase(str)) {
            Intent intent2 = new Intent(this.packageName);
            intent2.putExtra("notificationStatus", 1);
            intent2.putExtra("notificationType", 1);
            sendBroadcast(intent2);
        }
    }

    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        try {
            notificationOn(statusBarNotification.getPackageName());
        } catch (Exception unused) {
        }
    }

    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        try {
            notificationOff(statusBarNotification.getPackageName());
        } catch (Exception unused) {
        }
    }

    class NLServiceReceiver extends BroadcastReceiver {
        SharedPreferences prefs;

        NLServiceReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            this.prefs = NLService.this.getSharedPreferences(String.valueOf(NLService.this.getPackageName()), 0);
            int intExtra = intent.getIntExtra("notificationType", 0);
            int intExtra2 = intent.getIntExtra("notificationStatus", 0);
            if (NLService.nlServiceListener != null) {
                NLService.nlServiceListener.onNotification(intExtra2, intExtra);
            }
        }
    }
}
