package com.example.hw05;

import java.io.Serializable;

public class NewsData implements Serializable {
    String title;
    String urlToImage;
    String author;
    String publishedAt;
    String urlWebView;
    String name;


    public NewsData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlWebView() {
        return urlWebView;
    }

    public void setUrlWebView(String urlWebView) {
        this.urlWebView = urlWebView;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
