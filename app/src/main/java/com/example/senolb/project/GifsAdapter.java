package com.example.senolb.project;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.senolb.project.GestureHelper.ItemTouchHelperAdapter;
import com.example.senolb.project.GestureHelper.ItemTouchHelperViewHolder;
import com.example.senolb.project.GestureHelper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

/**
 * Created by senolb on 03/08/16.
 */
public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView gifImageView;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            gifImageView = (ImageView) itemView.findViewById(R.id.browse_gif_view);
            text = (TextView) itemView.findViewById(R.id.titlename);
        }
    }

    public List<String> urls;
    private Context myContext;
    private final OnStartDragListener mDragStartListener;


    // Store the context for easy access
    // Pass in the contact array into the constructor
    public GifsAdapter(Context context, List<String> gifUrls, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        urls = gifUrls;
        myContext = context;
    }

    private Context getContext() {
        return myContext;
    }

    @Override
    public GifsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        final View gifView = inflater.inflate(R.layout.item_gif_view, parent, false);

        // Return a new holder instance

        ViewHolder viewHolder = new ViewHolder(gifView);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gifView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                   // toggleInformationView(gifView);
                }
            });
        }
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final GifsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on
        if (!urls.get(position).equals(null)) {
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(viewHolder.gifImageView);
            System.out.println("------" + urls.get(position));

            int index = urls.get(position).indexOf("]");

            if (!urls.get(position).substring(0, index).equals(null)&&myContext!=null) {
                Glide.with(myContext)
                        .load(urls.get(position).substring(0, index))
                        .placeholder(R.drawable.spin)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageViewTarget);

                viewHolder.text.setText(urls.get(position).substring(index + 1));
            }

            System.out.println("=======" + urls.get(position));
        } else {
            // make sure Glide doesn't load anything into this view until told otherwise
            //    Glide.clear(viewHolder.gifImageView);
            // remove the placeholder (optional); read comments below
            viewHolder.gifImageView.setImageDrawable(null);
        }

        viewHolder.text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return urls.size();
    }
   /* @Override
    public void onItemDismiss(int position) {
        urls.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(urls, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(urls, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
    */

    @Override
    public void onItemDismiss(int position) {
        urls.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(urls, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final TextView textView;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.imageViewGif);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}