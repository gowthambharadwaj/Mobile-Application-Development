package com.example.inclass12;

import java.util.ArrayList;

public class Location {

    ArrayList<Coordinates> points;
    String title;

    public Location(ArrayList<Coordinates> points, String title) {
        this.points = points;
        this.title = title;
    }

    public ArrayList<Coordinates> getPoints() {
        return points;
    }
}
