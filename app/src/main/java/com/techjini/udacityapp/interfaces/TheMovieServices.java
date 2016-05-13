package com.techjini.udacityapp.interfaces;

import com.techjini.udacityapp.dataobjects.MovieReviewList;
import com.techjini.udacityapp.dataobjects.MoviesResultList;
import com.techjini.udacityapp.dataobjects.MoviesTrailersList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shweta on 5/9/16.
 */
public interface TheMovieServices {

    @GET("discover/movie")
    Call<MoviesResultList> loadMovies(@Query("sort_by") String sort, @Query("api_key") String apikey);

    @GET("movie/{id}/videos")
    Call<MoviesTrailersList> getMovieTrailers(@Path("id") String id, @Query("api_key") String apikey);

    @GET("movie/{id}/reviews")
    Call<MovieReviewList> getMovieReviews(@Path("id") String id, @Query("api_key") String apikey);

}
