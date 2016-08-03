package com.example.senolb.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by senolb on 03/08/16.
 */
public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView gifImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            gifImageView = (ImageView) itemView.findViewById(R.id.browse_gif_view);
        }
    }

    private List<String> urls;
    private Context myContext;

    // Store the context for easy access
    // Pass in the contact array into the constructor
    public GifsAdapter(Context context,List<String> gifUrls) {
        urls = gifUrls;
        myContext=context;
    }

    private Context getContext() {
        return myContext;
    }
    @Override
    public GifsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View gifView = inflater.inflate(R.layout.item_gif_view, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(gifView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GifsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on
        if (!urls.get(position).equals(null)) {
            System.out.println("------" + urls.get(position));
            Glide   .with(myContext)
                    .load(urls.get(position))
                    .placeholder(R.drawable.spin)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.gifImageView);

            System.out.println("=======" + urls.get(position));
        }
        else {
            // make sure Glide doesn't load anything into this view until told otherwise
        //    Glide.clear(viewHolder.gifImageView);
            // remove the placeholder (optional); read comments below
            viewHolder.gifImageView.setImageDrawable(null);
        }
    }
    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return urls.size();
    }

}