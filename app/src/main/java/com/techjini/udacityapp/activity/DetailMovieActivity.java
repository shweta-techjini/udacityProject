package com.techjini.udacityapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.techjini.udacityapp.R;
import com.techjini.udacityapp.fragments.DetailMovieFragment;
import com.techjini.udacityapp.utility.AppConstants;

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

        if (savedInstanceState == null) {
            DetailMovieFragment detailMovieFragment = new DetailMovieFragment();
            detailMovieFragment.setArguments(getIntent().getExtras());

            // TODO: need to check for savedInstanceState
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, detailMovieFragment)
                    .commit();
        }

    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AppConstants.SAVE_ACTIVITY_STATE, true);
    }
}
