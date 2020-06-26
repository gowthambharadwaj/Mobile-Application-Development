package com.example.hw04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListMoviesByyear extends AppCompatActivity {

    TextView dyNameDisplay;
    TextView dyDescriptionDisplay;
    TextView dyGenreDisplay;
    TextView dyRatingDisplay;
    TextView dyYearDisplay;
    TextView dyIMDBDisplay;
    ImageView dyNext;
    ImageView dyLast;
    ImageView dyPrev;
    ImageView dyFirst;
    Button dyFinish;

    ArrayList<Movies> moviesNameList = new ArrayList<>();
    int i = 0;

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = database.collection("movies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies_byyear);
        dyNameDisplay = findViewById(R.id.dyNameDisplay);
        dyDescriptionDisplay = findViewById(R.id.dyDescriptionDisplay);
        dyGenreDisplay = findViewById(R.id.dyGenreDisplay);
        dyRatingDisplay = findViewById(R.id.dyRatingDisplay);
        dyYearDisplay = findViewById(R.id.dyYearDisplay);
        dyIMDBDisplay = findViewById(R.id.dyIMDBDisplay);

        dyPrev = findViewById(R.id.dyPrev);
        dyNext = findViewById(R.id.dyNext);
        dyFirst = findViewById(R.id.dyFirst);
        dyLast = findViewById(R.id.dyLast);

        dyFinish = findViewById(R.id.dyFinish);

        i = 0;
        setTitle("Movies By Year");


        collectionReference.orderBy("year", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Movies movie = new Movies(documentSnapshot.getData());
                    moviesNameList.add(movie);
                    //Toast.makeText(ListMoviesByyear.this, "Fetched Movies", Toast.LENGTH_SHORT).show();
                }
                Movies movies = moviesNameList.get(i);
                dyNameDisplay.setText(movies.getName());
                dyDescriptionDisplay.setText(movies.getDesctiption());
                dyGenreDisplay.setText(movies.getGenre());
                dyRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                dyYearDisplay.setText(String.valueOf(movies.getYear()));
                dyIMDBDisplay.setText(movies.getImdb());
            }
        });

        dyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != moviesNameList.size() - 1) {
                    i += 1;
                } else {
                    i = moviesNameList.size() - 1;
                }
                Movies movies = moviesNameList.get(i);
                dyNameDisplay.setText(movies.getName());
                dyDescriptionDisplay.setText(movies.getDesctiption());
                dyGenreDisplay.setText(movies.getGenre());
                dyRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                dyYearDisplay.setText(String.valueOf(movies.getYear()));
                dyIMDBDisplay.setText(movies.getImdb());

            }
        });

        dyPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != 0) {
                    i -= 1;
                } else {
                    i = 0;
                }
                Movies movies = moviesNameList.get(i);
                dyNameDisplay.setText(movies.getName());
                dyDescriptionDisplay.setText(movies.getDesctiption());
                dyGenreDisplay.setText(movies.getGenre());
                dyRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                dyYearDisplay.setText(String.valueOf(movies.getYear()));
                dyIMDBDisplay.setText(movies.getImdb());
            }
        });

        dyFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                Movies movies = moviesNameList.get(i);
                dyNameDisplay.setText(movies.getName());
                dyDescriptionDisplay.setText(movies.getDesctiption());
                dyGenreDisplay.setText(movies.getGenre());
                dyRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                dyYearDisplay.setText(String.valueOf(movies.getYear()));
                dyIMDBDisplay.setText(movies.getImdb());
            }
        });

        dyLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = moviesNameList.size() - 1;
                Movies movies = moviesNameList.get(i);
                dyNameDisplay.setText(movies.getName());
                dyDescriptionDisplay.setText(movies.getDesctiption());
                dyGenreDisplay.setText(movies.getGenre());
                dyRatingDisplay.setText(String.valueOf(movies.getRating()) + "/5");
                dyYearDisplay.setText(String.valueOf(movies.getYear()));
                dyIMDBDisplay.setText(movies.getImdb());
            }
        });


        dyFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListMoviesByyear.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
