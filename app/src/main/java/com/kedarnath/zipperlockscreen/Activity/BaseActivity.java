package com.kedarnath.zipperlockscreen.Activity;



import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kedarnath.zipperlockscreen.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public abstract class BaseActivity extends AppCompatActivity {

    public List<Callable<Void>> callables = new ArrayList();
    protected Context mContext;
    protected ProgressDialog mProgress;

    public static final boolean isUsageAccessAllowed(Context context) {

        try {
            int checkOpNoThrow = ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.getPackageName());
            if (checkOpNoThrow != 3) {
                if (checkOpNoThrow == 0) {
                    return true;
                }
            } else if (context.checkCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS") == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        } catch (Throwable unused) {
            unused.printStackTrace();
        }
        return false;
    }


    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 1000) {
            if (i == 1001 && isUsageAccessAllowed(this)) {
                callListener();
            }
        } else if (Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(this)) {
            callListener();
        }
    }

    private void callListener() {
        for (Callable call : this.callables) {
            try {
                call.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);

        mContext = getContext();
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage("Please wait....");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.setCancelable(false);

    }

    protected abstract Context getContext();


    public SpannableString setFontSizeForPath(String str, int i, int i2, int i3) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new AbsoluteSizeSpan(i3), i, i2, 0);
        return spannableString;
    }


    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        } else {
            Toast.makeText(mContext, "No Internet..", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (networkInfo != null) {
            return networkInfo.getState() == NetworkInfo.State.CONNECTED;
        } else {
            Toast.makeText(mContext, "No Internet..", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }

    public void askForPermission(String str, Integer num) {
        if (Build.VERSION.SDK_INT <= 22) {
            onPermissionGranted(num.intValue());
        } else if (ContextCompat.checkSelfPermission(this, str) == 0) {
            onPermissionGranted(num.intValue());
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
            ActivityCompat.requestPermissions(this, new String[]{str}, num.intValue());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{str}, num.intValue());
        }
    }

    public void onPermissionGranted(int i) {
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (ActivityCompat.checkSelfPermission(this, strArr[0]) == 0) {
            onPermissionGranted(i);
        } else {
            openSettingDialog();
        }
    }


    public void openSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((CharSequence) "Permission Denied").setMessage((CharSequence) "Do you want to allow in application Setting?").setIcon((int) R.mipmap.ic_launcher);
        builder.setPositiveButton((CharSequence) "Settings", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                BaseActivity.this.openAppSettings();
            }
        });
        builder.setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }


    public void openAppSettings() {
        Uri fromParts = Uri.fromParts("package", getApplicationContext().getPackageName(), (String) null);
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(fromParts);
        intent.addFlags(268435456);
        getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}