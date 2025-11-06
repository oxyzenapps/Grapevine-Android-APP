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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

import datanapps.mediapicker.R;
//import com.oxyzenhomes.grapevine.R;


/*
 *  Yogendra
 *
 * https://github.com/datanapps
 *
 * 10-01-2019 */

public class CategorizedMediaAdapter extends RecyclerView.Adapter<CategorizedMediaAdapter.MyViewHolder>{
    private List<String> bucketNames;
    private List<String> bitmapList;
    private List<Integer> FileCount;
    private String FragmentType;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBucketName;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            tvBucketName =  view.findViewById(R.id.layout_album_item_title);
            thumbnail= view.findViewById(R.id.layout_album_item_image);
        }
    }

    public CategorizedMediaAdapter(List<String> bucketNames, List<String> bitmapList
            ,List<Integer> FileCount,String FragmentType, Context context) {
        this.bucketNames=bucketNames;
        this.bitmapList = bitmapList;
        this.FileCount=FileCount;
        this.context=context;
        this.FragmentType=FragmentType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_album_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        bucketNames.get(position);
        holder.tvBucketName.setText(bucketNames.get(position)+"("+FileCount.get(position)+")");
        if(FragmentType.equals("Audio")){
            String uri = "@drawable/audio";

            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable res = context.getResources().getDrawable(imageResource);
            holder.thumbnail.setImageDrawable(res);
        }
        else{
            Glide.with(context).load("file://"+bitmapList.get(position))
                    .apply(new RequestOptions().override(300,300).centerCrop()).into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return bucketNames.size();
    }
}

