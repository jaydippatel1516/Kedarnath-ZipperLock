package com.kedarnath.zipperlockscreen.KedarnathWallpaper.Activity;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.kedarnath.zipperlockscreen.Activity.BaseActivity;
import com.kedarnath.zipperlockscreen.KedarnathWallpaper.AppUtils.SaveImageHelper;
import com.kedarnath.zipperlockscreen.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ViewActivity extends BaseActivity {
    ImageView fullimage;
    ImageView setWallpaper;
    ImageView shareWallpaper;
    ImageView download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());

        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())), 1234);
        }
        fullimage = findViewById(R.id.fullimage);
        setWallpaper = findViewById(R.id.setWallpaper);
        shareWallpaper = findViewById(R.id.shareWallpaper);
        download = findViewById(R.id.download);
        Picasso.get().load(getIntent().getStringExtra("images")).into(fullimage);
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground();
                Toast.makeText(ViewActivity.this, "SET WALLPAPER", Toast.LENGTH_SHORT).show();
            }
        });
        shareWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
                Toast.makeText(ViewActivity.this, "DOWNLOAD WALLPAPER", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void shareImage() {
        Bitmap bitmapFromView = getBitmapFromView(fullimage);
        try {
            File file = new File(getExternalCacheDir(), "black.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmapFromView.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true, false);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setFlags(268435456);
            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this,getApplicationContext().getPackageName() + ".fileprovider", file));
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        view.draw(canvas);
        return createBitmap;
    }

    private void setBackground() {
        Bitmap bitmap = ((BitmapDrawable) fullimage.getDrawable()).getBitmap();
        WallpaperManager instance = WallpaperManager.getInstance(getApplicationContext());
        try {
            Toast.makeText(this, "Set Wallpaper Successfully..", Toast.LENGTH_SHORT).show();
            instance.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveImage() {
        Picasso.get().load(getIntent().getStringExtra("images")).into((Target) new SaveImageHelper(getBaseContext(), getApplicationContext().getContentResolver(), UUID.randomUUID().toString() + ".jpg", "Image Description"));

    }

    @Override
    protected Context getContext() {
        return ViewActivity.this;
    }
}