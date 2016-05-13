package com.techjini.udacityapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techjini.udacityapp.R;
import com.techjini.udacityapp.adapter.MovieReviewsAdapter;
import com.techjini.udacityapp.adapter.MovieTrailerAdapter;
import com.techjini.udacityapp.database.MovieDbHelper;
import com.techjini.udacityapp.dataobjects.Movie;
import com.techjini.udacityapp.dataobjects.MovieReview;
import com.techjini.udacityapp.dataobjects.MovieReviewList;
import com.techjini.udacityapp.dataobjects.MovieTrailer;
import com.techjini.udacityapp.dataobjects.MoviesTrailersList;
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
public class DetailMovieFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    @Bind(R.id.backdrop_image)
    ImageView backDrop;
    @Bind(R.id.movie_title)
    TextView movieTitle;
    @Bind(R.id.txt_year)
    TextView releaseDate;
    @Bind(R.id.txt_rating)
    TextView rating;
    @Bind(R.id.txt_lang)
    TextView popularity;
    @Bind(R.id.txt_vote_count)
    TextView voteCount;
    @Bind(R.id.txt_description)
    TextView description;
    @Bind(R.id.trailers)
    ListView listView;
    @Bind(R.id.mark_fav)
    ImageButton markFav;
    @Bind(R.id.txt_trailers)
    TextView trailers;
    @Bind(R.id.txt_reviews)
    TextView reviews;

    private MovieTrailerAdapter movieTrailerAdapter;
    private MovieReviewsAdapter movieReviewsAdapter;
    private ArrayList<MovieReview> reviewsList;
    private ArrayList<MovieTrailer> trailerList;
    private MovieDbHelper movieDbHelper;
    private Call<MoviesTrailersList> moviesTrailersCall;
    private Call<MovieReviewList> moviesReviewsCall;

    private Movie movie;

    public DetailMovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            movie = bundle.getParcelable(AppConstants.EXTRA_MOVIE);
        }

        if (movie == null) {
            getActivity().finish();
        }

        if (movieDbHelper == null) {
            movieDbHelper = MovieDbHelper.getInstanse(this.getActivity());
        }

        try {
            if (movieDbHelper.isMovieFav(movie.getId())) {
                movie.setIsFav(true);
                markFav.setSelected(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        movieTitle.setText(movie.getTitle());
        releaseDate.setText(getString(R.string.released_on, movie.getRelease_date()));
        rating.setText(getString(R.string.vote_avg, movie.getVote_average()));
        description.setText(movie.getOverview());
        popularity.setText(getString(R.string.popularity, movie.getPopularity()));
        voteCount.setText(getString(R.string.vote_count, movie.getVoteCount()));
        markFav.setOnClickListener(this);

        Picasso.with(this.getContext()).load(movie.getPoster_path()).into(backDrop);

        movieTrailerAdapter = new MovieTrailerAdapter(this.getActivity());
        movieReviewsAdapter = new MovieReviewsAdapter(this.getActivity());

        listView.setEmptyView(view.findViewById(android.R.id.empty));
        listView.setOnItemClickListener(this);
        trailers.setOnClickListener(this);
        reviews.setOnClickListener(this);

        if (savedInstanceState != null) {
            reviewsList = savedInstanceState.getParcelableArrayList(AppConstants.SAVE_REVIEWS_STATE);
            trailerList = savedInstanceState.getParcelableArrayList(AppConstants.SAVE_TRAILERS_STATE);
            movieTrailerAdapter.setMovieTrailersArrayList(trailerList);
            movieReviewsAdapter.setMovieReviewsArrayList(reviewsList);
        }

        selectTrailers();
        return view;
    }

    private void selectTrailers() {
        if (!trailers.isSelected()) {
            listView.setAdapter(movieTrailerAdapter);
            trailers.setSelected(true);
            reviews.setSelected(false);
            if (trailerList == null) {
                getMovieTrailer();
            }
        }
    }

    private void selectReviews() {
        if (!reviews.isSelected()) {
            listView.setAdapter(movieReviewsAdapter);
            trailers.setSelected(false);
            reviews.setSelected(true);
            if (reviewsList == null) {
                getMovieReviews();
            }
        }
    }

    private void getMovieReviews() {
        TheMovieServices movieServices = ApiServiceCalls.retrofitBuilder().create(TheMovieServices.class);
        moviesReviewsCall = movieServices.getMovieReviews(String.valueOf(movie.getId()), AppConstants.THE_MOVIE_APP_KEY);
        moviesReviewsCall.enqueue(reviewsListCallback);
    }

    private void getMovieTrailer() {
        TheMovieServices movieServices = ApiServiceCalls.retrofitBuilder().create(TheMovieServices.class);
        moviesTrailersCall = movieServices.getMovieTrailers(String.valueOf(movie.getId()), AppConstants.THE_MOVIE_APP_KEY);
        moviesTrailersCall.enqueue(trailersListCallback);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String youtubeKey = ((MovieTrailer) movieTrailerAdapter.getItem(position)).getKey();
        String url = AppConstants.YOUTUBE_BASE_URL + youtubeKey;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mark_fav:
                if (v.isSelected()) {
                    v.setSelected(false);
                    movie.setIsFav(false);
                    try {
                        movieDbHelper.deleteMovie(movie.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    v.setSelected(true);
                    movie.setIsFav(true);
                    try {
                        movieDbHelper.insertMovie(movie);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                PopularMoviesFragment.reloadFav = true;
                break;
            case R.id.txt_trailers:
                selectTrailers();
                break;
            case R.id.txt_reviews:
                selectReviews();
                break;
        }
    }

    private Callback<MoviesTrailersList> trailersListCallback = new Callback<MoviesTrailersList>() {
        @Override
        public void onResponse(Call<MoviesTrailersList> call, Response<MoviesTrailersList> response) {
            AppLogger.d(this, "response code onSuccess:: " + response.code());
            AppLogger.d(this, "size of the movies are :: " + response.body().getResults().size());
            trailerList = response.body().getResults();
            movieTrailerAdapter.setMovieTrailersArrayList(trailerList);
            movieTrailerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<MoviesTrailersList> call, Throwable t) {

        }
    };

    private Callback<MovieReviewList> reviewsListCallback = new Callback<MovieReviewList>() {
        @Override
        public void onResponse(Call<MovieReviewList> call, Response<MovieReviewList> response) {
            AppLogger.d(this, "response code onSuccess:: " + response.code());
            AppLogger.d(this, "size of the movie reviews are :: " + response.body().getResults().size());
            reviewsList = response.body().getResults();
            movieReviewsAdapter.setMovieReviewsArrayList(reviewsList);
            movieReviewsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<MovieReviewList> call, Throwable t) {

        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (trailerList != null)
            outState.putParcelableArrayList(AppConstants.SAVE_TRAILERS_STATE, trailerList);
        if (reviewsList != null) {
            outState.putParcelableArrayList(AppConstants.SAVE_REVIEWS_STATE, reviewsList);
        }
    }
}
