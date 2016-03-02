package com.techjini.udacityapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.techjini.udacityapp.R;
import com.techjini.udacityapp.activity.DetailMovieActivity;
import com.techjini.udacityapp.adapter.MovieAdapter;
import com.techjini.udacityapp.dataobjects.Movie;
import com.techjini.udacityapp.interfaces.FetchMovieListener;
import com.techjini.udacityapp.network.FetchMovieTask;
import com.techjini.udacityapp.utility.AppConstants;
import com.techjini.udacityapp.utility.AppLogger;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 *
 * Created by Shweta on 2/28/16.
 */
public class PopularMoviesFragment extends Fragment implements FetchMovieListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private GridView gridView;
    private MovieAdapter movieAdapter;
    private Button mError;
    private ProgressBar mProgress;

    public PopularMoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        gridView = (GridView) view.findViewById(R.id.grid_view_movies);
        mError = (Button) view.findViewById(R.id.refresh_error_message);
        mProgress = (ProgressBar) view.findViewById(R.id.progress_bar);

        FetchMovieTask fetchMovieTask = new FetchMovieTask(this);
        fetchMovieTask.execute();

        mError.setOnClickListener(this);
        movieAdapter = new MovieAdapter(this.getActivity());
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_popular_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this.getContext(), "Menu option clicked", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(ArrayList<Movie> movies, int statusCode) {
        AppLogger.d(this, "response code onSuccess:: " + statusCode);
//        AppLogger.d(this, "size of the movies are :: " + movies.size());
        movieAdapter.setMovieArrayList(movies);
        movieAdapter.notifyDataSetChanged();
        mProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
            }
        }, 5000);
    }

    @Override
    public void onFailure(int statusCode) {
        AppLogger.d(this, "response code on Failure :: " + statusCode);
        mError.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showProgressDialog() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = (Movie) movieAdapter.getItem(position);
        Intent detailMovieIntent = new Intent(this.getActivity(), DetailMovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.EXTRA_MOVIE, movie);
        // put movie item in bundle
        detailMovieIntent.putExtras(bundle);
        startActivity(detailMovieIntent);
    }

    @Override
    public void onClick(View v) {
        AppLogger.d(this, "resend the request to get the movies.");
        FetchMovieTask fetchMovieTask = new FetchMovieTask(this);
        fetchMovieTask.execute();
        mError.setVisibility(View.GONE);
    }
}
