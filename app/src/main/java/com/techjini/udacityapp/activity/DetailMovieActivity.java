package com.techjini.udacityapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.techjini.udacityapp.R;
import com.techjini.udacityapp.fragments.DetailMovieFragment;

/**
 * Created by Shweta on 2/29/16.
 */
public class DetailMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DetailMovieFragment detailMovieFragment = new DetailMovieFragment();
        detailMovieFragment.setArguments(getIntent().getExtras());

        // TODO: need to check for savedInstanceState
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, detailMovieFragment)
                .commit();
    }

}
