package com.techjini.udacityapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.techjini.udacityapp.R;
import com.techjini.udacityapp.fragments.PopularMoviesFragment;
import com.techjini.udacityapp.utility.AppConstants;
import com.techjini.udacityapp.utility.AppLogger;

/**
 * Created by Shweta on 2/29/16.
 */
public class PopularMovies extends AppCompatActivity {

    public static boolean isMultiPan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            AppLogger.d(this, "savedinstance state activity is null");
            View view = findViewById(R.id.container);
            if (view == null) {
                isMultiPan = true;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_one, new PopularMoviesFragment())
                        .commit();
            } else {
                isMultiPan = false;
                // TODO: need to check for savedInstanceState
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new PopularMoviesFragment())
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstants.SAVE_ACTIVITY_STATE, true);
    }
}
