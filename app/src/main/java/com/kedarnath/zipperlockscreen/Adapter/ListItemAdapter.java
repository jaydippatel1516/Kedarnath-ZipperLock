package com.kedarnath.zipperlockscreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.kedarnath.zipperlockscreen.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ListItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static BgItemViewClickListener itemClickListener;
    private int TYPE;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    ImageLoaderConfiguration loaderConfiguration;
    DisplayImageOptions loaderDisplayOptions;
    int selectedBackground;


    public interface BgItemViewClickListener {
        void listViewClickListener(View view, int i);
    }

    public ListItemAdapter(Context context2, BgItemViewClickListener bgItemViewClickListener, int i, int i2) {
        this.context = context2;
        this.inflater = (LayoutInflater) context2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemClickListener = bgItemViewClickListener;
        this.selectedBackground = i;
        this.TYPE = i2;
        this.loaderConfiguration = new ImageLoaderConfiguration.Builder(this.context).build();
        this.loaderDisplayOptions = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(false).considerExifParams(true).build();
        ImageLoader.getInstance().init(this.loaderConfiguration);
        this.imageLoader = ImageLoader.getInstance();
    }

    public void setSelectedBackground(int i) {

        this.selectedBackground = i;
        notifyItemChanged(this.selectedBackground);

    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolderBg(from.inflate(R.layout.list_item_layout, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderBg viewHolderBg = (ViewHolderBg) viewHolder;
        switch (this.TYPE) {
            case 1:
                ImageLoader imageLoader2 = this.imageLoader;
                StringBuilder sb = new StringBuilder();
                sb.append("drawable://");
                sb.append(this.context.getResources().getIdentifier("thumb_bg_" + i, "drawable", this.context.getPackageName()));
                imageLoader2.displayImage(sb.toString(), viewHolderBg.picture);
                break;
            case 2:
                ImageLoader imageLoader3 = this.imageLoader;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("drawable://");
                sb2.append(this.context.getResources().getIdentifier("zipper_g_" + i, "drawable", this.context.getPackageName()));
                imageLoader3.displayImage(sb2.toString(), viewHolderBg.picture);
                break;
            case 3:
                ImageLoader imageLoader4 = this.imageLoader;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("drawable://");
                sb3.append(this.context.getResources().getIdentifier("pendant_g_" + i, "drawable", this.context.getPackageName()));
                imageLoader4.displayImage(sb3.toString(), viewHolderBg.picture);
                break;
        }

    }

    public int getItemCount() {
        int i;
        switch (this.TYPE) {
            case 1:
                i = Integer.parseInt(this.context.getString(R.string.number_of_backgrounds));
                break;
            case 2:
                i = Integer.parseInt(this.context.getString(R.string.number_of_zippers));
                break;
            case 3:
                i = Integer.parseInt(this.context.getString(R.string.number_of_pendants));
                break;
            default:
                i = 0;
                break;
        }
        return i;
    }

    public class ViewHolderBg extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView picture;

        public ViewHolderBg(View view) {
            super(view);
            this.picture = (ImageView) view.findViewById(R.id.bg_item_image);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            ListItemAdapter.itemClickListener.listViewClickListener(view, getAdapterPosition());
            Toast.makeText(context, "Selected", Toast.LENGTH_SHORT).show();
        }
    }


}
