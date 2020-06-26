package com.example.hw04;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Movies implements Serializable {
    private String name;
    private String description;
    private String genre;
    private String rating;
    private String year;
    private String imdb;
    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Movies() {

    }

    public Map convertToMap() {
        Map<String, Object> movieMap = new HashMap<>();
        movieMap.put("name", this.name);
        movieMap.put("description", this.description);
        movieMap.put("genre", this.genre);
        movieMap.put("rating", this.rating);
        movieMap.put("year", this.year);
        movieMap.put("imdb", this.imdb);
        return movieMap;
    }

    // Movies constructor
    public Movies(String name, String description, String genre, String rating, String year, String imdb) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.imdb = imdb;
    }

    public Movies(Map moviesMap) {
        this.name = (String) moviesMap.get("name");
        this.description = (String) moviesMap.get("description");
        this.genre = (String) moviesMap.get("genre");
        this.rating =  (String) moviesMap.get("rating");
        this.year =  (String) moviesMap.get("year");
        this.imdb = (String) moviesMap.get("imdb");
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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

}