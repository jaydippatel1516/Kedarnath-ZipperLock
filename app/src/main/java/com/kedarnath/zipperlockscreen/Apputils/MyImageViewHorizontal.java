package com.kedarnath.zipperlockscreen.Apputils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.MotionEventCompat;

import com.kedarnath.zipperlockscreen.R;


public class MyImageViewHorizontal extends AppCompatImageView implements View.OnTouchListener {
    private Bitmap bmpBg;
    private Bitmap bmpMask;
    private Bitmap bmpPendant;
    private Bitmap bmpZipper;
    private int center;
    float delta = 0.0f;
    private int height;
    double limit;
    Context mContext;
    private LockScreenUtils mLockScreenUtils;
    private int offset = 0;

    /* renamed from: p */
    Paint f74p;
    private int pendantLength;
    private int pendantWidth;
    private boolean shouldDrag = false;
    private int step = 0;
    private boolean unlock = false;
    private int width;

    /* renamed from: y */
    private int f75y = 0;

    public MyImageViewHorizontal(Context context) {
        super(context);
    }

    public MyImageViewHorizontal(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyImageViewHorizontal(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void SetLockScreenUtils(LockScreenUtils lockScreenUtils) {
        this.mLockScreenUtils = lockScreenUtils;
    }

    public void SetWidthAndHeight(Context context, int i, int i2) {
        Context context2 = context;
        int i3 = i;
        int i4 = i2;
        this.width = i3;
        this.height = i4;
        setLayerType(2, (Paint) null);
        this.mContext = context2;
        setOnTouchListener(this);
        Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
        int i5 = context2.getSharedPreferences(String.valueOf(context.getPackageName()), 0).getInt(context2.getString(R.string.ZIPPER_SELECTED_PREF_KEY), 0);
        int i6 = context2.getSharedPreferences(String.valueOf(context.getPackageName()), 0).getInt(context2.getString(R.string.PENDANT_SELECTED_PREF_KEY), 0);
        int i7 = context2.getSharedPreferences(String.valueOf(context.getPackageName()), 0).getInt(context2.getString(R.string.BG_SELECTED_PREF_KEY), 0);
        this.bmpMask = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("mask_horizontal", "drawable", context.getPackageName()));
        int i8 = i3 * 2;
        this.bmpMask = Bitmap.createScaledBitmap(this.bmpMask, i8, i4, true);
        double height2 = (double) this.bmpMask.getHeight();
        Double.isNaN(height2);
        this.pendantWidth = (int) (height2 * 0.1015d);
        double width2 = (double) this.bmpMask.getWidth();
        Double.isNaN(width2);
        this.pendantLength = (int) (width2 * 0.16d);
        double height3 = (double) this.bmpMask.getHeight();
        Double.isNaN(height3);
        this.center = (int) (height3 * 0.7226d);
        double d = (double) i3;
        Double.isNaN(d);
        this.limit = d * 0.8d;
        Resources resources = context.getResources();
        Resources resources2 = context.getResources();
        this.bmpZipper = BitmapFactory.decodeResource(resources, resources2.getIdentifier("zipper_h_" + i5, "drawable", context.getPackageName()));
        this.bmpZipper = Bitmap.createScaledBitmap(this.bmpZipper, i8, i4, true);
        Bitmap createBitmap2 = Bitmap.createBitmap(this.bmpZipper, i3, 0, i3, i4);
        this.bmpZipper = Bitmap.createBitmap(this.bmpZipper, 0, 0, i3, i4);
        Resources resources3 = context.getResources();
        Resources resources4 = context.getResources();
        this.bmpPendant = BitmapFactory.decodeResource(resources3, resources4.getIdentifier("pendant_h_" + i6, "drawable", context.getPackageName()));
        this.bmpPendant = Bitmap.createScaledBitmap(this.bmpPendant, i8, i4, true);
        this.bmpPendant = Bitmap.createBitmap(this.bmpPendant, i3, 0, i3, i4);
        Resources resources5 = context.getResources();
        Resources resources6 = context.getResources();
        this.bmpBg = BitmapFactory.decodeResource(resources5, resources6.getIdentifier("bg_zipper_" + i7, "drawable", context.getPackageName()));
        float CheckDimensions = CheckDimensions(this.bmpBg.getWidth(), this.bmpBg.getHeight(), i3, i4);
        this.bmpBg = Bitmap.createScaledBitmap(this.bmpBg, (int) (((float) this.bmpBg.getWidth()) * CheckDimensions), (int) (((float) this.bmpBg.getHeight()) * CheckDimensions), true);
        this.f74p = new Paint(1);
        this.f74p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.f74p.setAntiAlias(true);
        this.f74p.setDither(true);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(this.bmpBg, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(createBitmap2, 0.0f, 0.0f, (Paint) null);
        this.bmpBg = Bitmap.createBitmap(createBitmap, 0, 0, createBitmap.getWidth(), createBitmap.getHeight());
        this.offset = (-this.bmpMask.getWidth()) / 2;
        this.f75y = 0;
        invalidate();
    }


    public float CheckDimensions(int i, int i2, int i3, int i4) {
        if (i >= i3 && i2 >= i4) {
            return Math.max(((float) i3) / ((float) i), ((float) i4) / ((float) i2));
        }
        if (i <= i3 && i2 <= i4) {
            return Math.max(((float) i3) / ((float) i), ((float) i4) / ((float) i2));
        }
        if (i > i3 && i2 <= i4) {
            return ((float) i4) / ((float) i2);
        }
        if (i2 <= i4 || i > i3) {
            return 1.0f;
        }
        return ((float) i3) / ((float) i);
    }

    public void ChangeImages(float f) {
        this.unlock = ((double) ((this.delta / 2.0f) + f)) >= this.limit;
        if (f < ((float) (this.width - (this.pendantLength / 2)))) {
            this.f75y = (int) f;
            invalidate();
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0:
                this.unlock = false;
                if (motionEvent.getY() > ((float) (this.center - (this.pendantWidth / 2))) && motionEvent.getY() < ((float) (this.center + (this.pendantWidth / 2))) && motionEvent.getX() < ((float) this.pendantLength)) {
                    this.shouldDrag = true;
                    this.delta = motionEvent.getX();
                    break;
                }
            case 1:
                Log.e("ACTION", "UP");
                this.shouldDrag = false;
                if (this.unlock) {
                    if (!this.mContext.getSharedPreferences("MY_PREF", 0).getBoolean("pinLock", false)) {
                        this.mLockScreenUtils.unlockScreen();
                        break;
                    } else {
                        setVisibility(GONE);
                        this.mLockScreenUtils.tdbHolder.setVisibility(GONE);
                        break;
                    }
                } else {
                    this.f75y = 0;
                    invalidate();
                    break;
                }
            case 2:
                if (this.shouldDrag && motionEvent.getX() - this.delta >= 0.0f) {
                    ChangeImages(motionEvent.getX() - this.delta);
                    break;
                }
        }
        return true;
    }


    public void onDraw(Canvas canvas) {
        if (this.bmpBg != null && this.bmpMask != null && this.bmpZipper != null && this.bmpPendant != null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(this.bmpMask, (float) (this.offset + this.f75y), 0.0f, (Paint) null);
            canvas.drawBitmap(this.bmpBg, 0.0f, 0.0f, this.f74p);
            canvas.drawBitmap(this.bmpZipper, (float) (this.offset + this.f75y), 0.0f, (Paint) null);
            canvas.drawBitmap(this.bmpPendant, (float) this.f75y, 0.0f, (Paint) null);
        }
    }

    public void ResetBitmaps() {
        setVisibility(VISIBLE);
        this.f75y = 0;
        invalidate();
    }

    public void DestroyBitmaps() {
        if (this.bmpMask != null) {
            this.bmpMask.recycle();
            this.bmpMask = null;
        }
        if (this.bmpZipper != null) {
            this.bmpZipper.recycle();
            this.bmpZipper = null;
        }
        if (this.bmpPendant != null) {
            this.bmpPendant.recycle();
            this.bmpPendant = null;
        }
        if (this.bmpBg != null) {
            this.bmpBg.recycle();
            this.bmpBg = null;
        }
    }
}
