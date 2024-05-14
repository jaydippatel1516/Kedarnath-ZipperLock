package com.kedarnath.zipperlockscreen.Receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.kedarnath.zipperlockscreen.Activity.LockActivity;
import com.kedarnath.zipperlockscreen.Apputils.AppState;
import com.kedarnath.zipperlockscreen.Apputils.LockScreenUtils;
import com.kedarnath.zipperlockscreen.R;
import com.kedarnath.zipperlockscreen.Service.LockScreenService;

public class LockScreenReceiver extends BroadcastReceiver {
    public static final String ACTION_PREVIEW_LOCK_ACTIVITY_SUFFIX = ".LockScreenReceiver.PREVIEW_LOCKSCREEN";
    public static LockScreenUtils.UnlockListener listener = null;
    private static boolean sWasRinging = false;
    public static boolean shouldRestartLockScreen = false;
    SharedPreferences prefs;
    private boolean status_idle = true;
    private boolean telephony = false;

    public void onReceive(Context context, Intent intent) {
        this.prefs = context.getSharedPreferences(null, 0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getApplicationContext());
        stringBuilder.append(ACTION_PREVIEW_LOCK_ACTIVITY_SUFFIX);
        String stringBuilder2 = stringBuilder.toString();
        String action = intent.getAction();
        int hashCode = action.hashCode();
        int obj;
        if (hashCode != -2128145023) {
            if (hashCode != -1454123155) {
                if (hashCode != -1326089125) {
                    if (hashCode == 798292259) {
                        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                            obj = 2;
                            switch (obj) {
                                case 1:
                                    if (this.telephony) {
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append("IDLE VALUE: ");
                                        stringBuilder.append(String.valueOf(this.status_idle));
                                        Log.e("STATUS", stringBuilder.toString());
                                        if (this.status_idle) {
                                            startLockScreenActivity(context);
                                            this.telephony = false;
                                            return;
                                        }
                                        return;
                                    }
                                    Log.e("STATUS", "REGULAR");
                                    startLockScreenActivity(context);
                                    return;
                                case 2:
                                    refreshLockScreenWhenReboot(context);
                                    return;
                                case 3:
                                    handleLockScreenWhenIncomingPhoneCall(context, intent);
                                    return;
                                default:
                                    if (stringBuilder2.equalsIgnoreCase(intent.getAction())) {
                                        startLockScreenActivity(context);
                                        return;
                                    }
                                    return;
                            }
                        }
                    }
                } else if (action.equals("android.intent.action.PHONE_STATE")) {
                    obj = 3;
                    switch (obj) {
                        case 0:
                            return;
                        case 1:
                            if (this.telephony) {
                                Log.e("STATUS", "REGULAR");
                                startLockScreenActivity(context);
                                return;
                            }
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("IDLE VALUE: ");
                            stringBuilder.append(String.valueOf(this.status_idle));
                            Log.e("STATUS", stringBuilder.toString());
                            if (this.status_idle) {
                                startLockScreenActivity(context);
                                this.telephony = false;
                                return;
                            }
                            return;
                        case 2:
                            refreshLockScreenWhenReboot(context);
                            return;
                        case 3:
                            handleLockScreenWhenIncomingPhoneCall(context, intent);
                            return;
                        default:
                            if (stringBuilder2.equalsIgnoreCase(intent.getAction())) {
                                startLockScreenActivity(context);
                                return;
                            }
                            return;
                    }
                }
            } else if (action.equals("android.intent.action.SCREEN_ON")) {
                obj = 0;
                switch (obj) {
                    case 0:
                        return;
                    case 1:
                        if (this.telephony) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("IDLE VALUE: ");
                            stringBuilder.append(String.valueOf(this.status_idle));
                            Log.e("STATUS", stringBuilder.toString());
                            if (this.status_idle) {
                                startLockScreenActivity(context);
                                this.telephony = false;
                                return;
                            }
                            return;
                        }
                        Log.e("STATUS", "REGULAR");
                        startLockScreenActivity(context);
                        return;
                    case 2:
                        refreshLockScreenWhenReboot(context);
                        return;
                    case 3:
                        handleLockScreenWhenIncomingPhoneCall(context, intent);
                        return;
                    default:
                        if (stringBuilder2.equalsIgnoreCase(intent.getAction())) {
                            startLockScreenActivity(context);
                            return;
                        }
                        return;
                }
            }
        } else if (action.equals("android.intent.action.SCREEN_OFF")) {
            obj = 1;
            switch (obj) {
                case 0:
                    return;
                case 1:
                    if (this.telephony) {
                        Log.e("STATUS", "REGULAR");
                        startLockScreenActivity(context);
                        return;
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("IDLE VALUE: ");
                    stringBuilder.append(String.valueOf(this.status_idle));
                    Log.e("STATUS", stringBuilder.toString());
                    if (this.status_idle) {
                        startLockScreenActivity(context);
                        this.telephony = false;
                        return;
                    }
                    return;
                case 2:
                    refreshLockScreenWhenReboot(context);
                    return;
                case 3:
                    handleLockScreenWhenIncomingPhoneCall(context, intent);
                    return;
                default:
                    if (stringBuilder2.equalsIgnoreCase(intent.getAction())) {
                        startLockScreenActivity(context);
                        return;
                    }
                    return;
            }
        }
        obj = -1;
        switch (obj) {
            case 0:
                return;
            case 1:
                if (this.telephony) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("IDLE VALUE: ");
                    stringBuilder.append(String.valueOf(this.status_idle));
                    Log.e("STATUS", stringBuilder.toString());
                    if (this.status_idle) {
                        startLockScreenActivity(context);
                        this.telephony = false;
                        return;
                    }
                    return;
                }
                Log.e("STATUS", "REGULAR");
                startLockScreenActivity(context);
                return;
            case 2:
                refreshLockScreenWhenReboot(context);
                return;
            case 3:
                handleLockScreenWhenIncomingPhoneCall(context, intent);
                return;
            default:
                if (stringBuilder2.equalsIgnoreCase(intent.getAction())) {
                    startLockScreenActivity(context);
                    return;
                }
                return;
        }
    }

    private void startLockScreenActivity(Context context) {
        ComponentName componentName = new ComponentName(context.getApplicationContext(), LockActivity.class);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.addFlags(8388608);
        intent.addFlags(268435456);
        intent.addFlags(2010);
        if (!AppState.getInstance().GetVisible()) {
            intent.addFlags(67108864);
            intent.addFlags(32768);
        }
        try {
            context.startActivity(intent);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void refreshLockScreenWhenReboot(Context context) {
        if (this.prefs.getBoolean(context.getString(R.string.lock_on_key), true)) {
            foregroundStartService(context);
            startLockScreenActivity(context);
            return;
        }
        foregroundStopService(context);
    }

    private void foregroundStartService(Context context) {
        Intent intent = new Intent(context, LockScreenService.class);
        intent.setAction(LockScreenService.ACTION_START_FOREGROUND_SERVICE);
        context.startService(intent);
    }

    private void foregroundStopService(Context context) {
        Intent intent = new Intent(context, LockScreenService.class);
        if (Build.VERSION.SDK_INT >= 26) {
            intent.setAction(LockScreenService.ACTION_STOP_FOREGROUND_SERVICE);
            context.startService(intent);
            return;
        }
        context.stopService(intent);
    }

    private void handleLockScreenWhenIncomingPhoneCall(Context context, Intent intent) {
        if (this.prefs.getBoolean(context.getString(R.string.lock_on_key), true)) {
            String stringExtra = intent.getStringExtra("state");
            if (TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(stringExtra)) {
                sWasRinging = true;
                this.status_idle = false;
                this.telephony = true;
                Log.e("STATUS", "RINGING");
                Log.e("STATUS", "WAS RINGING RINGING " + String.valueOf(sWasRinging));
                if (listener != null) {
                    listener.onUnlockCommand();
                }
            } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equalsIgnoreCase(stringExtra)) {
                this.status_idle = false;
                this.telephony = true;
                Log.e("STATUS", "OFF HOOK");
            } else if (TelephonyManager.EXTRA_STATE_IDLE.equalsIgnoreCase(stringExtra)) {
                Log.e("STATUS", "IDLE");
                Log.e("STATUS", "WAS RINGING IDLE " + String.valueOf(sWasRinging));
                this.status_idle = true;
                if (sWasRinging) {
                    Log.e("STATUS", "WAS RINGING");
                    sWasRinging = false;
                    startLockScreenActivity(context);
                }
            }
        }
    }
}
