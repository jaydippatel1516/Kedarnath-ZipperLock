package com.kedarnath.zipperlockscreen.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kedarnath.zipperlockscreen.Apputils.AppState;
import com.kedarnath.zipperlockscreen.KedarnathWallpaper.Activity.KSWCategoryActivity;
import com.kedarnath.zipperlockscreen.R;

public class KZL_CustomizeActivity extends BaseActivity implements View.OnClickListener {
    ImageView customizeDoneBtn;
    TextView horizontalText;
    ImageView previewBtn;
    ImageView setBgBtn;
    ImageView setDateTimeBtn;
    ImageView setPendantBtn;
    ImageView setZipperColorBtn;
    TextView verticalText;
    RelativeLayout zipperPositionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kzl_customize);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());

        setBgBtn = findViewById(R.id.set_bg_btn);
        setDateTimeBtn = findViewById(R.id.set_date_and_time_btn);
        setZipperColorBtn = findViewById(R.id.set_zipper_color_btn);
        setPendantBtn = findViewById(R.id.set_pendant_btn);
        customizeDoneBtn = findViewById(R.id.customize_done_btn);
        zipperPositionBtn = findViewById(R.id.set_zipper_position_btn);
        previewBtn = findViewById(R.id.preview_btn);
        if (getString(R.string.enable_position_switch).equalsIgnoreCase("0")) {
            this.zipperPositionBtn.setVisibility(View.GONE);
        }
        setBgBtn.setOnClickListener(this);
        setDateTimeBtn.setOnClickListener(this);
        setZipperColorBtn.setOnClickListener(this);
        setPendantBtn.setOnClickListener(this);
        customizeDoneBtn.setOnClickListener(this);
        zipperPositionBtn.setOnClickListener(this);
        previewBtn.setOnClickListener(this);
        verticalText = findViewById(R.id.txtVertical);
        horizontalText = findViewById(R.id.txtHorizontal);
        if (getSharedPreferences("MY_PREF", 0).getInt("zipperPos", 1) == 1) {
            this.zipperPositionBtn.setBackgroundResource(R.drawable.switch_right);
        } else {
            this.zipperPositionBtn.setBackgroundResource(R.drawable.switch_left);
        }
        findViewById(R.id.set_wallpaper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KZL_CustomizeActivity.this, KSWCategoryActivity.class));
            }
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.preview_btn) {
            startActivity(new Intent(KZL_CustomizeActivity.this, KZL_LockPreviewActivity.class));
        } else if (view.getId() == R.id.set_bg_btn) {
            Intent intent = new Intent(KZL_CustomizeActivity.this, KZL_BZPSetActivity.class);
            intent.putExtra("TYPE", 1);
            startActivity(intent);
        } else if (view.getId() == R.id.set_date_and_time_btn) {
            Intent intent = new Intent(KZL_CustomizeActivity.this, KZL_DateTimeFormatActivity.class);
            intent.putExtra("TYPE", 1);
            startActivity(intent);
        } else if (view.getId() == R.id.set_pendant_btn) {
            Intent intent = new Intent(KZL_CustomizeActivity.this, KZL_BZPSetActivity.class);
            intent.putExtra("TYPE", 3);
            startActivity(intent);
        } else if (view.getId() == R.id.set_zipper_color_btn) {
            Intent intent = new Intent(KZL_CustomizeActivity.this, KZL_BZPSetActivity.class);
            intent.putExtra("TYPE", 2);
            startActivity(intent);
        } else if (view.getId() == R.id.set_zipper_position_btn) {
            if (getSharedPreferences("MY_PREF", 0).getInt("zipperPos", 1) == 1) {
                getSharedPreferences("MY_PREF", 0).edit().putInt("zipperPos", 2).apply();
                this.zipperPositionBtn.setBackgroundResource(R.drawable.switch_left);
                return;
            }
            getSharedPreferences("MY_PREF", 0).edit().putInt("zipperPos", 1).apply();
            this.zipperPositionBtn.setBackgroundResource(R.drawable.switch_right);
        }
    }


    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        AppState.getInstance().SetVisible(false);
    }

    @Override
    protected Context getContext() {
        return KZL_CustomizeActivity.this;
    }
}