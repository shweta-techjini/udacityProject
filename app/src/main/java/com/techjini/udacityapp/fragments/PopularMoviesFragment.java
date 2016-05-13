package com.techjini.udacityapp.fragments;

import android.content.Context;
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

import com.techjini.udacityapp.R;
import com.techjini.udacityapp.activity.DetailMovieActivity;
import com.techjini.udacityapp.activity.PopularMovies;
import com.techjini.udacityapp.adapter.MovieAdapter;
import com.techjini.udacityapp.database.MovieDbHelper;
import com.techjini.udacityapp.dataobjects.Movie;
import com.techjini.udacityapp.dataobjects.MoviesResultList;
import com.techjini.udacityapp.interfaces.TheMovieServices;
import com.techjini.udacityapp.network.ApiServiceCalls;
import com.techjini.udacityapp.utility.AppConstants;
import com.techjini.udacityapp.utility.AppLogger;

import java.sql.SQLException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 * <p/>
 * Created by Shweta on 2/28/16.
 */
public class PopularMoviesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, Callback<MoviesResultList> {

    @Bind(R.id.grid_view_movies)
    GridView gridView;
    @Bind(R.id.refresh_error_message)
    Button mError;
    @Bind(R.id.progress_bar)
    ProgressBar mProgress;
    private MovieAdapter movieAdapter;
    private MovieDbHelper movieDbHelper;
    public static boolean reloadFav = false;

    private PopularMovies parentActivity;
    private Call<MoviesResultList> moviesResultListCall;
    private String selectedParam;

    public PopularMoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parentActivity = (PopularMovies) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        ButterKnife.bind(this, view);

        mError.setOnClickListener(this);
        movieAdapter = new MovieAdapter(this.getActivity());
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            selectedParam = AppConstants.SORT_POPULAR_PARAM;
            AppLogger.d(this, "oncreateview getmovies");
            getMovies(AppConstants.SORT_POPULAR_PARAM);
        } else {
            ArrayList<Movie> movieArrayList = savedInstanceState.getParcelableArrayList(AppConstants.EXTRA_SAVE_MOVIE);
            movieAdapter.setMovieArrayList(movieArrayList);
            movieAdapter.notifyDataSetChanged();
            selectedParam = savedInstanceState.getString(AppConstants.SAVE_SELECTION);
            AppLogger.d(this, "savedinstance with selected param is :: " + selectedParam);
        }
        return view;
    }

    private void getMovies(String params) {
        TheMovieServices movieServices = ApiServiceCalls.retrofitBuilder().create(TheMovieServices.class);
        moviesResultListCall = movieServices.loadMovies(params, AppConstants.THE_MOVIE_APP_KEY);
        moviesResultListCall.enqueue(this);
        if (movieAdapter != null) {
            movieAdapter.setMovieArrayList(null);
            movieAdapter.notifyDataSetChanged();
        }
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((selectedParam.equalsIgnoreCase(AppConstants.SORT_FAV_PARAM)) && reloadFav) {
            loadFavMovies();
            reloadFav = false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_popular_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = parentActivity.getSupportFragmentManager().findFragmentByTag("DetailMovieFragment");
        if (fragment != null)
            parentActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        if (moviesResultListCall != null) {
            moviesResultListCall.cancel();
        }
        switch (item.getItemId()) {
            case R.id.action_popular:
                selectedParam = AppConstants.SORT_POPULAR_PARAM;
                AppLogger.d(this, "optionitemselect popular getmovies");
                getMovies(AppConstants.SORT_POPULAR_PARAM);
                break;
            case R.id.action_rating:
                selectedParam = AppConstants.SORT_RATING_PARAM;
                AppLogger.d(this, "optionitemselect rating getmovies");
                getMovies(AppConstants.SORT_RATING_PARAM);
                break;
            case R.id.action_fav:
                selectedParam = AppConstants.SORT_FAV_PARAM;
                AppLogger.d(this, "optionitemselect fav getmovies");
                loadFavMovies();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFavMovies() {
        if (movieDbHelper == null) {
            movieDbHelper = MovieDbHelper.getInstanse(this.getActivity());
        }
        try {
            ArrayList<Movie> favMovies = movieDbHelper.getFavMovies();
            movieAdapter.setMovieArrayList(favMovies);
            movieAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = (Movie) movieAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.EXTRA_MOVIE, movie);
        if (PopularMovies.isMultiPan) {
            DetailMovieFragment detailMovieFragment = new DetailMovieFragment();
            detailMovieFragment.setArguments(bundle);
            parentActivity.getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_two, detailMovieFragment, "DetailMovieFragment")
                    .commit();
        } else {
            Intent detailMovieIntent = new Intent(this.getActivity(), DetailMovieActivity.class);
            // put movie item in bundle
            detailMovieIntent.putExtras(bundle);
            startActivity(detailMovieIntent);
        }
    }

    @Override
    public void onClick(View v) {
        AppLogger.d(this, "resend the request to get the movies.");
        getMovies(selectedParam);
        mError.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(Call<MoviesResultList> call, Response<MoviesResultList> response) {
        AppLogger.d(this, "response code onSuccess:: " + response.code());
        final ArrayList<Movie> movies = response.body().getResults();
        movieAdapter.setMovieArrayList(movies);
        movieAdapter.notifyDataSetChanged();
        mProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
                if (PopularMovies.isMultiPan) {
                    if (movies.size() > 0)
                        gridView.performItemClick(gridView.getChildAt(0), 0, gridView.getItemIdAtPosition(0));
                }
            }
        }, 5000);
    }

    @Override
    public void onFailure(Call<MoviesResultList> call, Throwable t) {
        mError.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        AppLogger.d(this, "savedintanse called.");
        ArrayList<Movie> movies = movieAdapter.getMovieArrayList();
        if (movies != null)
            outState.putParcelableArrayList(AppConstants.EXTRA_SAVE_MOVIE, movies);
        outState.putString(AppConstants.SAVE_SELECTION, selectedParam);
    }
}
