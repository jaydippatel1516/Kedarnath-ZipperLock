package com.kedarnath.zipperlockscreen.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.kedarnath.zipperlockscreen.Apputils.AppState;
import com.kedarnath.zipperlockscreen.Apputils.PasswordDialog;
import com.kedarnath.zipperlockscreen.R;
import com.kedarnath.zipperlockscreen.Service.LockScreenService;
import com.kedarnath.zipperlockscreen.Service.OverlayPermissionIntentService;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class KZL_MainActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    public static String CURRENT_PASSWORD;
    public static String[] PERMISSION_LIST = {"android.permission.READ_PHONE_STATE"};
    public static int PERMISSION_REQUEST = 0;
    public static boolean RESUME_FROM_SERVICE;
    static SharedPreferences spf;
    private boolean appIsUsable;
    boolean lockIsOn;
    ImageView customizeBtn;
    TextView customizeText;
    RelativeLayout enableLockScreenBtn;
    CheckBox enableLockScreenCheckBox;
    CheckBox enablePinLockCheckBox;
    RelativeLayout enablePinLockBtn;
    ImageLoader imageLoader;
    ImageView setPasswordBtn;
    SharedPreferences.Editor spfEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kzl_main);

        spf = getSharedPreferences(String.valueOf(getPackageName()), 0);
        this.spfEdit = spf.edit();
        this.lockIsOn = spf.getBoolean(getString(R.string.lock_on_key), false);
        CURRENT_PASSWORD = spf.getString(getString(R.string.PIN_PREF_KEY), "");
        if (Build.VERSION.SDK_INT < 23) {
            this.appIsUsable = true;
            loadApp();
        } else if (ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != 0) {
            this.appIsUsable = false;
        } else {
            this.appIsUsable = true;
            loadApp();
        }
        initImageLoader();
        initialize();
        this.spfEdit.apply();
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_PHONE_STATE")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.permission_have_to));
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(KZL_MainActivity.this, KZL_MainActivity.PERMISSION_LIST, KZL_MainActivity.PERMISSION_REQUEST);
                }
            });
            builder.create().show();
            return;
        }
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage(getString(R.string.permission_have_to));
        builder2.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(KZL_MainActivity.this, KZL_MainActivity.PERMISSION_LIST, KZL_MainActivity.PERMISSION_REQUEST);
            }
        });
        builder2.create().show();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != PERMISSION_REQUEST) {
            return;
        }
        if (iArr.length == 1 && iArr[0] == -1) {
            this.appIsUsable = false;
            this.enableLockScreenCheckBox.setChecked(false);
            ChangeCheckBoxValue(this.enableLockScreenCheckBox);
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, strArr[0])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.permission_have_to));
                builder.setNegativeButton(getString(R.string.settings), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        intent.addFlags(268435456);
                        intent.addFlags(1073741824);
                        intent.addFlags(8388608);
                        startActivity(intent);
                    }
                });
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        } else if (iArr.length == 1 && iArr[0] == 0) {
            this.appIsUsable = true;
            loadApp();
            checkDrawOverlayPermission();
        }
    }

    private void initImageLoader() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.ARGB_8888).build()).memoryCache(new WeakMemoryCache()).build();
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(build);
    }

    private void loadApp() {
        RESUME_FROM_SERVICE = false;
        if (this.lockIsOn) {
            foregroundStartService();
        }
    }

    private void initialize() {
        enableLockScreenBtn = findViewById(R.id.enable_lockscreen_btn);
        enableLockScreenBtn.setOnClickListener(this);
        enablePinLockBtn = findViewById(R.id.enable_pinlock_btn);
        enablePinLockBtn.setOnClickListener(this);
        setPasswordBtn = findViewById(R.id.set_password_btn);
        setPasswordBtn.setOnClickListener(this);
        customizeBtn = findViewById(R.id.customize_btn);
        customizeBtn.setOnClickListener(this);
        enableLockScreenCheckBox = findViewById(R.id.enable_lockscreen_checkbox);
        enableLockScreenCheckBox.setChecked(this.lockIsOn);
        enableLockScreenCheckBox.setOnCheckedChangeListener(this);
        enablePinLockCheckBox = findViewById(R.id.enable_pinlock_checkbox);
        enablePinLockCheckBox.setChecked(getSharedPreferences("MY_PREF", 0).getBoolean("pinLock", false));
        enablePinLockBtn.setClickable(this.lockIsOn);
        if (this.appIsUsable) {
            this.enableLockScreenCheckBox.setChecked(this.lockIsOn);
            ChangeCheckBoxValue(this.enableLockScreenCheckBox);
            return;
        }
        this.enableLockScreenCheckBox.setChecked(false);
        ChangeCheckBoxValue(this.enableLockScreenCheckBox);
    }

    public void showPasswordDialog() {
        final PasswordDialog passwordDialog = new PasswordDialog(this);
        passwordDialog.setCancelable(true);
        passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                if (KZL_MainActivity.CURRENT_PASSWORD.equals("")) {
                    enablePinLockCheckBox.setChecked(false);
                } else {
                    if (PasswordDialog.OK_CLICKED) {
                        if (Build.VERSION.SDK_INT < 23) {
                            enablePinLockCheckBox.setChecked(true);
                        } else if (!Settings.canDrawOverlays(KZL_MainActivity.this.getApplicationContext())) {
                            startService(new Intent(KZL_MainActivity.this, OverlayPermissionIntentService.class));
                            startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())));
                        } else {
                            enablePinLockCheckBox.setChecked(true);
                        }
                    }
                }
                getSharedPreferences("MY_PREF", 0).edit().putBoolean("pinLock", enablePinLockCheckBox.isChecked()).apply();
                setPasswordClickable();
            }
        });
        passwordDialog.show();
    }

    private void ChangeCheckBoxValue(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            this.spfEdit.putInt(String.valueOf(getPackageName()), 1);
            this.spfEdit.commit();
            foregroundStartService();
        } else {
            this.spfEdit.putInt(String.valueOf(getPackageName()), 0);
            this.spfEdit.commit();
            foregroundStopService();
            Intent intent = new Intent();
            intent.setAction(getPackageName());
            sendBroadcast(intent);
        }
        this.enablePinLockBtn.setClickable(checkBox.isChecked());
        if (checkBox.isChecked()) {
        } else {
            this.enablePinLockCheckBox.setChecked(false);
            getSharedPreferences("MY_PREF", 0).edit().putBoolean("pinLock", this.enablePinLockCheckBox.isChecked()).apply();
        }
        this.spfEdit.putBoolean(getString(R.string.lock_on_key), checkBox.isChecked());
        this.spfEdit.commit();
        setPasswordClickable();
    }

    private void foregroundStartService() {
        Intent intent = new Intent(this, LockScreenService.class);
        intent.setAction(LockScreenService.ACTION_START_FOREGROUND_SERVICE);
        startService(intent);
    }

    private void foregroundStopService() {
        Intent intent = new Intent(this, LockScreenService.class);
        intent.setAction(LockScreenService.ACTION_STOP_FOREGROUND_SERVICE);
        startService(intent);
    }


    public void checkForPass() {
        if (CURRENT_PASSWORD.equals("")) {
            showPasswordDialog();
            return;
        }
        this.enablePinLockCheckBox.setChecked(!this.enablePinLockCheckBox.isChecked());
        getSharedPreferences("MY_PREF", 0).edit().putBoolean("pinLock", this.enablePinLockCheckBox.isChecked()).apply();
        setPasswordClickable();
    }


    public void setPasswordClickable() {
        if (!this.enableLockScreenCheckBox.isChecked() || !this.enablePinLockCheckBox.isChecked()) {
            this.setPasswordBtn.setClickable(false);
            return;
        }
        this.setPasswordBtn.setClickable(true);
    }


    public void onResume() {
        super.onResume();
        AppState.getInstance().SetVisible(true);
        if (OverlayPermissionIntentService.isPermissionEnabled && RESUME_FROM_SERVICE) {
            RESUME_FROM_SERVICE = false;
            this.enableLockScreenCheckBox.setChecked(true);
        }
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != 0) {
            this.enableLockScreenCheckBox.setChecked(false);
        }
        ChangeCheckBoxValue(this.enableLockScreenCheckBox);
    }

    public void checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            this.enableLockScreenCheckBox.setChecked(!this.enableLockScreenCheckBox.isChecked());
            ChangeCheckBoxValue(this.enableLockScreenCheckBox);
        } else if (!Settings.canDrawOverlays(getApplicationContext())) {
            startService(new Intent(this, OverlayPermissionIntentService.class));
            startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())));
        } else {
            this.enableLockScreenCheckBox.setChecked(!this.enableLockScreenCheckBox.isChecked());
            ChangeCheckBoxValue(this.enableLockScreenCheckBox);
        }
    }


    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        AppState.getInstance().SetVisible(false);
    }


    @Override
    protected Context getContext() {
        return KZL_MainActivity.this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.customize_btn) {
            startActivity(new Intent(KZL_MainActivity.this, KZL_CustomizeActivity.class));
        } else if (v.getId() == R.id.enable_lockscreen_btn) {
            if (this.appIsUsable) {
                checkDrawOverlayPermission();
                return;
            } else {
                requestPermissions();
                return;
            }
        } else if (v.getId() == R.id.enable_pinlock_btn) {
            checkForPass();
        } else if (v.getId() == R.id.set_password_btn) {
            showPasswordDialog();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}