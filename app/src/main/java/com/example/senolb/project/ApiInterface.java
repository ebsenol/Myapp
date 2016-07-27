package com.example.senolb.project;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by senolb on 21/07/16.
 */
public interface ApiInterface {
    @GET("random")
    Call<JsonResponse> getGif(@Query("api_key") String key,
                                    @Query("fmt") String format,
                                    @Query("tag") String tag
                                    //@Query("q") String type,
                                    //@Query("limit") String limit
    );
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.giphy.com/v1/gifs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

//52ab3ed3f1f39a747fc24b817ee31e7