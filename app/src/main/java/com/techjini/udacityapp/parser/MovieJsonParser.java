package com.techjini.udacityapp.parser;

import com.techjini.udacityapp.dataobjects.Movie;
import com.techjini.udacityapp.utility.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shweta on 2/27/16.
 */
public class MovieJsonParser {

    public static ArrayList<Movie> getMoviesFromJson(String jsonString) {
        ArrayList<Movie> listOfMovies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray result = jsonObject.getJSONArray(AppConstants.RESULTS);

            for (int i = 0; i < result.length(); i++) {
                JSONObject movieJson = result.getJSONObject(i);
                Movie newMovie = new Movie();
                newMovie.setPoster_path(movieJson.getString(AppConstants.POSTER_PATH));
                newMovie.setAdult(movieJson.getBoolean(AppConstants.ADULT));
                newMovie.setOverview(movieJson.getString(AppConstants.OVERVIEW));
                newMovie.setRelease_date(movieJson.getString(AppConstants.RELEASE_DATE));
                newMovie.setId(movieJson.getInt(AppConstants.ID));
                newMovie.setOriginal_language(movieJson.getString(AppConstants.ORIGINAL_LANGUAGE));
                newMovie.setTitle(movieJson.getString(AppConstants.TITLE));
                newMovie.setPopularity(movieJson.getDouble(AppConstants.POPULARITY));
                newMovie.setVote_count(movieJson.getLong(AppConstants.VOTE_COUNT));
                newMovie.setVideo(movieJson.getBoolean(AppConstants.VIDEO));
                newMovie.setVote_average(movieJson.getString(AppConstants.VOTE_AVERAGE));

                listOfMovies.add(newMovie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listOfMovies;
    }
}
