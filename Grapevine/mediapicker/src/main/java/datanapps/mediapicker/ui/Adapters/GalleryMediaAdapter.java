package datanapps.mediapicker.ui.adapters;


import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import datanapps.mediapicker.R;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import com.bumptech.glide.Glide;

import java.util.List;


/*
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019 */


public class GalleryMediaAdapter extends RecyclerView.Adapter<GalleryMediaAdapter.MyViewHolder>{
    private List<String> bitmapList;
    private List<String> bucketNames;
    private List<Boolean> selected;
    private Context context;
    private String FragmentType;

    public GalleryMediaAdapter(List<String> bitmapList,List<String> bucketNames, List<Boolean> selected,String FragmentType, Context context) {
        this.bitmapList = bitmapList;
        this.context=context;
        this.selected=selected;
        this.bucketNames=bucketNames;
        this.FragmentType=FragmentType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_media_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(FragmentType.equals("Audios")){
            String uri = "@drawable/audio";

            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable res = context.getResources().getDrawable(imageResource);
            holder.thumbnail.setImageDrawable(res);
            holder.FileName.setText(bucketNames.get(position));
            holder.FileName.setVisibility(View.VISIBLE);
        }
        else{
            holder.FileName.setVisibility(View.GONE);
            Glide.with(context).load("file://"+bitmapList.get(position)).
                    apply(new RequestOptions().override(153,160).centerCrop().
                            dontAnimate().skipMemoryCache(true)).transition(withCrossFade()).into(holder.thumbnail);
        }
        if(selected.get(position).equals(true)){
            holder.imgChecked.setVisibility(View.VISIBLE);
            holder.imgChecked.setAlpha(150);
        }else{
            holder.imgChecked.setVisibility(View.GONE);
        }

    }

    @Override
   public int getItemCount() {
        return bitmapList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail,imgChecked;
        public TextView FileName;

        public MyViewHolder(View view) {
            super(view);
            thumbnail= view.findViewById(R.id.layout_media_item_image);
            FileName= view.findViewById(R.id.layout_album_item_title);
            imgChecked= view.findViewById(R.id.layout_media_item_checked);
        }
    }
}

