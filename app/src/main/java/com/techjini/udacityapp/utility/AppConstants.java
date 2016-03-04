package com.techjini.udacityapp.utility;

/**
 * Created by Shweta on 2/27/16.
 */
public class AppConstants {

    public static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    public static final String THE_MOVIE_APP_KEY = "Your Key";

    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342";

    // Movie Request Params
    public static final String API_KEY_PARAM = "api_key";
    public static final String SORT_PARAM = "sort_by";
    public static final String SORT_POPULAR_PARAM = "popularity.desc";
    public static final String SORT_RATING_PARAM = "vote_average.desc";

    // Json parser string constants
    public static final String RESULTS = "results";

    // JSON movie object fields
    public static final String TITLE = "title";
    public static final String POSTER_PATH = "poster_path";
    public static final String ADULT = "adult";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String ID = "id";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String POPULARITY = "popularity";
    public static final String VOTE_COUNT = "vote_count";
    public static final String VIDEO = "video";
    public static final String VOTE_AVERAGE = "vote_average";

    // Intent Extra
    public static final String EXTRA_MOVIE = "extra.movie";
}
