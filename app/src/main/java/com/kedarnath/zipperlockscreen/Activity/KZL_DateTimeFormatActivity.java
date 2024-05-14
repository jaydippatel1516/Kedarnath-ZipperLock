package com.kedarnath.zipperlockscreen.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import com.kedarnath.zipperlockscreen.Apputils.AppState;
import com.kedarnath.zipperlockscreen.Apputils.DateFormatDialog;
import com.kedarnath.zipperlockscreen.R;

public class KZL_DateTimeFormatActivity extends BaseActivity implements View.OnClickListener {
    static SharedPreferences spf;
    String BATTERY_ON_PREF_KEY;
    String DATE_FORMAT_PREF_KEY;
    String DATE_ON_PREF_KEY;
    String MISSED_CALL_ON_PREF_KEY;
    String SMS_ON_PREF_KEY;
    String TIME_FORMAT_PREF_KEY;
    String TIME_ON_PREF_KEY;
    private boolean f60am;
    boolean batteryEnabled;
    boolean dateEnabled;
    ImageView dateFormat;
    ImageView enableBatteryBtn;
    CheckBox enableBatteryCheckBox;
    ImageView enableDateBtn;
    CheckBox enableDateCheckBox;
    ImageView enableMissedCallBtn;
    CheckBox enableMissedCallCheckBox;
    boolean missedCallEnabled;
    boolean smsEnabled;
    SharedPreferences.Editor spfEdit;
    TextView time24hText;
    RelativeLayout timeAm24Btn;
    TextView timeAmPmText;
    ImageView timeDoneBtn;
    int timeFormatMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kzl_date_time_format);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        spf = getSharedPreferences(String.valueOf(getPackageName()), 0);
        spfEdit = spf.edit();
        TIME_ON_PREF_KEY = getString(R.string.TIME_ON_PREF_KEY);
        DATE_ON_PREF_KEY = getString(R.string.DATE_ON_PREF_KEY);
        BATTERY_ON_PREF_KEY = getString(R.string.BATTERY_ON_PREF_KEY);
        TIME_FORMAT_PREF_KEY = getString(R.string.TIME_FORMAT_PREF_KEY);
        DATE_FORMAT_PREF_KEY = getString(R.string.DATE_FORMAT_PREF_KEY);
        MISSED_CALL_ON_PREF_KEY = getString(R.string.MISSED_CALL_ON_PREF_KEY);
        SMS_ON_PREF_KEY = getString(R.string.SMS_ON_PREF_KEY);
        timeFormatMode = spf.getInt(TIME_FORMAT_PREF_KEY, 1);
        f60am = timeFormatMode == 0;
        dateEnabled = spf.getBoolean(DATE_ON_PREF_KEY, true);
        batteryEnabled = spf.getBoolean(BATTERY_ON_PREF_KEY, true);
        missedCallEnabled = spf.getBoolean(MISSED_CALL_ON_PREF_KEY, false);
        smsEnabled = spf.getBoolean(SMS_ON_PREF_KEY, false);
        timeAm24Btn = findViewById(R.id.time_holder);
        timeDoneBtn = findViewById(R.id.date_done_btn);
        dateFormat = findViewById(R.id.set_date_format_btn);
        enableDateBtn = findViewById(R.id.enable_date_btn);
        enableBatteryBtn = findViewById(R.id.enable_battery_btn);
        enableDateBtn = findViewById(R.id.enable_date_btn);
        enableBatteryBtn = findViewById(R.id.enable_battery_btn);
        enableMissedCallBtn = findViewById(R.id.enable_missed_call_btn);
        timeAm24Btn.setOnClickListener(this);
        timeDoneBtn.setOnClickListener(this);
        dateFormat.setOnClickListener(this);
        enableDateBtn.setOnClickListener(this);
        enableBatteryBtn.setOnClickListener(this);
        enableMissedCallBtn.setOnClickListener(this);
        timeAmPmText = findViewById(R.id.time_am_pm_text);
        time24hText = findViewById(R.id.time_24h_text);
        setTimeFormat(this.timeFormatMode);
        enableDateCheckBox = findViewById(R.id.enable_date_checkbox);
        enableBatteryCheckBox = findViewById(R.id.enable_battery_checkbox);
        enableMissedCallCheckBox = findViewById(R.id.enable_missed_call_checkbox);
        enableDateCheckBox.setChecked(this.dateEnabled);
        enableBatteryCheckBox.setChecked(this.batteryEnabled);
        enableMissedCallCheckBox.setChecked(this.missedCallEnabled);
        spfEdit.apply();
    }

    public void setTimeFormat(int i) {
        if (i == 1) {
            this.timeFormatMode = 1;
            this.timeAm24Btn.setBackgroundResource(R.drawable.switch_right);
        } else if (i == 0) {
            this.timeFormatMode = 0;
            this.timeAm24Btn.setBackgroundResource(R.drawable.switch_left);
        }
    }

    public void onClick(View view) {
        if(view.getId()==R.id.date_done_btn){
            spfEdit.putBoolean(this.BATTERY_ON_PREF_KEY, this.batteryEnabled);
            spfEdit.putBoolean(this.DATE_ON_PREF_KEY, this.dateEnabled);
            spfEdit.putInt(this.TIME_FORMAT_PREF_KEY, this.timeFormatMode);
            spfEdit.putBoolean(this.MISSED_CALL_ON_PREF_KEY, this.missedCallEnabled);
            spfEdit.putBoolean(this.SMS_ON_PREF_KEY, this.smsEnabled);
            spfEdit.commit();
            onBackPressed();
        }else if(view.getId()==R.id.enable_battery_btn){
            toggleBatteryEnabled();
        }else if(view.getId()==R.id.enable_date_btn){
            toggleDateEnabled();
        }else if(view.getId()==R.id.enable_missed_call_btn){
            toggleMissedCallEnabled();
        }else if(view.getId()==R.id.set_date_format_btn){
            showDateFormatDialog();
        }else if(view.getId()==R.id.time_holder){
            this.f60am = !this.f60am;
            if (this.f60am) {
                setTimeFormat(0);
                return;
            } else {
                setTimeFormat(1);
                return;
            }
        }

    }

    private void requestPermissions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.permission_notif_have_to));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean z = !missedCallEnabled;
                missedCallEnabled = z;
                smsEnabled = z;
                enableMissedCallCheckBox.setChecked(missedCallEnabled);
                Intent intent = new Intent();
                if (Build.VERSION.SDK_INT >= 22) {
                    intent.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                } else {
                    intent.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                }
                startActivity(intent);
            }
        });
        builder.create().show();
    }


    public void toggleDateEnabled() {
        this.dateEnabled = !this.dateEnabled;
        this.enableDateCheckBox.setChecked(this.dateEnabled);
    }


    public void toggleBatteryEnabled() {
        this.batteryEnabled = !this.batteryEnabled;
        this.enableBatteryCheckBox.setChecked(this.batteryEnabled);
        Log.v("CUSTOMIZE_TEST", "battery: " + this.batteryEnabled);
    }


    public void toggleMissedCallEnabled() {
        if (!this.missedCallEnabled) {
            requestPermissions();
            return;
        }
        boolean z = !this.missedCallEnabled;
        missedCallEnabled = z;
        smsEnabled = z;
        enableMissedCallCheckBox.setChecked(missedCallEnabled);
    }


    public void showDateFormatDialog() {
        new DateFormatDialog(this).show();
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
        return KZL_DateTimeFormatActivity.this;
    }
}