package com.techjini.udacityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.techjini.udacityapp.R;
import com.techjini.udacityapp.dataobjects.MovieTrailer;

import java.util.ArrayList;

/**
 * Created by Shweta on 4/29/16.
 */
public class MovieTrailerAdapter extends BaseAdapter {

    private ArrayList<MovieTrailer> movieTrailers;
    private Context context;

    public MovieTrailerAdapter(Context context) {
        this.context = context;
    }

    public void setMovieTrailersArrayList(ArrayList<MovieTrailer> movies) {
        if (this.movieTrailers != null) {
            this.movieTrailers.clear();
        }
        this.movieTrailers = movies;
    }

    @Override
    public int getCount() {
        if (movieTrailers == null) {
            return 0;
        }
        return movieTrailers.size();
    }

    @Override
    public Object getItem(int position) {
        if (movieTrailers == null) {
            return null;
        }
        return movieTrailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (movieTrailers == null) {
            return 0;
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView trailerName;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_trailer_row, parent, false);
        }

        trailerName = (TextView) convertView.findViewById(R.id.traile_name);

        if (movieTrailers.get(position).getName() != null) {
            trailerName.setText(movieTrailers.get(position).getName());
        } else {
            trailerName.setText(context.getString(R.string.default_trailer_name, position));
        }
        return convertView;
    }
}
