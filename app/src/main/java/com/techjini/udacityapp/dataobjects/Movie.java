package com.techjini.udacityapp.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.techjini.udacityapp.utility.AppConstants;

/**
 * Created by Shweta on 2/27/16.
 */
@DatabaseTable
public class Movie implements Parcelable {

    /*"poster_path": "/inVq3FRqcYIRl2la8iZikYYxFNR.jpg"
            "adult": false
            "overview": "Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life."
            "release_date": "2016-02-09"
            "genre_ids": [3]
            0:  12
            1:  28
            2:  35
            -
            "id": 293660
            "original_title": "Deadpool"
            "original_language": "en"
            "title": "Deadpool"
            "backdrop_path": "/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg"
            "popularity": 65.848775
            "vote_count": 1364
            "video": false
            "vote_average": 7.33*/

    @DatabaseField
    private String title; // Title and Original title both are same
    @DatabaseField
    private String poster_path;
    @DatabaseField
    private String overview;
    @DatabaseField
    private String release_date;
    @DatabaseField
    private String original_language;
    @DatabaseField
    private String backdrop_path;
    @DatabaseField
    private String vote_average;
    @DatabaseField
    private double popularity;
    @DatabaseField
    private long vote_count;
    @DatabaseField
    private boolean video;
    @DatabaseField
    private boolean adult;
    @DatabaseField
    private int id;
    @DatabaseField
    private boolean isFav;

    public Movie() {

    }

    public Movie(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
        id = in.readInt();
        original_language = in.readString();
        backdrop_path = in.readString();
        popularity = in.readDouble();
        vote_count = in.readLong();
        adult = in.readByte() != 0;
        isFav = in.readByte() != 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        if (poster_path != null) {
            return AppConstants.IMAGE_BASE_URL + poster_path;
        } else {
            return null;
        }
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public long getVoteCount(){
        return vote_count;
    }
    public String getOriginal_language() {
        return original_language;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(vote_average);
        dest.writeInt(id);
        dest.writeString(original_language);
        dest.writeString(backdrop_path);
        dest.writeDouble(popularity);
        dest.writeLong(vote_count);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeByte((byte) (isFav ? 1 : 0));
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
