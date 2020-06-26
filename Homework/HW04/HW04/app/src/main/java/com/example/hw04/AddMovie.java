package com.example.hw04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.hw04.MainActivity.movieMap;
import static com.example.hw04.MainActivity.movieNameList;

public class AddMovie extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText addEditAcceptName;
    EditText addEditDescriptionContent;
    Spinner addSpinner;
    SeekBar addSeekBar;
    TextView addDisplayRating;
    EditText addYear;
    EditText addIMDB;
    Button addAddMovie;
    int addedValue;
    Movies movies = new Movies();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        setTitle("Add Movie");

        addEditAcceptName = findViewById(R.id.addEditAcceptName);
        addEditDescriptionContent = findViewById(R.id.addEditDescriptionContent);
        addSpinner = findViewById(R.id.addSpinner);
        addSeekBar = findViewById(R.id.addSeekBar);
        addDisplayRating = findViewById(R.id.addDisplayRating);
        addYear = findViewById(R.id.addYear);
        addIMDB = findViewById(R.id.addIMDB);
        addAddMovie = findViewById(R.id.addAddMovie);
        movies.setRating(0);
        movies.setGenre("Action");

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> categories = getIntent().getStringArrayListExtra("categoriss");

        // Create an adapter for the spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down to display various genre
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Connecting genre and spinner
        addSpinner.setAdapter(dataAdapter);
        addDisplayRating.setText(String.valueOf(0));

        //Listener for discrete seekbar
        addSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                addedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                addDisplayRating.setText(String.valueOf(addedValue));
                movies.setRating(addedValue);
            }
        });

        //Event handler to add a movie
        addAddMovie.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               String movieTitle = addEditAcceptName.getText().toString().trim();
                                               String movieDescription = addEditDescriptionContent.getText().toString().trim();
                                               String movieYear = addYear.getText().toString().trim();
                                               String imdb = addIMDB.getText().toString();

                                               if (movieTitle.length() == 0 || movieDescription.length() == 0 || movieYear.length() == 0 || imdb.length() == 0) {
                                                   String msg = (movieTitle.length() == 0 ? "Enter the movies title" :
                                                           (movieDescription.length() == 0 ? "Enter the movies description" :
                                                                   (movieYear.length() == 0 ? "Enter the movies release year" :
                                                                           (imdb.length() == 0 ? "Enter the IMDB link" : "Good to go")
                                                                   )
                                                           ));

                                                   Context appContext = getApplicationContext();
                                                   Toast displayToast = Toast.makeText(appContext, msg, Toast.LENGTH_LONG);
                                                   displayToast.setGravity(Gravity.TOP | Gravity.LEFT, 225, 700);
                                                   displayToast.show();
                                               } else {
                                                   movies.setName(movieTitle);
                                                   movies.setDesctiption(movieDescription);
                                                   movies.setYear(Integer.parseInt(String.valueOf(movieYear)));
                                                   movies.setImdb(imdb);

                                                   movieMap.put(movieTitle, movies);
                                                   movieNameList = movieMap.keySet();
                                                   Intent intent = new Intent(AddMovie.this, MainActivity.class);
                                                   startActivity(intent);
                                               }
                                           }
                                       }

        );
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        movies.setGenre(item);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
