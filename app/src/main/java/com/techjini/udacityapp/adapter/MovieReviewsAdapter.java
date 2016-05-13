package com.techjini.udacityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.techjini.udacityapp.R;
import com.techjini.udacityapp.dataobjects.MovieReview;

import java.util.ArrayList;

/**
 * Created by Shweta on 5/3/16.
 */
public class MovieReviewsAdapter extends BaseAdapter {

    private ArrayList<MovieReview> movieReviews;
    private Context context;

    public MovieReviewsAdapter(Context context) {
        this.context = context;
    }

    public void setMovieReviewsArrayList(ArrayList<MovieReview> movieReviews) {
        if (this.movieReviews != null) {
            this.movieReviews.clear();
        }
        this.movieReviews = movieReviews;
    }

    @Override
    public int getCount() {
        if (movieReviews == null) {
            return 0;
        }
        return movieReviews.size();
    }

    @Override
    public Object getItem(int position) {
        if (movieReviews == null) {
            return null;
        }
        return movieReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (movieReviews == null) {
            return 0;
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReviewHolder reviewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_review_layout, parent, false);
            reviewHolder = new ReviewHolder();
            reviewHolder.authorName = (TextView) convertView.findViewById(R.id.txt_author);
            reviewHolder.content = (TextView) convertView.findViewById(R.id.txt_content);
            convertView.setTag(reviewHolder);
        } else {
            reviewHolder = (ReviewHolder) convertView.getTag();
        }

        if (movieReviews.get(position).getAuthor() != null && movieReviews.get(position).getAuthor().length() > 0) {
            reviewHolder.authorName.setText(movieReviews.get(position).getAuthor());
        } else {
            reviewHolder.authorName.setText(context.getString(R.string.unknown));
        }
        reviewHolder.content.setText(movieReviews.get(position).getContent());
        return convertView;
    }

    private static class ReviewHolder {
        TextView authorName;
        TextView content;
    }
}
