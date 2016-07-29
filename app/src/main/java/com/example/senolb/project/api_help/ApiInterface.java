package com.example.senolb.project.api_help;

import com.example.senolb.project.api_help.JsonResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by senolb on 21/07/16.
 */
public interface ApiInterface {
    @GET("gifs/random")
    Call<JsonResponse> getGif(@Query("api_key") String key,
                              @Query("fmt") String format,
                              @Query("tag") String tag
                              //@Query("q") String type,
                              //@Query("limit") String limit
    );
    @GET("stickers/random")
    Call<JsonResponse> getSticker( @Query("api_key") String key,
                                   @Query("fmt") String format,
                                   @Query ("tag") String tag,
                                   @Query("rating") String rating
    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.giphy.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

//52ab3ed3f1f39a747fc24b817ee31e7