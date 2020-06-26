package com.example.hw04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListMoviesByRating extends AppCompatActivity {

    TextView drNameDisplay;
    TextView drDescriptionDisplay;
    TextView drGenreDisplay;
    TextView drRatingDisplay;
    TextView drYearDisplay;
    TextView drIMDBDisplay;
    ImageView drNext;
    ImageView drLast;
    ImageView drPrev;
    ImageView drFirst;
    Button drFinish;

    ArrayList<Movies> moviesNameList;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies_by_rating);
        setTitle("Movies By Rating");

        moviesNameList = getIntent().getExtras().getParcelableArrayList("movies");

        drNameDisplay = findViewById(R.id.drNameDisplay);
        drDescriptionDisplay = findViewById(R.id.drDescriptionDisplay);
        drGenreDisplay = findViewById(R.id.drGenreDisplay);
        drRatingDisplay = findViewById(R.id.drRatingDisplay);
        drYearDisplay = findViewById(R.id.drYearDisplay);
        drIMDBDisplay = findViewById(R.id.drIMDBDisplay);

        drPrev = findViewById(R.id.drPrev);
        drNext = findViewById(R.id.drNext);
        drFirst = findViewById(R.id.drFirst);
        drLast = findViewById(R.id.drLast);

        drFinish = findViewById(R.id.drFinish);

        i = 0;
        Movies movies = moviesNameList.get(i);

        drNameDisplay.setText(movies.getName());
        drDescriptionDisplay.setText(movies.getDesctiption());
        drGenreDisplay.setText(movies.getGenre());
        drRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
        drYearDisplay.setText(String.valueOf(movies.getYear()));
        drIMDBDisplay.setText(movies.getImdb());

        drNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != moviesNameList.size() - 1) {
                    i += 1;
                } else {
                    i = moviesNameList.size() - 1;
                }
                Movies movies = moviesNameList.get(i);
                drNameDisplay.setText(movies.getName());
                drDescriptionDisplay.setText(movies.getDesctiption());
                drGenreDisplay.setText(movies.getGenre());
                drRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                drYearDisplay.setText(String.valueOf(movies.getYear()));
                drIMDBDisplay.setText(movies.getImdb());
            }
        });

        drPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != 0) {
                    i -= 1;
                } else {
                    i = 0;
                }
                Movies movies = moviesNameList.get(i);
                drNameDisplay.setText(movies.getName());
                drDescriptionDisplay.setText(movies.getDesctiption());
                drGenreDisplay.setText(movies.getGenre());
                drRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                drYearDisplay.setText(String.valueOf(movies.getYear()));
                drIMDBDisplay.setText(movies.getImdb());
            }
        });

        drFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                Movies movies = moviesNameList.get(i);
                drNameDisplay.setText(movies.getName());
                drDescriptionDisplay.setText(movies.getDesctiption());
                drGenreDisplay.setText(movies.getGenre());
                drRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                drYearDisplay.setText(String.valueOf(movies.getYear()));
                drIMDBDisplay.setText(movies.getImdb());
            }
        });

        drLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = moviesNameList.size() - 1;
                Movies movies = moviesNameList.get(i);
                drNameDisplay.setText(movies.getName());
                drDescriptionDisplay.setText(movies.getDesctiption());
                drGenreDisplay.setText(movies.getGenre());
                drRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                drYearDisplay.setText(String.valueOf(movies.getYear()));
                drIMDBDisplay.setText(movies.getImdb());
            }
        });


        drFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListMoviesByRating.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
