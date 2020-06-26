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

    ArrayList<Movies> moviesNameList = new ArrayList<>();
    int i = 0;

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = database.collection("movies");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movies_by_rating);
        setTitle("Movies By Rating");
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
        collectionReference.orderBy("rating", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Movies movie = new Movies(documentSnapshot.getData());
                    moviesNameList.add(movie);
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
