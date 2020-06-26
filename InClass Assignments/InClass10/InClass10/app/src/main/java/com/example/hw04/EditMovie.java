package com.example.hw04;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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
    ArrayList<String> genres = new ArrayList<>();
    Movies movies = new Movies();

    ArrayList<Movies> edit=new ArrayList<Movies>();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = database.collection("movies");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        setTitle("Edit Movie");

        edit = (ArrayList<Movies>) getIntent().getExtras().getSerializable("edit");
        final int index=getIntent().getExtras().getInt("index");
        final String docID = getIntent().getExtras().getString("key");
        genres = getIntent().getExtras().getStringArrayList("genre");

        Log.d("demo","data: "+edit+"   "+index+"    "+docID);

        Log.d("demo","data_new: "+edit.get(index).getName()+"    "+edit.get(0).getGenre());

        editUpdateName = findViewById(R.id.editUpdateName);
        editUpdateDescription = findViewById(R.id.editUpdateDescription);
        editSpinner = findViewById(R.id.editSpinner);
        editSeekbar = findViewById(R.id.editSeekbar);
        editDisplayrating = findViewById(R.id.editDisplayrating);
        editYear = findViewById(R.id.editYear);
        editIMDB = findViewById(R.id.editIMDB);
        saveChanges = findViewById(R.id.saveChanges);

        editUpdateName.setText(edit.get(index).getName());
        Log.d("demo","name in edit: "+edit.get(index).getName());
        editUpdateDescription.setText(edit.get(index).getDesctiption());

        int spinnerPosition = 0;
        //Toast.makeText(EditMovie.this, "Genre:  "+genres.get(index), Toast.LENGTH_SHORT).show();
        //Log.d("demo","spinner pos: "+spinnerPosition);
        String[] str = {"Action", "Animation", "Comedy", "Documentary", "Family", "Horror", "Crime", "Others"};

        for(int i=0;i<str.length;i++){
            if(genres.get(index).equals(str[i])){
                spinnerPosition = i;
            }

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, str);
        editSpinner.setAdapter(adapter);
        editSpinner.setSelection(spinnerPosition);

        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                edit.get(index).setGenre(item);
                //movies.setGenre(item);
                Log.d("demo","item:  "+item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        editSeekbar.setProgress(Integer.parseInt(edit.get(index).getRating()));
        editDisplayrating.setText(String.valueOf(edit.get(index).getRating()));

        editYear.setText(String.valueOf(edit.get(index).getYear() == "0" ? "" : edit.get(index).getYear()));
        editIMDB.setText(edit.get(index).getImdb());

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
                //movies.setRating(updatedValue);
            }
        });

        if(updatedValue == 0){
            updatedValue = Integer.parseInt(edit.get(index).getRating());
        }


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieName = editUpdateName.getText().toString().trim();
                String movieDescription = editUpdateDescription.getText().toString().trim();
                String movieYear = editYear.getText().toString().trim();
                String imdb = editIMDB.getText().toString();



                Log.d("demo","updated value: "+updatedValue);

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
                    collectionReference.document(docID).update("name",editUpdateName.getText().toString(),
                            "description",editUpdateDescription.getText().toString(),
                            "genre",edit.get(index).getGenre(),"year",String.valueOf(editYear.getText().toString()),
                            "imdb",editIMDB.getText().toString(),"rating",String.valueOf(updatedValue)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditMovie.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }
            }
        });
    }
}

