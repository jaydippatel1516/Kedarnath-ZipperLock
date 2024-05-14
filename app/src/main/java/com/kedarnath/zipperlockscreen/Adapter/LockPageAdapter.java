package com.kedarnath.zipperlockscreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class LockPageAdapter extends PagerAdapter {
    public LayoutInflater inflater;
    View pinView;
    View swipeView;
    View view;

    public int getCount() {
        return 2;
    }

    public LockPageAdapter(View view2, View view3) {
        this.swipeView = view2;
        this.pinView = view3;
    }

    public boolean isViewFromObject(View view2, Object obj) {
        return view2 == ((View) obj);
    }

    public Object instantiateItem(View view2, int i) {
        this.inflater = (LayoutInflater) view2.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (i == 0) {
            this.view = this.pinView;
        } else {
            this.view = this.swipeView;
        }
        ((ViewPager) view2).addView(this.view);
        return this.view;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ((ViewPager) viewGroup).removeView((View) obj);
    }
}
