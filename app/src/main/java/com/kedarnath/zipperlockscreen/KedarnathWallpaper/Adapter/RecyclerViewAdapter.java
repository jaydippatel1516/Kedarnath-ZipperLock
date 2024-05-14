package com.kedarnath.zipperlockscreen.KedarnathWallpaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.kedarnath.zipperlockscreen.KedarnathWallpaper.Activity.ViewActivity;
import com.kedarnath.zipperlockscreen.Model.CustpmItems;
import com.kedarnath.zipperlockscreen.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder> {
    Context context;
    List<CustpmItems> itemsList;

    public RecyclerViewAdapter(List<CustpmItems> list, Context context2) {
        this.itemsList = list;
        this.context = context2;
    }

    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ImageViewHolder(LayoutInflater.from(this.context).inflate(R.layout.custom_layout, viewGroup, false));
    }

    public void onBindViewHolder(ImageViewHolder imageViewHolder, int i) {
        Picasso.get().load(this.itemsList.get(i).getUrl()).into(imageViewHolder.imageView);
    }

    public int getItemCount() {
        return this.itemsList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageView = (ImageView) view.findViewById(R.id.img);
        }

        public void onClick(View view) {
            Intent intent = new Intent(RecyclerViewAdapter.this.context, ViewActivity.class);
            intent.putExtra("images", RecyclerViewAdapter.this.itemsList.get(getAdapterPosition()).getUrl());
            RecyclerViewAdapter.this.context.startActivity(intent);
        }
    }
}
