package com.example.senolb.project;

/**
 * Created by senolb on 27/07/16.
 */
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceMovie {
    @GET("movie")
    Call<JsonResponse2> getMovie(        //   @Query("with_genres") int genre,
                                 @Query("language") String language,
                               @Query("api_key") String key,
                                 @Query("page") String pageNum
                              //  @Query("sort_by") String sortType
                              //  @Query("vote_average.gte") float vote
                               //  @Query("vote_average.gte") float num
                           //      @Query("primary_release_year") String person
                                    );
    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/discover/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}