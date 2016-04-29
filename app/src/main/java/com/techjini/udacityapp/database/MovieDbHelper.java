package com.techjini.udacityapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.techjini.udacityapp.dataobjects.Movie;
import com.techjini.udacityapp.utility.AppLogger;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Shweta on 3/17/16.
 */
public class MovieDbHelper extends OrmLiteSqliteOpenHelper {

    private static String DATABASE_NAME = "moviedb";
    private static final int DATABASE_VERSION = 1;

    private static MovieDbHelper movieDbHelper = null;

    private Dao<Movie, Integer> movieDao;

    public static MovieDbHelper getInstanse(Context context) {
        if (movieDbHelper == null) {
            movieDbHelper = new MovieDbHelper(context);
            movieDbHelper.getWritableDatabase();
        }
        return movieDbHelper;
    }

    private MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Movie.class);
        } catch (SQLException e) {
            AppLogger.e(this, "exception in onCreate.");
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    private Dao<Movie, Integer> getMovieDao() throws SQLException {
        if (movieDao == null) {
            movieDao = getDao(Movie.class);
        }
        return movieDao;
    }

    public boolean insertMovie(Movie movie) throws SQLException {
        int count = getMovieDao().create(movie);
        return count > 0 ? true : false;
    }

    public boolean updateMovie(Movie movie) throws SQLException {
        int count = getMovieDao().update(movie);
        return count > 0 ? true : false;
    }

    public ArrayList<Movie> getAllMovies() throws SQLException {
        return (ArrayList<Movie>) getMovieDao().queryForAll();
    }
}
