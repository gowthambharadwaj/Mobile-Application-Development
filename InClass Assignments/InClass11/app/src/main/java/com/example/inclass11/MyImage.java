package com.example.inclass11;

public class MyImage {
    String imageURL;
    String imagePath;

    //Constructor
    public MyImage(){

    }

    public MyImage(String imageUrl, String imagePath) {
        this.imageURL = imageUrl;
        this.imagePath = imagePath;
    }
// Getters and setters
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "MyImage{" +
                "imageURL='" + imageURL + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
