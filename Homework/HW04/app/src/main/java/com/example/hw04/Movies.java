package com.example.hw04;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {
    private String name;
    private String description;
    private String genre;
    private int rating;
    private int year;
    private String imdb;

    public Movies() {

    }

    // Movies constructor
    public Movies(String name, String description, String genre, int rating, int year, String imdb) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.imdb = imdb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesctiption() {
        return description;
    }

    public void setDesctiption(String desctiption) {
        this.description = desctiption;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                ", imdb='" + imdb + '\'' +
                '}';
    }

    // Creator
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // Parcel movie details
    public Movies(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.genre = in.readString();
        this.rating = in.readInt();
        this.year = in.readInt();
        this.imdb = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.genre);
        dest.writeInt(this.rating);
        dest.writeInt(this.year);
        dest.writeString(this.imdb);

    }
}