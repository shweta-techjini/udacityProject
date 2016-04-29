package com.techjini.udacityapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techjini.udacityapp.R;
import com.techjini.udacityapp.dataobjects.Movie;
import com.techjini.udacityapp.utility.AppConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 * <p/>
 * Created by Shweta on 2/28/16.
 */
public class DetailMovieFragment extends Fragment {

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
    ListView trailersList;

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

        movieTitle.setText(movie.getTitle());
        releaseDate.setText(getString(R.string.released_on, movie.getRelease_date()));
        rating.setText(getString(R.string.vote_avg, movie.getVote_average()));
        description.setText(movie.getOverview());
        popularity.setText(getString(R.string.popularity, movie.getPopularity()));
        voteCount.setText(getString(R.string.vote_count, movie.getVoteCount()));

        Picasso.with(this.getContext()).load(movie.getPoster_path()).into(backDrop);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
