package com.kedarnath.zipperlockscreen.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kedarnath.zipperlockscreen.Apputils.AppState;
import com.kedarnath.zipperlockscreen.R;

import java.util.Calendar;
import java.util.Date;

public class KZL_LockPreviewActivity extends BaseActivity implements View.OnClickListener {
    static final IntentFilter sIntentFilter = new IntentFilter();
    int TYPE;
    boolean batteryEnabled;
    Bitmap bmpPendant;
    Bitmap bmpRezFront;
    Bitmap bmpZipper;
    boolean dateEnabled;
    String dateFormat;
    ImageView imgBg;
    ImageView imgZipper;
    int maxHeight;
    int maxWidth;
    boolean missedCallEnabled;
    SharedPreferences prefs;
    int screenWidth;
    boolean smsEnabled;

    public final BroadcastReceiver timeChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            updateTimeTextView();
            updateDateTextView();
            refreshBatteryPercentage();
            unregisterReceiver(timeChangedReceiver);
        }
    };
    boolean timeEnabled;
    int timeFormat;
    TextView txtBattery;
    TextView txtDate;
    TextView txtTime;

    static {
        sIntentFilter.addAction("android.intent.action.TIME_TICK");
        sIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        sIntentFilter.addAction("android.intent.action.TIME_SET");
        sIntentFilter.addAction("android.intent.action.DATE_CHANGED");
        sIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kzl_lock_preview);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        TYPE = getSharedPreferences("MY_PREF", 0).getInt("zipperPos", 1);
        prefs = getSharedPreferences(String.valueOf(getPackageName()), 0);
        timeEnabled = this.prefs.getBoolean(getString(R.string.TIME_ON_PREF_KEY), true);
        dateEnabled = this.prefs.getBoolean(getString(R.string.DATE_ON_PREF_KEY), true);
        batteryEnabled = this.prefs.getBoolean(getString(R.string.BATTERY_ON_PREF_KEY), true);
        timeFormat = this.prefs.getInt(getString(R.string.TIME_FORMAT_PREF_KEY), 1);
        this.dateFormat = getResources().getStringArray(R.array.date_formats)[this.prefs.getInt(getString(R.string.DATE_FORMAT_PREF_KEY), 0)];
        findViewById(R.id.preview_done_btn).setOnClickListener(this);
        this.imgZipper = findViewById(R.id.imgZipper);
        this.imgBg = findViewById(R.id.imgBg);
        int i = getSharedPreferences(String.valueOf(getPackageName()), 0).getInt(getString(R.string.BG_SELECTED_PREF_KEY), 0);
        ImageView imageView = this.imgBg;
        Resources resources = getResources();
        Resources resources2 = getResources();
        imageView.setImageBitmap(BitmapFactory.decodeResource(resources, resources2.getIdentifier("lock_bg_" + i, "drawable", getPackageName())));
        ViewTreeObserver viewTreeObserver = this.imgZipper.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= 16) {
                        imgZipper.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    maxWidth = imgZipper.getWidth();
                    maxHeight = imgZipper.getHeight();
                    try {
                        if (TYPE == 1) {
                            SetBitmapsVertical();
                        } else {
                            SetBitmapsHorizontal();
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                        finish();
                    }
                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeTDBHolder);
                    if (TYPE == 1) {
                        if (dateEnabled || batteryEnabled) {
                            relativeLayout.setVisibility(View.VISIBLE);
                            double d = (double) maxHeight;
                            Double.isNaN(d);
                            int i = (int) (d * 0.244375d);
                            double d2 = (double) maxHeight;
                            Double.isNaN(d2);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i, (int) (d2 * 0.21875d));
                            layoutParams.addRule(9, -1);
                            layoutParams.addRule(12, -1);
                            relativeLayout.setLayoutParams(layoutParams);
                            return;
                        }
                        relativeLayout.setVisibility(View.GONE);
                    } else if (dateEnabled || batteryEnabled) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        double d3 = (double) maxHeight;
                        Double.isNaN(d3);
                        int i2 = (int) (d3 * 0.261d);
                        double d4 = (double) maxHeight;
                        Double.isNaN(d4);
                        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i2, (int) (d4 * 0.21875d));
                        layoutParams2.addRule(14, -1);
                        layoutParams2.setMargins(0, screenWidth / 5, 0, 0);
                        relativeLayout.setLayoutParams(layoutParams2);
                    } else {
                        relativeLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
        this.txtTime = findViewById(R.id.txtTime);
        this.txtDate = findViewById(R.id.txtDate);
        if (!this.dateEnabled) {
            this.txtTime.setVisibility(View.INVISIBLE);
            this.txtDate.setVisibility(View.INVISIBLE);
        }
        this.txtBattery = findViewById(R.id.txtBattery);
        if (!this.batteryEnabled) {
            this.txtBattery.setVisibility(View.INVISIBLE);
            findViewById(R.id.imgBattery).setVisibility(View.INVISIBLE);
        }
        this.missedCallEnabled = this.prefs.getBoolean(getString(R.string.MISSED_CALL_ON_PREF_KEY), false);
        this.smsEnabled = this.prefs.getBoolean(getString(R.string.SMS_ON_PREF_KEY), false);
        if (this.missedCallEnabled) {
            findViewById(R.id.imgMissedCall).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.imgMissedCall).setVisibility(View.INVISIBLE);
        }
        if (this.smsEnabled) {
            findViewById(R.id.imgMissedSms).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.imgMissedSms).setVisibility(View.INVISIBLE);
        }
        registerReceiver(this.timeChangedReceiver, sIntentFilter);
    }

    public void SetBitmapsHorizontal() {
        int i = getSharedPreferences(String.valueOf(getPackageName()), 0).getInt(getString(R.string.ZIPPER_SELECTED_PREF_KEY), 0);
        int i2 = getSharedPreferences(String.valueOf(getPackageName()), 0).getInt(getString(R.string.PENDANT_SELECTED_PREF_KEY), 0);
        Resources resources = getResources();
        Resources resources2 = getResources();
        this.bmpZipper = BitmapFactory.decodeResource(resources, resources2.getIdentifier("zipper_h_" + i, "drawable", getPackageName()));
        this.bmpZipper = Bitmap.createScaledBitmap(this.bmpZipper, this.maxWidth * 2, this.maxHeight, true);
        Resources resources3 = getResources();
        Resources resources4 = getResources();
        this.bmpPendant = BitmapFactory.decodeResource(resources3, resources4.getIdentifier("pendant_h_" + i2, "drawable", getPackageName()));
        this.bmpPendant = Bitmap.createScaledBitmap(this.bmpPendant, this.maxWidth * 2, this.maxHeight, true);
        this.bmpRezFront = Bitmap.createBitmap(this.maxWidth, this.maxHeight, Bitmap.Config.ARGB_8888);
        int i3 = getSharedPreferences(String.valueOf(getPackageName()), 0).getInt(getString(R.string.BG_SELECTED_PREF_KEY), 0);
        Resources resources5 = getResources();
        Resources resources6 = getResources();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources5, resources6.getIdentifier("bg_zipper_" + i3, "drawable", getPackageName())), this.maxWidth, this.maxHeight, false);
        Canvas canvas = new Canvas(this.bmpRezFront);
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(this.bmpZipper, (float) (-this.maxWidth), 0.0f, (Paint) null);
        canvas.drawBitmap(this.bmpPendant, (float) (-this.maxWidth), 0.0f, (Paint) null);
        this.imgZipper.setImageBitmap(this.bmpRezFront);
    }


    public void SetBitmapsVertical() {
        int i = 0;
        getSharedPreferences("MY_PREF", 0).getInt("height", 1);
        int i2 = getSharedPreferences(String.valueOf(getPackageName()), 0).getInt(getString(R.string.ZIPPER_SELECTED_PREF_KEY), 0);
        int i3 = getSharedPreferences(String.valueOf(getPackageName()), 0).getInt(getString(R.string.PENDANT_SELECTED_PREF_KEY), 0);
        int i4 = getSharedPreferences(String.valueOf(getPackageName()), 0).getInt(getString(R.string.BG_SELECTED_PREF_KEY), 0);
        Resources resources = getResources();
        Resources resources2 = getResources();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, resources2.getIdentifier("bg_zipper_" + i4, "drawable", getPackageName())), this.maxWidth, this.maxHeight, true);
        this.bmpRezFront = Bitmap.createBitmap(this.maxWidth, this.maxHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.bmpRezFront);
        Resources resources3 = getResources();
        Resources resources4 = getResources();
        this.bmpZipper = BitmapFactory.decodeResource(resources3, resources4.getIdentifier("zipper_v_" + i2, "drawable", getPackageName()));
        Resources resources5 = getResources();
        Resources resources6 = getResources();
        this.bmpPendant = BitmapFactory.decodeResource(resources5, resources6.getIdentifier("pendant_v_" + i3, "drawable", getPackageName()));
        double height = (double) this.bmpZipper.getHeight();
        Double.isNaN(height);
        int i5 = (int) (height * 0.3535d);
        int height2 = (int) ((((float) this.maxHeight) / ((float) (this.bmpZipper.getHeight() - i5))) * ((float) i5));
        int width = (int) (((float) this.bmpZipper.getWidth()) * (((float) (this.maxHeight + height2)) / ((float) this.bmpZipper.getHeight())));
        if (this.maxWidth <= width) {
            this.bmpZipper = Bitmap.createScaledBitmap(this.bmpZipper, width, this.maxHeight + height2, true);
            i = (this.bmpZipper.getWidth() - this.maxWidth) / 2;
        } else {
            this.bmpZipper = Bitmap.createScaledBitmap(this.bmpZipper, this.maxWidth, (int) ((((float) this.maxWidth) / ((float) width)) * ((float) (this.maxHeight + height2))), true);
        }
        double height3 = (double) this.bmpZipper.getHeight();
        Double.isNaN(height3);
        this.bmpPendant = Bitmap.createScaledBitmap(this.bmpPendant, this.bmpZipper.getWidth(), this.bmpZipper.getHeight(), true);
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        float f = (float) (-i);
        float f2 = (float) (-((int) (height3 * 0.3535d)));
        canvas.drawBitmap(this.bmpZipper, f, f2, (Paint) null);
        canvas.drawBitmap(this.bmpPendant, f, f2, (Paint) null);
        this.imgZipper.setImageBitmap(this.bmpRezFront);
    }

    public void refreshBatteryPercentage() {
        if (this.batteryEnabled) {
            Intent registerReceiver = registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
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

    public void onClick(View view) {
        if (view.getId() == R.id.preview_done_btn) {
            onBackPressed();
        }
    }

    public void onDestroy() {

        if (this.bmpRezFront != null) {
            this.bmpRezFront.recycle();
            this.bmpRezFront = null;
        }
        if (this.bmpZipper != null) {
            this.bmpZipper.recycle();
            this.bmpZipper = null;
        }
        if (this.bmpPendant != null) {
            this.bmpPendant.recycle();
            this.bmpPendant = null;
        }
        super.onDestroy();
    }


    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        AppState.getInstance().SetVisible(false);
    }


    public void onResume() {
        super.onResume();

        AppState.getInstance().SetVisible(true);
    }

    @Override
    protected Context getContext() {
        return KZL_LockPreviewActivity.this;
    }
}