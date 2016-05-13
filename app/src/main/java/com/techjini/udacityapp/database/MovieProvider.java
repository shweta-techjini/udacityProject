package com.techjini.udacityapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Shweta on 4/12/16.
 */
public class MovieProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;

    static final int MOVIE = 100;
    static final int MOVIE_ID = 200;

    private static final SQLiteQueryBuilder sMovieQueryBuilder;

    static {
        sMovieQueryBuilder = new SQLiteQueryBuilder();
        sMovieQueryBuilder.setTables(MovieContract.MovieEntry.TABLE_NAME);
    }

    static UriMatcher buildUriMatcher() {
        // TODO: add uri matcher here
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(authority, MovieContract.PATH_MOVIE+"/#", MOVIE_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = MovieDbHelper.getInstanse(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "movie"
            case MOVIE:
            {
                retCursor = sMovieQueryBuilder.query(mOpenHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
                retCursor.setNotificationUri(getContext().getContentResolver(),uri);
                return retCursor;
            }
            // "movie/#"
            case MOVIE_ID: {
                retCursor = sMovieQueryBuilder.query(mOpenHelper.getReadableDatabase(), projection, MovieContract.MovieEntry.COLUMN_ID, new String[]{String.valueOf(ContentUris.parseId(uri))}, null, null, sortOrder);
                retCursor.setNotificationUri(getContext().getContentResolver(),uri);
                return retCursor;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri :: "+ uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch(sUriMatcher.match(uri))
        {
            case MOVIE :
            {
                long _id=db.insert(MovieContract.MovieEntry.TABLE_NAME,null,values );
                if (_id>0)
                {
                    returnUri=MovieContract.MovieEntry.buildMovieUriForId(_id);
                }
                else
                    throw new android.database.SQLException("Unsupported URI :" + uri);
                break;

            }
            default:
                throw new UnsupportedOperationException("Unknown URI :"+uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        int rowsDeleted;
        switch(sUriMatcher.match(uri))
        {
            case MOVIE :
                rowsDeleted=db.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI : "+uri);

        }

        if(rowsDeleted!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db=mOpenHelper.getWritableDatabase();
        int rowsUpdated;
        switch(sUriMatcher.match(uri))
        {
            case MOVIE :
                rowsUpdated=db.update(MovieContract.MovieEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI :"+uri);
        }
        if(rowsUpdated!=0)
            getContext().getContentResolver().notifyChange(uri,null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
