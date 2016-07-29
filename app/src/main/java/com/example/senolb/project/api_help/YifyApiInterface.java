package com.example.senolb.project.api_help;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface YifyApiInterface {
    @GET("list_movies.json")
    Call<JsonResponse3> getYifyMovie(        @Query("limit") int lim,
                                         @Query("page") int page
                                         //  @Query("sort_by") String sortType
                                         //  @Query("vote_average.gte") float vote
                                         //  @Query("vote_average.gte") float num
                                         //      @Query("primary_release_year") String person
    );
    Retrofit retrofit3 = new Retrofit.Builder()
            .baseUrl("http://yify.is/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}