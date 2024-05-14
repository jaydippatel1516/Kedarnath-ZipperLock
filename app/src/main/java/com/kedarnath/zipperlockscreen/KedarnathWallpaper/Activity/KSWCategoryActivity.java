package com.kedarnath.zipperlockscreen.KedarnathWallpaper.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kedarnath.zipperlockscreen.Activity.BaseActivity;


import com.kedarnath.zipperlockscreen.R;

public class KSWCategoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kswcategory);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.hd4k).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KSWCategoryActivity.this, HD4KWallpaperActivity.class));

            }
        });
        findViewById(R.id.daily).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KSWCategoryActivity.this, DailyWallpaperActivity.class));

            }
        });
        findViewById(R.id.classic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KSWCategoryActivity.this, ClassicWallpaperActivity.class));


            }
        });
        findViewById(R.id.unique).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KSWCategoryActivity.this, UniqueWallpaperActivity.class));

            }
        });
        findViewById(R.id.top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KSWCategoryActivity.this, TopWallpaperActivity.class));


            }
        });
    }

    @Override
    protected Context getContext() {
        return KSWCategoryActivity.this;
    }
}