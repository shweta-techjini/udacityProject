package com.techjini.udacityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.techjini.udacityapp.R;
import com.techjini.udacityapp.dataobjects.Movie;

import java.util.ArrayList;

/**
 * Created by Shweta on 2/29/16.
 */
public class MovieAdapter extends BaseAdapter {

    private ArrayList<Movie> movieArrayList;
    private Context mContext;

    public MovieAdapter(Context context) {
        this.mContext = context;
    }

    public void setMovieArrayList(ArrayList<Movie> movies) {
        if (this.movieArrayList != null) {
            this.movieArrayList.clear();
        }
        this.movieArrayList = movies;
    }

    @Override
    public int getCount() {
        if (movieArrayList == null) {
            return 0;
        }
        return movieArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if (movieArrayList == null) {
            return null;
        }
        return movieArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (movieArrayList == null) {
            return 0;
        }
        return movieArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView itemView;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
            itemView = (ImageView) convertView;
        } else {
            itemView = (ImageView) convertView;
        }

        String imageUrl = movieArrayList.get(position).getPoster_path();
        Picasso.with(mContext).load(imageUrl).into(itemView);
        return itemView;
    }
}
