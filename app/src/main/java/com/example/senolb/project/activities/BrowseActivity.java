package com.example.senolb.project.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.senolb.project.GifsAdapter;
import com.example.senolb.project.GlobalData;
import com.example.senolb.project.GestureHelper.OnStartDragListener;
import com.example.senolb.project.R;
import com.example.senolb.project.GestureHelper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

public class BrowseActivity extends Activity implements OnStartDragListener {
    private ArrayList<String> gifUrls=new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        RecyclerView rvGifs = (RecyclerView) findViewById(R.id.rvGifs);

        ArrayList<String> pass = GlobalData.getList();

        for(int i= 0; i < pass.size();i++ ){
            gifUrls.add(pass.get(i));
        }

        System.out.println(this.gifUrls.size()+"");

        final GifsAdapter gifAdapter = new GifsAdapter(getApplicationContext(), gifUrls,this);

        rvGifs.setHasFixedSize(true);
        rvGifs.setAdapter(gifAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(gifAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvGifs);
        // Create adapter passing in the sample user data
        // Attach the adapter to the recyclerview to populate items
        // Set layout manager to position the items
        rvGifs.setLayoutManager(new LinearLayoutManager(this));
        rvGifs.requestLayout();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
    public void removeIndex(int i){
        gifUrls.remove(i);
    }
}