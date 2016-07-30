package com.example.senolb.project.easy_mode;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ListInterface {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.giphy.com/v1/gifs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("search")
    Call<JsonResponse> getDownsized(@Query("api_key") String key,
                                    @Query("fmt") String format,
                                    @Query("q") String type,
                                    @Query("limit") String limit);}