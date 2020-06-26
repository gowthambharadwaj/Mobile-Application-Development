package com.example.hw04;

/*
Submitted by Gowtham Bharadwaj and Rajath Anand
Group 18
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public static Map<String, Movies> movieMap = new HashMap<>();
    public static Map<String, Integer> categoryMap;
    public static Set<String> movieNameList = new HashSet<>();
    public static ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                                        if (movieNameList.size() != 0) {
                                            List<String> keyList = new ArrayList<String>(movieNameList);
                                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                                    android.R.layout.simple_list_item_1, keyList);
                                            final ListView listView = new ListView(MainActivity.this);
                                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                                            listView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                                            listView.setAdapter(adapter);


                                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                            builder.setTitle("Select a movie to edit content");
                                            builder.setView(listView);
                                            builder.create().show();

                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId) {
                                                    Intent intentEdit = new Intent(MainActivity.this, EditMovie.class);
                                                    intentEdit.putExtra("movieSelected", String.valueOf(itemPosition));
                                                    intentEdit.putStringArrayListExtra("categoriss", categories);
                                                    startActivity(intentEdit);
                                                }
                                            });
                                        } else {

                                            Context appContext = getApplicationContext();
                                            Toast displayToast = Toast.makeText(appContext, "There is no movie in the list to edit", Toast.LENGTH_LONG);
                                            displayToast.setGravity(Gravity.TOP | Gravity.LEFT, 225, 700);
                                            displayToast.show();
                                        }
                                    }
                                }
        );

        // Action for "Delete Movie" button
        delete.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          if (movieNameList.size() != 0) {
                                              List<String> keyList = new ArrayList<String>(movieNameList);
                                              final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                                      android.R.layout.simple_list_item_1, keyList);

                                              final ListView listView = new ListView(MainActivity.this);

                                              listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                      LinearLayout.LayoutParams.WRAP_CONTENT));
                                              listView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                                              listView.setAdapter(adapter);

                                              AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                              builder.setTitle("Select a movie to delete");
                                              builder.setView(listView);

                                              final AlertDialog alert = builder.create();
                                              alert.show();
                                              listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                  public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId) {
                                                      List<String> keyList = new ArrayList<String>(movieNameList);
                                                      String movieName = keyList.get(Integer.parseInt(String.valueOf(itemPosition)));
                                                      movieMap.remove(movieName);
                                                      Context appContext = getApplicationContext();
                                                      String deleteMessage = movieName + " is deleted";
                                                      Toast displayToast = Toast.makeText(appContext, deleteMessage, Toast.LENGTH_LONG);
                                                      displayToast.setGravity(Gravity.TOP | Gravity.LEFT, 225, 700);
                                                      displayToast.show();
                                                      alert.cancel();
                                                  }
                                              });

                                          } else {
                                              Context appContext = getApplicationContext();
                                              Toast displayToast = Toast.makeText(appContext, "There is no movie in the list to delete", Toast.LENGTH_LONG);
                                              displayToast.setGravity(Gravity.TOP | Gravity.LEFT, 225, 700);
                                              displayToast.show();
                                          }
                                      }
                                  }
        );

        // Action for "Show List By Year" button
        listByYear.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (movieNameList.size() != 0) {
                                                  ArrayList<Movies> movies = new ArrayList<Movies>(movieMap.values());
                                                  Collections.sort(movies,
                                                          new Comparator<Movies>() {
                                                              @Override
                                                              public int compare(Movies o1, Movies o2) {
                                                                  return o1.getYear() - o2.getYear();
                                                              }
                                                          }
                                                  );

                                                  Intent intentYear = new Intent("com.example.hw04.intent.action.ListMoviesByyear");
                                                  intentYear.putParcelableArrayListExtra("movies", movies);
                                                  startActivity(intentYear);
                                              } else {
                                                  Context appContext = getApplicationContext();
                                                  Toast displayToast = Toast.makeText(appContext, "There is no movie in the list to show", Toast.LENGTH_LONG);
                                                  displayToast.setGravity(Gravity.TOP | Gravity.LEFT, 225, 700);
                                                  displayToast.show();
                                              }
                                          }
                                      }
        );

        //Action for "Show List By Rating" button
        listByrating.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (movieNameList.size() != 0) {
                                                    ArrayList<Movies> movies = new ArrayList<Movies>(movieMap.values());
                                                    Collections.sort(movies,
                                                            new Comparator<Movies>() {
                                                                @Override
                                                                public int compare(Movies o1, Movies o2) {
                                                                    return o2.getRating() - o1.getRating();
                                                                }
                                                            }
                                                    );

                                                    Intent intentRating = new Intent("com.example.hw04.intent.action.ListMoviesByRating");
                                                    intentRating.putParcelableArrayListExtra("movies", movies);
                                                    startActivity(intentRating);
                                                } else {
                                                    Context appContext = getApplicationContext();
                                                    Toast displayToast = Toast.makeText(appContext, "There is no movie in the list to show", Toast.LENGTH_LONG);
                                                    displayToast.setGravity(Gravity.TOP | Gravity.LEFT, 225, 700);
                                                    displayToast.show();
                                                }

                                            }
                                        }
        );

    }
}
