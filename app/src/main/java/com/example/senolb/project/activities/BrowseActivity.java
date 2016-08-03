package com.example.senolb.project.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.senolb.project.GifsAdapter;
import com.example.senolb.project.R;
import com.example.senolb.project.easymodegif.JsonResponse;
import com.example.senolb.project.easymodegif.ListInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseActivity extends Activity {

    ArrayList<String> gifUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        RecyclerView rvGifs = (RecyclerView) findViewById(R.id.rvGifs);


        ListInterface service = ListInterface.retrofit.create(ListInterface.class);
        Call<JsonResponse> myDownsized =
                service.getDownsized("dc6zaTOxFJmzC", "json", "funny ", "15"); // api key, format, tag
        myDownsized.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<com.example.senolb.project.easymodegif.JsonResponse> call,
                                   Response<JsonResponse> response) {
                if (response.isSuccessful()) {
                    //get the data
                    int count = 0;
                    for (com.example.senolb.project.easymodegif.Data data : response.body().getDataList()) {
                        System.out.println(response.body().getDataList().get(count).getImages().getDownsized().getUrl());
                        gifUrls.add(count, data.getImages().getDownsized().getUrl());
                        count++;
                    }
                } else { //unsuccessful response

                }
            }

            @Override
            public void onFailure(Call<com.example.senolb.project.easymodegif.JsonResponse> call,
                                  Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });



        // Create adapter passing in the sample user data
        GifsAdapter adapter = new GifsAdapter(this, gifUrls);
        // Attach the adapter to the recyclerview to populate items
        // Set layout manager to position the items
        rvGifs.setAdapter(adapter);
        rvGifs.setLayoutManager(new LinearLayoutManager(this));
    }
}