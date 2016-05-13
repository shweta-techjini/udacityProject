package com.techjini.udacityapp.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shweta on 4/29/16.
 */
public class MovieReview implements Parcelable {
//    "id": "56f4f0bd9251417a440017bd"
//            "author": "Rahul Gupta"
//            "content": "Awesome moview. Best Action sequence. **Slow in the first half**"
//            "url": "https://www.themoviedb.org/review/56f4f0bd9251417a440017bd"

    private String id;
    private String author;
    private String content;

    protected MovieReview(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
    }
}
