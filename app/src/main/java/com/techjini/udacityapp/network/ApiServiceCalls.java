package com.techjini.udacityapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shweta on 5/13/16.
 */
public class ApiServiceCalls {

    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/";

    public static Retrofit retrofitBuilder() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MOVIE_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
