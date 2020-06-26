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
import java.util.List;

import static com.example.hw04.MainActivity.movieMap;
import static com.example.hw04.MainActivity.movieNameList;

public class EditMovie extends AppCompatActivity {

    EditText editUpdateName;
    EditText editUpdateDescription;
    Spinner editSpinner;
    SeekBar editSeekbar;
    TextView editDisplayrating;
    EditText editYear;
    EditText editIMDB;
    Button saveChanges;
    int updatedValue;
    Movies movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        setTitle("Edit Movie");

        Bundle b = getIntent().getExtras();
        final String selectedMovie = (String) b.get("movieSelected");
        ArrayList<String> categories = getIntent().getStringArrayListExtra("categoriss");
        final List<String> keyList = new ArrayList<String>(movieNameList);

        movies = movieMap.get(
                keyList.get(Integer.parseInt(
                        selectedMovie)
                ));
        editUpdateName = findViewById(R.id.editUpdateName);
        editUpdateDescription = findViewById(R.id.editUpdateDescription);
        editSpinner = findViewById(R.id.editSpinner);
        editSeekbar = findViewById(R.id.editSeekbar);
        editDisplayrating = findViewById(R.id.editDisplayrating);
        editYear = findViewById(R.id.editYear);
        editIMDB = findViewById(R.id.editIMDB);
        saveChanges = findViewById(R.id.saveChanges);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        editSpinner.setAdapter(dataAdapter);

        editUpdateName.setText(movies.getName());
        editUpdateDescription.setText(movies.getDesctiption());

        int spinnerPosition = dataAdapter.getPosition(movies.getGenre());
        editSpinner.setSelection(spinnerPosition);

        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                movies.setGenre(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editSeekbar.setProgress(movies.getRating());
        editDisplayrating.setText(String.valueOf(movies.getRating()));

        editYear.setText(String.valueOf(movies.getYear() == 0 ? "" : movies.getYear()));
        editIMDB.setText(movies.getImdb());

        //Event Listener for Seekbar
        editSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updatedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                editDisplayrating.setText(String.valueOf(updatedValue));
                movies.setRating(updatedValue);
            }
        });

        movieMap.remove(movies.getName());

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieName = editUpdateName.getText().toString().trim();
                String movieDescription = editUpdateDescription.getText().toString().trim();
                String movieYear = editYear.getText().toString().trim();
                String imdb = editIMDB.getText().toString();

                if (movieName.length() == 0 || movieDescription.length() == 0 || movieYear.length() == 0 || imdb.length() == 0) {
                    String msg = (movieName.length() == 0 ? "Enter the movies title" :
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
                    movies.setName(movieName);
                    movies.setDesctiption(movieDescription);
                    movies.setYear(Integer.parseInt(String.valueOf(movieYear)));
                    movies.setImdb(imdb);

                    if (!selectedMovie.equalsIgnoreCase(movieName)) {
                        movieNameList = movieMap.keySet();
                    }

                    movieMap.put(movieName, movies);
                    Intent intent = new Intent(EditMovie.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

