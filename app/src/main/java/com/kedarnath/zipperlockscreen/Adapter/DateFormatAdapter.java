package com.kedarnath.zipperlockscreen.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kedarnath.zipperlockscreen.R;

import java.util.Date;

public class DateFormatAdapter extends RecyclerView.Adapter<DateFormatAdapter.ViewHolder> {

    public static DateFormatItemClickListener itemClickListener;
    Context context;
    Date date = new Date(System.currentTimeMillis());
    String[] formats;
    LayoutInflater inflater;
    int selectedFormat;
    SharedPreferences spf;

    public interface DateFormatItemClickListener {
        void listViewClickListener(View view, int i);
    }

    public DateFormatAdapter(Context context2, DateFormatItemClickListener dateFormatItemClickListener, SharedPreferences sharedPreferences) {
        this.context = context2;
        this.inflater = (LayoutInflater) context2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemClickListener = dateFormatItemClickListener;
        this.spf = sharedPreferences;
        this.selectedFormat = sharedPreferences.getInt(context2.getString(R.string.DATE_FORMAT_PREF_KEY), 0);
        this.formats = context2.getResources().getStringArray(R.array.date_formats);
    }

    public void setSelectedFormat(int i) {
        notifyItemChanged(this.selectedFormat);
        this.selectedFormat = i;
        notifyItemChanged(this.selectedFormat);
    }

    public int getSelectedFormat() {
        return this.selectedFormat;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.date_format_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.dateFormatText.setText(DateFormat.format(this.formats[i], this.date));
        if (this.selectedFormat == i) {
            viewHolder.dateFormatText.setTextColor(ContextCompat.getColor(this.context, R.color.date_format_item_text_color));
        } else {
            viewHolder.dateFormatText.setTextColor(ContextCompat.getColor(this.context, R.color.date_format_item_unselected));
        }
    }

    public int getItemCount() {
        return this.formats.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout dateFormatBg;
        TextView dateFormatText;

        public ViewHolder(View view) {
            super(view);
            this.dateFormatText = (TextView) view.findViewById(R.id.date_format_item_text);
            this.dateFormatBg = (RelativeLayout) view.findViewById(R.id.date_format_item_bg);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            DateFormatAdapter.itemClickListener.listViewClickListener(view, getPosition());
        }
    }
}
