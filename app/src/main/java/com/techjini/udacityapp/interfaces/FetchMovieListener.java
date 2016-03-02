package com.techjini.udacityapp.interfaces;

import com.techjini.udacityapp.dataobjects.Movie;

import java.util.ArrayList;

/**
 * Created by Shweta on 2/28/16.
 */
public interface FetchMovieListener {

    // Exception error
    int ERROR_RESPONSE_CODE = 10;

    void onSuccess(ArrayList<Movie> movies, int statusCode);

    void onFailure(int statusCode);

    void showProgressDialog();
}
