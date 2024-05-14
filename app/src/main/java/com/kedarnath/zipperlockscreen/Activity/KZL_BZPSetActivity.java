package com.kedarnath.zipperlockscreen.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kedarnath.zipperlockscreen.Adapter.ListItemAdapter;

import com.kedarnath.zipperlockscreen.Apputils.AppState;
import com.kedarnath.zipperlockscreen.KedarnathWallpaper.Activity.KSWCategoryActivity;
import com.kedarnath.zipperlockscreen.R;

public class KZL_BZPSetActivity extends BaseActivity implements View.OnClickListener, ListItemAdapter.BgItemViewClickListener {
    static SharedPreferences spf;
    private int TYPE = 1;
    ImageView bgDoneBtn;
    RecyclerView bgRecyclerView;
    GridLayoutManager gridLayoutManager;
    ListItemAdapter mListItemAdapter;
    int selectedItem;
    SharedPreferences.Editor spfEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kzl_bzpset);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        TYPE = getIntent().getIntExtra("TYPE", 1);
        spf = getSharedPreferences(String.valueOf(getPackageName()), 0);
        spfEdit = spf.edit();
        bgDoneBtn = findViewById(R.id.bg_done_btn);
        bgDoneBtn.setOnClickListener(this);
        switch (this.TYPE) {
            case 1:
                this.selectedItem = spf.getInt(getString(R.string.BG_SELECTED_PREF_KEY), 0);
                bgDoneBtn.setImageResource(R.drawable.set_background);
                break;
            case 2:
                this.selectedItem = spf.getInt(getString(R.string.ZIPPER_SELECTED_PREF_KEY), 0);
                bgDoneBtn.setImageResource(R.drawable.set_zipper);

                break;
            case 3:
                this.selectedItem = spf.getInt(getString(R.string.PENDANT_SELECTED_PREF_KEY), 0);
                bgDoneBtn.setImageResource(R.drawable.set_pendant);
                break;
        }
        bgRecyclerView = findViewById(R.id.bg_recycler_view);
        mListItemAdapter = new ListItemAdapter(this, this, this.selectedItem, this.TYPE);
        gridLayoutManager = new GridLayoutManager((Context) this, 2, 1, false);
        bgRecyclerView.setLayoutManager(this.gridLayoutManager);
        bgRecyclerView.setAdapter(this.mListItemAdapter);
        bgRecyclerView.setHasFixedSize(true);
        bgRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bgRecyclerView.scrollToPosition(this.selectedItem);
        bgDoneBtn = findViewById(R.id.bg_done_btn);
        bgDoneBtn.setOnClickListener(this);
    }

    public void listViewClickListener(View view, int i) {
        this.mListItemAdapter.setSelectedBackground(i);
        this.selectedItem = i;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bg_done_btn) {
            switch (this.TYPE) {
                case 1:
                    spfEdit.putInt(getString(R.string.BG_SELECTED_PREF_KEY), selectedItem);
                    spfEdit.commit();
                    break;
                case 2:
                    spfEdit.putInt(getString(R.string.ZIPPER_SELECTED_PREF_KEY), selectedItem);
                    spfEdit.commit();
                    break;
                case 3:
                    spfEdit.putInt(getString(R.string.PENDANT_SELECTED_PREF_KEY), selectedItem);
                    spfEdit.commit();
                    break;
            }
            onBackPressed();
        }
    }

    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        AppState.getInstance().SetVisible(false);
    }

    @Override
    protected Context getContext() {
        return KZL_BZPSetActivity.this;
    }
}