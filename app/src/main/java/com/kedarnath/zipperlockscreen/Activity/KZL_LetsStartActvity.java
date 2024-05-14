package com.kedarnath.zipperlockscreen.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kedarnath.zipperlockscreen.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class KZL_LetsStartActvity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kzl_lets_start_actvity);

        PushDownAnim.setPushDownAnimTo(findViewById(R.id.start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(KZL_LetsStartActvity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        ).withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                startActivity(new Intent(KZL_LetsStartActvity.this, KZL_MainActivity.class));
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

    }


    @Override
    public void onBackPressed() {
        showExitDialog();

    }

    public void showExitDialog() {
        final Dialog dialog = new Dialog(this, R.style.fulldialog);
        dialog.setContentView(R.layout.exit_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        PushDownAnim.setPushDownAnimTo(dialog.findViewById(R.id.txt_yes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        PushDownAnim.setPushDownAnimTo(dialog.findViewById(R.id.txt_no)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    protected Context getContext() {
        return KZL_LetsStartActvity.this;
    }
}