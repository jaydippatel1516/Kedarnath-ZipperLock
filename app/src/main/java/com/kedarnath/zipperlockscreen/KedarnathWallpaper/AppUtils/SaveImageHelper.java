package com.kedarnath.zipperlockscreen.KedarnathWallpaper.AppUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

public class SaveImageHelper implements Target {
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private Context context;
    private String desc;
    private String name;

    public void onPrepareLoad(Drawable drawable) {
    }

    public SaveImageHelper(Context context2, ContentResolver contentResolver, String str, String str2) {
        this.context = context2;
        this.contentResolverWeakReference = new WeakReference<>(contentResolver);
        this.name = str;
        this.desc = str2;
    }

    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        ContentResolver contentResolver = (ContentResolver) this.contentResolverWeakReference.get();
        if (contentResolver != null) {
            MediaStore.Images.Media.insertImage(contentResolver, bitmap, this.name, this.desc);
            Toast.makeText(this.context, "Successfully download..", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this.context, "Something Went to wrong", Toast.LENGTH_SHORT).show();
    }

    public void onBitmapFailed(Exception exc, Drawable drawable) {
        Context context2 = this.context;
        Toast.makeText(context2, "" + exc.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
