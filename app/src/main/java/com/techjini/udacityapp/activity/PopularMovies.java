package com.techjini.udacityapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.techjini.udacityapp.R;
import com.techjini.udacityapp.fragments.PopularMoviesFragment;

/**
 * Created by Shweta on 2/29/16.
 */
public class PopularMovies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: need to check for savedInstanceState
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new PopularMoviesFragment())
                .commit();
    }

}
