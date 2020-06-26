package com.example.inclass07;

import java.io.Serializable;

public class Music implements Serializable {
    String trackname;
    String albumname;
    String artistname;
    String date;
    String url;

    @Override
    public String toString() {
        return "Music{" +
                "trackname='" + trackname + '\'' +
                ", albumname='" + albumname + '\'' +
                ", artistname='" + artistname + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTrackname() {
        return trackname;
    }

    public void setTrackname(String trackname) {
        this.trackname = trackname;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Music() {
    }
}
