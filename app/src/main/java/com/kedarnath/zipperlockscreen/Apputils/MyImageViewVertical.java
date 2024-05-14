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
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MotionEventCompat;

import com.kedarnath.zipperlockscreen.R;


public class MyImageViewVertical extends AppCompatImageView implements View.OnTouchListener {
    private Bitmap bmpBg;
    private Bitmap bmpMask;
    private Bitmap bmpPendant;
    private Bitmap bmpZipper;
    float delta = 0.0f;
    private int height;
    double limit;
    Context mContext;
    private LockScreenUtils mLockScreenUtils;
    private int offset = 0;

    /* renamed from: p */
    Paint f76p;
    private int pendantLength;
    private int pendantWidth;
    private boolean shouldDrag = false;
    private int step = 0;
    private boolean unlock = false;
    private int width;

    /* renamed from: y */
    private int f77y = 0;

    public MyImageViewVertical(Context context) {
        super(context);
    }

    public MyImageViewVertical(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyImageViewVertical(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void SetLockScreenUtils(LockScreenUtils lockScreenUtils) {
        this.mLockScreenUtils = lockScreenUtils;
    }

    public void ResetBitmaps() {
        setVisibility(VISIBLE);
        this.f77y = 0;
        invalidate();
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
        Resources resources = context.getResources();
        Resources resources2 = context.getResources();
        this.bmpZipper = BitmapFactory.decodeResource(resources, resources2.getIdentifier("zipper_v_" + i5, "drawable", context.getPackageName()));
        this.bmpMask = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("mask_vertical", "drawable", context.getPackageName()));
        Resources resources3 = context.getResources();
        Resources resources4 = context.getResources();
        this.bmpPendant = BitmapFactory.decodeResource(resources3, resources4.getIdentifier("pendant_v_" + i6, "drawable", context.getPackageName()));
        double height2 = (double) this.bmpZipper.getHeight();
        Double.isNaN(height2);
        this.step = (int) (height2 * 0.3535d);
        if (this.bmpZipper.getHeight() < this.step + i4) {
            int height3 = ((int) ((((float) i4) / ((float) (this.bmpZipper.getHeight() - this.step))) * ((float) this.step))) + i4;
            float f = (float) height3;
            int width2 = (int) (((float) this.bmpZipper.getWidth()) * (f / ((float) this.bmpZipper.getHeight())));
            if (i3 <= width2) {
                this.bmpZipper = Bitmap.createScaledBitmap(this.bmpZipper, width2, height3, true);
                this.offset = (this.bmpZipper.getWidth() - i3) / 2;
            } else {
                this.bmpZipper = Bitmap.createScaledBitmap(this.bmpZipper, i3, (int) ((((float) i3) / ((float) width2)) * f), true);
            }
            double height4 = (double) this.bmpZipper.getHeight();
            Double.isNaN(height4);
            this.step = (int) (height4 * 0.3535d);
            this.bmpMask = Bitmap.createScaledBitmap(this.bmpMask, this.bmpZipper.getWidth(), this.bmpZipper.getHeight(), true);
            this.bmpPendant = Bitmap.createScaledBitmap(this.bmpPendant, this.bmpZipper.getWidth(), this.bmpZipper.getHeight(), true);
        } else if (this.bmpZipper.getWidth() - i3 > 0) {
            this.offset = (this.bmpZipper.getWidth() - i3) / 2;
        }
        Bitmap createBitmap2 = Bitmap.createBitmap(this.bmpZipper, 0, this.step, this.bmpZipper.getWidth(), this.bmpZipper.getHeight() - this.step);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(createBitmap2, createBitmap2.getWidth(), i4, true);
        this.bmpZipper = Bitmap.createBitmap(this.bmpZipper, 0, 0, this.bmpZipper.getWidth(), this.step);
        Resources resources5 = context.getResources();
        Resources resources6 = context.getResources();
        this.bmpBg = BitmapFactory.decodeResource(resources5, resources6.getIdentifier("bg_zipper_" + i7, "drawable", context.getPackageName()));
        float CheckDimensions = CheckDimensions(this.bmpBg.getWidth(), this.bmpBg.getHeight(), i3, i4);
        this.bmpBg = Bitmap.createScaledBitmap(this.bmpBg, (int) (((float) this.bmpBg.getWidth()) * CheckDimensions), (int) (((float) this.bmpBg.getHeight()) * CheckDimensions), true);
        double width3 = (double) this.bmpMask.getWidth();
        Double.isNaN(width3);
        this.pendantWidth = ((int) (width3 * 0.16d)) / 2;
        double height5 = (double) this.bmpMask.getHeight();
        Double.isNaN(height5);
        this.pendantLength = (int) (height5 * 0.1162d);
        double d = (double) i4;
        Double.isNaN(d);
        this.limit = d * 0.8d;
        this.f76p = new Paint(1);
        this.f76p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.f76p.setAntiAlias(true);
        this.f76p.setDither(true);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(SupportMenu.CATEGORY_MASK);
        canvas.drawBitmap(this.bmpBg, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(createScaledBitmap, (float) (-this.offset), 0.0f, (Paint) null);
        this.bmpBg = Bitmap.createBitmap(createBitmap, 0, 0, createBitmap.getWidth(), createBitmap.getHeight());
        this.f77y = 0;
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
        Log.e("ACTION", "CHECK IMAGES");
        this.unlock = ((double) ((this.delta / 2.0f) + f)) >= this.limit;
        if (f < ((float) (this.height - (this.pendantLength / 2)))) {
            Log.e("ACTION", "INVALIDATE");
            this.f77y = (int) f;
            invalidate();
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0:
                Log.e("ACTION", "DOWN");
                this.unlock = false;
                if (motionEvent.getX() > ((float) ((this.width / 2) - this.pendantWidth)) && motionEvent.getX() < ((float) ((this.width / 2) + this.pendantWidth)) && motionEvent.getY() < ((float) this.pendantLength)) {
                    Log.e("ACTION", "DOWN SHOULD DRAG");
                    this.shouldDrag = true;
                    this.delta = motionEvent.getY();
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
                    this.f77y = 0;
                    invalidate();
                    break;
                }
            case 2:
                Log.e("ACTION", "MOVE");
                if (this.shouldDrag) {
                    Log.e("ACTION", "SHOULD DRAG");
                    if (motionEvent.getY() - this.delta >= 0.0f) {
                        ChangeImages(motionEvent.getY() - this.delta);
                        break;
                    }
                }
                break;
        }
        return true;
    }


    public void onDraw(Canvas canvas) {
        if (this.bmpBg != null && this.bmpMask != null && this.bmpZipper != null && this.bmpPendant != null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(this.bmpMask, (float) (-this.offset), (float) ((-this.step) + this.f77y), (Paint) null);
            canvas.drawBitmap(this.bmpBg, 0.0f, 0.0f, this.f76p);
            canvas.drawBitmap(this.bmpZipper, (float) (-this.offset), (float) ((-this.step) + this.f77y), (Paint) null);
            canvas.drawBitmap(this.bmpPendant, (float) (-this.offset), (float) ((-this.step) + this.f77y), (Paint) null);
        }
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
