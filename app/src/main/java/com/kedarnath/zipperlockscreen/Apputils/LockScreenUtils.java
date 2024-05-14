package com.kedarnath.zipperlockscreen.Apputils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.provider.Telephony;
import android.service.notification.StatusBarNotification;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kedarnath.zipperlockscreen.R;
import com.kedarnath.zipperlockscreen.Receiver.LockScreenReceiver;
import com.kedarnath.zipperlockscreen.Service.NLService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LockScreenUtils implements View.OnClickListener, View.OnTouchListener, NLService.NLServiceListener {
    public static String PIN_FOR_UNLOCK;
    static final IntentFilter sIntentFilter = new IntentFilter();
    String LOCK_COMBINATION;
    boolean batteryEnabled;
    private boolean batteryGone = false;
    ImageView btn0;
    ImageView btn1;
    ImageView btn2;
    ImageView btn3;
    ImageView btn4;
    ImageView btn5;
    ImageView btn6;
    ImageView btn7;
    ImageView btn8;
    ImageView btn9;
    ImageView btnBackSpace;
    ImageView btnCancel;
    boolean callHas;
    Context context;
    boolean dateEnabled;
    String dateFormat;
    private boolean dateGone = false;
    TextView enterPasswordText;
    final Handler handler = new Handler();

    public int height;
    ImageView imgMissedCall = null;
    ImageView imgMissedSms = null;
    AppCompatActivity masterActivity;
    boolean missedCallEnabled;
    MyImageViewHorizontal myImageViewHorizontal = null;
    MyImageViewVertical myImageViewVertical = null;
    ArrayList<String> packageDials;
    String packageSms;
    ImageView[] pinImages;
    RelativeLayout pinLockHolder;
    public View pinView;
    LinearLayout pinsHolder;
    SharedPreferences prefs;
    int selectedBgNumber;
    boolean shouldUnlockScreen = true;
    boolean smsEnabled;
    boolean smsHas;
    public RelativeLayout tdbHolder;
    private final BroadcastReceiver timeChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            try {
                LockScreenUtils.this.updateTimeTextView();
                LockScreenUtils.this.updateDateTextView();
                LockScreenUtils.this.refreshBatteryPercentage();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    };
    boolean timeEnabled;
    int timeFormat;
    TextView txtBattery;
    TextView txtDate;
    TextView txtTime;
    Typeface typeface;
    UnlockListener unlockListener;

    public int width;
    Animation wiggleAnim;

    public interface UnlockListener {
        void onUnlockCommand();
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    static {
        sIntentFilter.addAction("android.intent.action.TIME_TICK");
        sIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        sIntentFilter.addAction("android.intent.action.TIME_SET");
        sIntentFilter.addAction("android.intent.action.DATE_CHANGED");
        sIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    }

    public AppCompatActivity getMasterActivity() {
        return this.masterActivity;
    }

    public LockScreenUtils(Context context2) {
        this.masterActivity = (AppCompatActivity) context2;
        this.context = context2;
        NLService.nlServiceListener = this;
        this.prefs = context2.getSharedPreferences(String.valueOf(context2.getPackageName()), 0);
    }

    private void notificationOn(String str) {
        int i = 0;
        while (i < this.packageDials.size()) {
            try {
                if (str.equalsIgnoreCase(this.packageDials.get(i)) && this.imgMissedCall != null && this.missedCallEnabled) {
                    this.imgMissedCall.setVisibility(VISIBLE);
                    this.callHas = true;
                }
                i++;
            } catch (Exception unused) {
                return;
            }
        }
        if (this.packageSms.equalsIgnoreCase(str) && this.imgMissedSms != null && this.smsEnabled) {
            this.imgMissedSms.setVisibility(VISIBLE);
            this.smsHas = true;
        }
    }

    private ArrayList<String> getPackagesOfDialerApps(Context context2) {
        ArrayList<String> arrayList = new ArrayList<>();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        for (ResolveInfo resolveInfo : context2.getPackageManager().queryIntentActivities(intent, 0)) {
            arrayList.add(resolveInfo.activityInfo.applicationInfo.packageName);
        }
        return arrayList;
    }

    private String getPackageOfSms(Context context2) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Telephony.Sms.getDefaultSmsPackage(context2);
        }
        String string = Settings.Secure.getString(context2.getContentResolver(), "sms_default_application");
        PackageManager packageManager = context2.getPackageManager();
        return packageManager.resolveActivity(packageManager.getLaunchIntentForPackage(string), 0).activityInfo.packageName;
    }


    public void getSavedPin() {
        PIN_FOR_UNLOCK = this.prefs.getString(this.context.getString(R.string.PIN_PREF_KEY), "0000");
        Log.v("LOCKSCREEN_UTILS_TEST", "password: " + PIN_FOR_UNLOCK);
    }


    public void getVisibilityUtils() {
        this.timeEnabled = this.prefs.getBoolean(this.context.getString(R.string.TIME_ON_PREF_KEY), true);
        this.dateEnabled = this.prefs.getBoolean(this.context.getString(R.string.DATE_ON_PREF_KEY), true);
        this.batteryEnabled = this.prefs.getBoolean(this.context.getString(R.string.BATTERY_ON_PREF_KEY), true);
        this.missedCallEnabled = this.prefs.getBoolean(this.context.getString(R.string.MISSED_CALL_ON_PREF_KEY), false);
        this.smsEnabled = this.prefs.getBoolean(this.context.getString(R.string.SMS_ON_PREF_KEY), false);
        this.timeFormat = this.prefs.getInt(this.context.getString(R.string.TIME_FORMAT_PREF_KEY), 1);
        this.dateFormat = this.context.getResources().getStringArray(R.array.date_formats)[this.prefs.getInt(this.context.getString(R.string.DATE_FORMAT_PREF_KEY), 0)];
    }


    public void checkCombination(int i) {
        try {
            this.LOCK_COMBINATION += String.valueOf(i);
            this.pinImages[this.LOCK_COMBINATION.length() - 1].setImageResource(R.drawable.pin_full);
            this.btnBackSpace.setVisibility(VISIBLE);
            this.btnCancel.setVisibility(GONE);
            if (this.LOCK_COMBINATION.length() != 4) {
                return;
            }
            if (this.LOCK_COMBINATION.equalsIgnoreCase(PIN_FOR_UNLOCK)) {
                unlockScreen();
                return;
            }
            resetPins();
            this.pinsHolder.startAnimation(this.wiggleAnim);
        } catch (Throwable unused) {
            resetPins();
            this.pinsHolder.startAnimation(this.wiggleAnim);
        }
    }


    public void deleteNumberFromCombination() {
        try {
            if (this.LOCK_COMBINATION.length() > 0) {
                this.pinImages[this.LOCK_COMBINATION.length() - 1].setImageResource(R.drawable.pin_empty);
                this.LOCK_COMBINATION = this.LOCK_COMBINATION.substring(0, this.LOCK_COMBINATION.length() - 1);
                Log.v("PIN_CHECK_TEST", this.LOCK_COMBINATION);
                if (this.LOCK_COMBINATION.length() == 0) {
                    this.btnBackSpace.setVisibility(GONE);
                    this.btnCancel.setVisibility(VISIBLE);
                    return;
                }
                return;
            }
            this.btnBackSpace.setVisibility(GONE);
            this.btnCancel.setVisibility(VISIBLE);
            Log.v("PIN_CHECK_TEST", "LOCK_COMBINATION is empty");
        } catch (Throwable unused) {
            resetPins();
            this.pinsHolder.startAnimation(this.wiggleAnim);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.pin_btn_0) {
            checkCombination(0);
        } else if (view.getId() == R.id.pin_btn_1) {
            checkCombination(1);
        } else if (view.getId() == R.id.pin_btn_2) {
            checkCombination(2);
        } else if (view.getId() == R.id.pin_btn_3) {
            checkCombination(3);
        } else if (view.getId() == R.id.pin_btn_4) {
            checkCombination(4);
        } else if (view.getId() == R.id.pin_btn_5) {
            checkCombination(5);
        } else if (view.getId() == R.id.pin_btn_6) {
            checkCombination(6);
        } else if (view.getId() == R.id.pin_btn_7) {
            checkCombination(7);
        } else if (view.getId() == R.id.pin_btn_8) {
            checkCombination(8);
        } else if (view.getId() == R.id.pin_btn_9) {
            checkCombination(9);
        } else if (view.getId() == R.id.pin_btn_backspace) {
            deleteNumberFromCombination();
        } else if (view.getId() == R.id.pin_btn_cancel) {
            resetPins();
            if (this.myImageViewVertical != null) {
                this.myImageViewVertical.ResetBitmaps();
                return;
            } else if (this.myImageViewHorizontal != null) {
                this.myImageViewHorizontal.ResetBitmaps();
                return;
            } else {
                return;
            }

        }

    }

    public void onNotification(int i, int i2) {
        if (i == 0 && i2 == 1) {
            if (this.imgMissedSms != null && this.smsEnabled) {
                this.imgMissedSms.setVisibility(VISIBLE);
            }
        } else if (i == 1 && i2 == 1) {
            if (this.imgMissedSms != null && this.smsEnabled) {
                this.imgMissedSms.setVisibility(View.INVISIBLE);
            }
        } else if (i == 0 && i2 == 0) {
            if (this.imgMissedCall != null && this.missedCallEnabled) {
                this.imgMissedCall.setVisibility(VISIBLE);
            }
        } else if (i == 1 && i2 == 0 && this.imgMissedCall != null && this.missedCallEnabled) {
            this.imgMissedCall.setVisibility(View.INVISIBLE);
        }
    }

    public void createLock() {
        LockScreenReceiver.shouldRestartLockScreen = true;
        this.selectedBgNumber = this.prefs.getInt(this.context.getString(R.string.BG_SELECTED_PREF_KEY), 0);
        getSavedPin();
        getVisibilityUtils();
        this.width = this.context.getSharedPreferences("MY_PREF", 0).getInt("SCREEN_WIDTH", 1);
        this.height = this.context.getSharedPreferences("MY_PREF", 0).getInt("SCREEN_HEIGHT", 1);
        this.typeface = Typeface.createFromAsset(this.context.getAssets(), this.context.getString(R.string.font_date));
        try {
            this.pinView = View.inflate(this.context, R.layout.pin_lock, (ViewGroup) null);
        } catch (InflateException unused) {
            unlockScreen();
        } catch (Exception unused2) {
            unlockScreen();
        }
        if (this.pinView != null) {
            this.txtTime = (TextView) this.pinView.findViewById(R.id.txtTime);
            this.txtTime.setTypeface(this.typeface);
            this.txtDate = (TextView) this.pinView.findViewById(R.id.txtDate);
            this.txtDate.setTypeface(this.typeface);
            if (!this.dateEnabled) {
                this.txtTime.setVisibility(View.INVISIBLE);
                this.txtDate.setVisibility(View.INVISIBLE);
                this.dateGone = true;
            }
            this.txtBattery = (TextView) this.pinView.findViewById(R.id.txtBattery);
            this.txtBattery.setTypeface(this.typeface);
            if (!this.batteryEnabled) {
                this.txtBattery.setVisibility(View.INVISIBLE);
                this.pinView.findViewById(R.id.imgBattery).setVisibility(View.INVISIBLE);
                this.batteryGone = true;
            }
            this.unlockListener = new UnlockListener() {
                public void onUnlockCommand() {
                    LockScreenUtils.this.shouldUnlockScreen = true;
                    LockScreenUtils.this.unlockScreen();
                }
            };
            this.imgMissedCall = this.pinView.findViewById(R.id.imgMissedCall);
            this.imgMissedSms = this.pinView.findViewById(R.id.imgMissedSms);
            NLService nLService = NLService.get();
            this.packageDials = getPackagesOfDialerApps(this.context);
            this.packageSms = getPackageOfSms(this.context);
            this.callHas = false;
            this.smsHas = false;
            try {
                for (StatusBarNotification packageName : nLService.getActiveNotifications()) {
                    notificationOn(packageName.getPackageName());
                }
            } catch (Exception unused3) {
            }
            if (!this.callHas && this.imgMissedCall != null && this.missedCallEnabled) {
                this.imgMissedCall.setVisibility(View.INVISIBLE);
            }
            if (!this.smsHas && this.imgMissedSms != null && this.smsEnabled) {
                this.imgMissedSms.setVisibility(View.INVISIBLE);
            }
            this.btn1 = this.pinView.findViewById(R.id.pin_btn_1);
            this.btn2 = this.pinView.findViewById(R.id.pin_btn_2);
            this.btn3 = this.pinView.findViewById(R.id.pin_btn_3);
            this.btn4 = this.pinView.findViewById(R.id.pin_btn_4);
            this.btn5 = this.pinView.findViewById(R.id.pin_btn_5);
            this.btn6 = this.pinView.findViewById(R.id.pin_btn_6);
            this.btn7 = this.pinView.findViewById(R.id.pin_btn_7);
            this.btn8 = this.pinView.findViewById(R.id.pin_btn_8);
            this.btn9 = this.pinView.findViewById(R.id.pin_btn_9);
            this.btn0 = this.pinView.findViewById(R.id.pin_btn_0);
            this.btnBackSpace = this.pinView.findViewById(R.id.pin_btn_backspace);
            this.btnCancel = this.pinView.findViewById(R.id.pin_btn_cancel);
            this.btn1.setOnClickListener(this);
            this.btn2.setOnClickListener(this);
            this.btn3.setOnClickListener(this);
            this.btn4.setOnClickListener(this);
            this.btn5.setOnClickListener(this);
            this.btn6.setOnClickListener(this);
            this.btn7.setOnClickListener(this);
            this.btn8.setOnClickListener(this);
            this.btn9.setOnClickListener(this);
            this.btn0.setOnClickListener(this);
            this.btnBackSpace.setOnClickListener(this);
            this.btnCancel.setOnClickListener(this);
            this.pinImages = new ImageView[4];
            this.pinImages[0] = this.pinView.findViewById(R.id.pin_1);
            this.pinImages[1] = this.pinView.findViewById(R.id.pin_2);
            this.pinImages[2] = this.pinView.findViewById(R.id.pin_3);
            this.pinImages[3] = this.pinView.findViewById(R.id.pin_4);
            this.enterPasswordText = (TextView) this.pinView.findViewById(R.id.text_view_enter_password);
            this.enterPasswordText.setTypeface(this.typeface);
            this.pinsHolder = (LinearLayout) this.pinView.findViewById(R.id.pins_holder);
            this.wiggleAnim = AnimationUtils.loadAnimation(this.context, R.anim.wiggle_anim);
            LockScreenReceiver.listener = this.unlockListener;
            resetPins();
            this.pinLockHolder = (RelativeLayout) this.pinView.findViewById(R.id.pinLockHolder);
            this.tdbHolder = (RelativeLayout) this.pinView.findViewById(R.id.relativeTDBHolder);
            if (!this.context.getSharedPreferences("MY_PREF", 0).getBoolean("pinLock", false)) {
                this.pinLockHolder.setVisibility(GONE);
            }
            final RelativeLayout relativeLayout = (RelativeLayout) this.pinView.findViewById(R.id.pinLockBG);
            relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (LockScreenUtils.this.width != relativeLayout.getMeasuredWidth() || LockScreenUtils.this.height != relativeLayout.getMeasuredHeight()) {
                        int unused = LockScreenUtils.this.width = relativeLayout.getMeasuredWidth();
                        int unused2 = LockScreenUtils.this.height = relativeLayout.getMeasuredHeight();
                        LockScreenUtils.this.context.getSharedPreferences("MY_PREF", 0).edit().putInt("SCREEN_HEIGHT", LockScreenUtils.this.height).apply();
                        LockScreenUtils.this.context.getSharedPreferences("MY_PREF", 0).edit().putInt("SCREEN_WIDTH", LockScreenUtils.this.width).apply();
                        try {
                            if (LockScreenUtils.this.myImageViewHorizontal != null) {
                                LockScreenUtils.this.myImageViewHorizontal.SetWidthAndHeight(LockScreenUtils.this.context, LockScreenUtils.this.width, LockScreenUtils.this.height);
                            } else if (LockScreenUtils.this.myImageViewVertical != null) {
                                LockScreenUtils.this.myImageViewVertical.SetWidthAndHeight(LockScreenUtils.this.context, LockScreenUtils.this.width, LockScreenUtils.this.height);
                            }
                        } catch (OutOfMemoryError unused3) {
                            LockScreenUtils.this.unlockScreen();
                        } catch (Exception unused4) {
                            LockScreenUtils.this.unlockScreen();
                        }
                    }
                }
            });
            try {
                relativeLayout.setBackgroundResource(this.context.getResources().getIdentifier("bg_pin", "drawable", this.context.getPackageName()));
            } catch (Throwable unused4) {
                unlockScreen();
            }
            try {
                switch (this.context.getSharedPreferences("MY_PREF", 0).getInt("zipperPos", Integer.parseInt(this.context.getString(R.string.zipper_position)))) {
                    case 1:
                        this.myImageViewVertical = (MyImageViewVertical) this.pinView.findViewById(R.id.imgZipperVertical);
                        this.myImageViewVertical.setVisibility(VISIBLE);
                        this.myImageViewVertical.SetWidthAndHeight(this.context, this.width, this.height);
                        this.myImageViewVertical.SetLockScreenUtils(this);
                        if (this.batteryGone) {
                            if (this.dateGone) {
                                this.tdbHolder.setVisibility(GONE);
                                break;
                            }
                        }
                        this.tdbHolder.setVisibility(VISIBLE);
                        double d = (double) this.height;
                        Double.isNaN(d);
                        int i = (int) (d * 0.244375d);
                        double d2 = (double) this.height;
                        Double.isNaN(d2);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i, (int) (d2 * 0.225d));
                        layoutParams.addRule(9, -1);
                        layoutParams.addRule(12, -1);
                        this.tdbHolder.setLayoutParams(layoutParams);
                        break;
                    case 2:
                        this.myImageViewHorizontal = (MyImageViewHorizontal) this.pinView.findViewById(R.id.imgZipperHorizontal);
                        this.myImageViewHorizontal.setVisibility(VISIBLE);
                        this.myImageViewHorizontal.SetWidthAndHeight(this.context, this.width, this.height);
                        this.myImageViewHorizontal.SetLockScreenUtils(this);
                        if (this.batteryGone) {
                            if (this.dateGone) {
                                this.tdbHolder.setVisibility(GONE);
                                break;
                            }
                        }
                        this.tdbHolder.setVisibility(VISIBLE);
                        double d3 = (double) this.height;
                        Double.isNaN(d3);
                        int i2 = (int) (d3 * 0.261d);
                        double d4 = (double) this.height;
                        Double.isNaN(d4);
                        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i2, (int) (d4 * 0.225d));
                        layoutParams2.addRule(14, -1);
                        layoutParams2.setMargins(0, this.width / 5, 0, 0);
                        this.tdbHolder.setLayoutParams(layoutParams2);
                        break;
                }
                this.masterActivity.registerReceiver(this.timeChangedReceiver, sIntentFilter);
            } catch (OutOfMemoryError unused5) {
                unlockScreen();
            } catch (Exception unused6) {
                unlockScreen();
            }
        } else {
            unlockScreen();
        }
    }

    private void resetPins() {
        this.LOCK_COMBINATION = "";
        for (ImageView imageResource : this.pinImages) {
            imageResource.setImageResource(R.drawable.pin_empty);
        }
        this.btnBackSpace.setVisibility(GONE);
        this.btnCancel.setVisibility(VISIBLE);
    }

    public void resumeLock() {
        try {
            refreshBatteryPercentage();
            updateTimeTextView();
            updateDateTextView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.shouldUnlockScreen = false;
        if (this.myImageViewHorizontal != null) {
            this.myImageViewHorizontal.ResetBitmaps();
        }
        if (this.myImageViewVertical != null) {
            this.myImageViewVertical.ResetBitmaps();
        }
        if (!this.batteryGone || !this.dateGone) {
            this.tdbHolder.setVisibility(VISIBLE);
        } else {
            this.tdbHolder.setVisibility(GONE);
        }
        resetPins();
        LockScreenReceiver.shouldRestartLockScreen = true;
    }

    public void refreshBatteryPercentage() {
        if (this.batteryEnabled) {
            Intent registerReceiver = this.context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            float intExtra = ((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1));
            TextView textView = this.txtBattery;
            textView.setText(((int) Math.floor((double) (intExtra * 100.0f))) + "%");
        }
    }

    public void updateTimeTextView() {
        if (this.dateEnabled) {
            StringBuilder sb = new StringBuilder(5);
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(System.currentTimeMillis());
            if (this.timeFormat == 1) {
                if (instance.get(11) < 10) {
                    sb.append("0");
                }
                sb.append(String.valueOf(instance.get(11)));
            } else if (instance.get(10) == 0) {
                sb.append("12");
            } else {
                if (instance.get(11) < 10) {
                    sb.append("0");
                }
                sb.append(String.valueOf(instance.get(10)));
            }
            sb.append(":");
            if (instance.get(12) < 10) {
                sb.append("0");
            }
            sb.append(String.valueOf(instance.get(12)));
            if (this.timeFormat == 0) {
                if (instance.get(9) == 0) {
                    sb.append(" am");
                } else {
                    sb.append(" pm");
                }
            }
            this.txtTime.setText(sb);
        }
    }

    public void updateDateTextView() {
        if (this.dateEnabled) {
            this.txtDate.setText(DateFormat.format(this.dateFormat, new Date(System.currentTimeMillis())));
        }
    }

    public void unlockScreen() {
        Log.v("LOCK_TEST", "unlockScreen()");
        Log.v("LOCK_TEST", "unlockScreen() " + this.masterActivity.getLocalClassName());
        if (this.pinView != null) {
            this.pinView.setVisibility(GONE);
        }
        this.masterActivity.finish();
    }

    public void destroyLock() {
        this.masterActivity.unregisterReceiver(this.timeChangedReceiver);
    }
}
