package com.kedarnath.zipperlockscreen.Apputils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kedarnath.zipperlockscreen.Adapter.DateFormatAdapter;
import com.kedarnath.zipperlockscreen.R;


public class DateFormatDialog extends Dialog implements DateFormatAdapter.DateFormatItemClickListener {
    static SharedPreferences spf;
    Context context;
    DateFormatAdapter dateFormatAdapter;
    ImageView dateFormatDoneBtn;
    RecyclerView dateFormatList;
    SharedPreferences.Editor spfEdit;

    public DateFormatDialog(Context context2) {
        super(context2);
        this.context = context2;
        spf = context2.getSharedPreferences(String.valueOf(context2.getPackageName()), 0);
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_date_format);
        spfEdit = spf.edit();
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dateFormatDoneBtn = findViewById(R.id.date_format_done_btn);
        this.dateFormatDoneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DateFormatDialog.this.spfEdit.putInt(DateFormatDialog.this.context.getString(R.string.DATE_FORMAT_PREF_KEY), DateFormatDialog.this.dateFormatAdapter.getSelectedFormat());
                DateFormatDialog.this.spfEdit.commit();
                DateFormatDialog.this.dismiss();
            }
        });
        this.dateFormatList = (RecyclerView) findViewById(R.id.date_format_recycler);
        this.dateFormatAdapter = new DateFormatAdapter(this.context, this, spf);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
        linearLayoutManager.setOrientation(1);
        this.dateFormatList.setAdapter(this.dateFormatAdapter);
        this.dateFormatList.setHasFixedSize(true);
        this.dateFormatList.setLayoutManager(linearLayoutManager);
        this.dateFormatList.setItemAnimator(new DefaultItemAnimator());
        this.dateFormatList.scrollToPosition(this.dateFormatAdapter.getSelectedFormat());
    }

    public void listViewClickListener(View view, int i) {
        this.dateFormatAdapter.setSelectedFormat(i);
    }
}
