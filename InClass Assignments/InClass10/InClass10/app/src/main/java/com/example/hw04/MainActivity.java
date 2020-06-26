package com.example.hw04;

/*
Submitted by Gowtham Bharadwaj
Group 18
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button addMovie;
    Button edit;
    Button delete;
    Button listByYear;
    Button listByrating;

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference movieRef = database.collection("movies");
    List<String> moviesName = new ArrayList<>();
    List<String> moviesID = new ArrayList<>();

    public static Map<String, Movies> movieMap = new HashMap<>();
    public static Map<String, Integer> categoryMap;
    public static Set<String> movieNameList = new HashSet<>();
    public static ArrayList<String> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        setTitle("My Favourite Movies");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addMovie = findViewById(R.id.addMovie);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        listByYear = findViewById(R.id.listByYear);
        listByrating = findViewById(R.id.listByrating);

        categoryMap = new HashMap<>();
        categories = new ArrayList<>();

        categories.add("Action");
        categories.add("Animation");
        categories.add("Comedy");
        categories.add("Documentary");
        categories.add("Family");
        categories.add("Horror");
        categories.add("Crime");
        categories.add("Others");

        categoryMap.put("Action", 1);
        categoryMap.put("Animation", 2);
        categoryMap.put("Comedy", 3);
        categoryMap.put("Documentary", 4);
        categoryMap.put("Family", 5);
        categoryMap.put("Horror", 6);
        categoryMap.put("Crime", 7);
        categoryMap.put("Others", 8);

        // Action for "Add Movie" button
        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainActivity.this, AddMovie.class);
                intentAdd.putStringArrayListExtra("categoriss", categories);
                startActivity(intentAdd);
            }
        });

        // Action for "Edit" button
        Log.d("demo", "Size: " + movieNameList.size());
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final List<String> movieName = new ArrayList<>();
                final List<String> movieID = new ArrayList<>();
                final ArrayList<Movies> movies = new ArrayList<>();
                final ArrayList<String> genres = new ArrayList<>();

                movieRef.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Movies movie = new Movies(documentSnapshot.getData());
                                    Log.d("demo", "data from database: " + movie);
                                    movieName.add(movie.getName());
                                    movieID.add(documentSnapshot.getId());
                                    movies.add(movie);
                                    genres.add(movie.getGenre());
                                    //Toast.makeText(MainActivity.this, " "+genres, Toast.LENGTH_SHORT).show();
                                    Log.d("demo", "Genre main:  " + genres);
                                    Log.d("demo", "data in movie list: " + movies);
                                    //Toast.makeText(MainActivity.this,""+movies,Toast.LENGTH_SHORT).show();
                                }

                                if (movies.size() != 0) {
                                    final String[] names = movieName.toArray(new String[movieName.size()]);
                                    builder.setItems(names, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //final String name = movieName.get(i);

                                            Log.d("demo", "pos: " + i);

                                            Intent sendIntentedit = new Intent(MainActivity.this, EditMovie.class);
                                            sendIntentedit.putExtra("edit", movies);
                                            sendIntentedit.putExtra("index", i);
                                            sendIntentedit.putExtra("key", movieID.get(i));
                                            sendIntentedit.putExtra("genre", genres);
                                            startActivity(sendIntentedit);
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } else {
                                    Toast.makeText(MainActivity.this, "No Data in DataBase", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });


        // Action for "Delete Movie" button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> movieName = new ArrayList<>();
                final List<String> movieID = new ArrayList<>();


                movieRef.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    Movies movie = new Movies(documentSnapshot.getData());
                                    movieName.add(movie.getName());
                                    movieID.add(documentSnapshot.getId());
                                }
                                //Toast.makeText(MainActivity.this, ""+movieName, Toast.LENGTH_SHORT).show();
                                if (movieName.size() != 0) {
                                    final String[] names = movieName.toArray(new String[movieName.size()]);
                                    builder.setItems(names, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            final String name = movieName.get(i);
                                            movieRef.document(movieID.get(i)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(MainActivity.this, " Movie " + name + " Document successfully deleted!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } else {
                                    Toast.makeText(MainActivity.this, "No Data in DataBase", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        // Action for "Show List By Year" button
        listByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentYear = new Intent(MainActivity.this, ListMoviesByyear.class);
                startActivity(intentYear);
            }
//                                              else {
//                                                  Context appContext = getApplicationContext();
//                                                  Toast displayToast = Toast.makeText(appContext, "There is no movie in the list to show", Toast.LENGTH_LONG);
//                                                  displayToast.setGravity(Gravity.TOP | Gravity.LEFT, 225, 700);
//                                                  displayToast.show();
//                                              }

        });

        //Action for "Show List By Rating" button
        listByrating.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Intent intentRate = new Intent("com.example.hw04.intent.action.ListMoviesByRating");
                                                //intentYear.putParcelableArrayListExtra("movies", movies);
                                                startActivity(intentRate);

                                            }
                                        }
        );

    }
}
