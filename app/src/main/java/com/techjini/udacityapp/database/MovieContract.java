package com.techjini.udacityapp.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Shweta on 3/17/16.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.techjini.udacityapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    // Inner class that defines the columns of movie table
    public static final class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_ADULT = "adult";
        // Primary key of the table
        public static final String COLUMN_ID = "id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // Cursor return more than a row
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        // cursor return row
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static Uri buildMovieUriForId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

