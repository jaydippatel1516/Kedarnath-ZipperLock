package com.kedarnath.zipperlockscreen.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kedarnath.zipperlockscreen.Apputils.LockScreenUtils;

public class LockActivity extends AppCompatActivity {
    LockScreenUtils lockScreen;
    SharedPreferences prefs;
    WindowManager winManager;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        this.lockScreen = new LockScreenUtils(this);
        try {
            this.lockScreen.createLock();
        } catch (Exception unused) {
            Toast.makeText(this, "Error occurred, please try again", Toast.LENGTH_LONG).show();
            finish();
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(Build.VERSION.SDK_INT >= 26 ? 2038 : 2010, 808);
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                layoutParams.flags = 67108864;
                this.lockScreen.pinView.setSystemUiVisibility(3846);
            }
            layoutParams.flags = 16777216;
            this.winManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            getWindow().setAttributes(layoutParams);
            this.winManager.addView(this.lockScreen.pinView, layoutParams);
            this.prefs = getSharedPreferences((String) null, 0);
        } catch (Exception unused2) {
            Toast.makeText(this, "Error occurred, please try again later", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void setContentView(int i) {
        super.setContentView(this.lockScreen.pinView);
        this.lockScreen.createLock();
    }


    public void onDestroy() {
        try {
            this.lockScreen.destroyLock();
            this.winManager.removeView(this.lockScreen.pinView);
            System.gc();
            super.onDestroy();
        } catch (Exception unused) {
            System.gc();
            super.onDestroy();
        }
    }


    public void onResume() {
        this.lockScreen.resumeLock();
        super.onResume();
    }
}
