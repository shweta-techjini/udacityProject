package com.techjini.udacityapp.network;

import android.net.Uri;
import android.os.AsyncTask;

import com.techjini.udacityapp.dataobjects.Movie;
import com.techjini.udacityapp.interfaces.FetchMovieListener;
import com.techjini.udacityapp.parser.MovieJsonParser;
import com.techjini.udacityapp.utility.AppConstants;
import com.techjini.udacityapp.utility.AppLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shweta on 2/27/16.
 */
public class FetchMovieTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private FetchMovieListener movieListener;
    private int responseCode;

    public FetchMovieTask(FetchMovieListener listener) {
        this.movieListener = listener;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {

        Uri requestUri = Uri.parse(AppConstants.MOVIE_BASE_URL).buildUpon().appendQueryParameter(AppConstants.API_KEY_PARAM, AppConstants.THE_MOVIE_APP_KEY).build();

        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        try {
            URL requestUrl = new URL(requestUri.toString());

            AppLogger.d(this, "request url for movie api :: " + requestUrl);

            try {
                httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                responseCode = httpURLConnection.getResponseCode();
                if (inputStream == null) {
                    responseCode = FetchMovieListener.ERROR_RESPONSE_CODE;
                    return null;
                }
                StringBuffer buffer = new StringBuffer();
                String movieJSONStr;
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    responseCode = FetchMovieListener.ERROR_RESPONSE_CODE;
                    return null;
                }

                movieJSONStr = buffer.toString();
                ArrayList<Movie> movies = MovieJsonParser.getMoviesFromJson(movieJSONStr);
                inputStream.close();

                return movies;
            } catch (IOException e) {
                e.printStackTrace();
                AppLogger.e(this, "Error : " + e.toString());
                responseCode = FetchMovieListener.ERROR_RESPONSE_CODE;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            AppLogger.e(this, "Error : " + e.toString());
            responseCode = FetchMovieListener.ERROR_RESPONSE_CODE;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        if (movieListener != null)
            movieListener.showProgressDialog();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        if (movieListener != null) {
            if (responseCode == FetchMovieListener.ERROR_RESPONSE_CODE || movies == null) {
                movieListener.onFailure(FetchMovieListener.ERROR_RESPONSE_CODE);
            } else {
                movieListener.onSuccess(movies, responseCode);
            }
        }
    }
}
