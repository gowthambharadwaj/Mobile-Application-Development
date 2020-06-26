package com.example.homework2_new;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


/*
* Submitted by Rajath Anand and Gowtham Bharadwaj
* Group#30
* */
public class MainActivity extends AppCompatActivity {

    Button add_topping;
    GridView gridView;
    ProgressBar progressBar;
    Button clear_pizza;
    Button checkout;
    final CharSequence[] items = {"Bacon","Cheese","Garlic","Green Pepper","Mushroom","Olives","Onions","Red Pepper"};
    ArrayList<Integer> imageArrayList = new ArrayList<>();
    ArrayList<String> toppings_selected = new ArrayList<>();
    CheckBox deli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pizza Store");

        add_topping = findViewById(R.id.add_topping);
        gridView = findViewById(R.id.gridView);
        progressBar = findViewById(R.id.progressBar);
        clear_pizza = findViewById(R.id.clear_pizza);
        checkout = findViewById(R.id.checkout);
        deli = findViewById(R.id.deli);

        final GridAdapter gridAdapter = new GridAdapter(MainActivity.this,imageArrayList);
        progressBar.setMax(10);



        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose a topping")
                .setCancelable(true)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int x = 1;

                        if(gridAdapter.getCount()<=9){
                            switch (i){
                                case 0:
                                    progressBar.incrementProgressBy(x);
                                    Log.d("demo","Control here: "+i);
                                    imageArrayList.add(R.drawable.bacon);
                                    toppings_selected.add("bacon");
                                    gridView.setAdapter(gridAdapter);
                                    break;
                                case 1:
                                    progressBar.incrementProgressBy(x);
                                    imageArrayList.add(R.drawable.cheese);
                                    toppings_selected.add("cheese");
                                    gridView.setAdapter(gridAdapter);
                                    break;
                                case 2:
                                    progressBar.incrementProgressBy(x);
                                    imageArrayList.add(R.drawable.garlic);
                                    toppings_selected.add("garlic");
                                    gridView.setAdapter(gridAdapter);
                                    break;
                                case 3:
                                    progressBar.incrementProgressBy(x);
                                    imageArrayList.add(R.drawable.green_pepper);
                                    toppings_selected.add("green pepper");
                                    gridView.setAdapter(gridAdapter);
                                    break;
                                case 4:
                                    progressBar.incrementProgressBy(x);
                                    imageArrayList.add(R.drawable.mashroom);
                                    toppings_selected.add("mushroom");
                                    gridView.setAdapter(gridAdapter);
                                    break;
                                case 5:
                                    progressBar.incrementProgressBy(x);
                                    imageArrayList.add(R.drawable.olive);
                                    toppings_selected.add("olive");
                                    gridView.setAdapter(gridAdapter);
                                    break;
                                case 6:
                                    progressBar.incrementProgressBy(x);
                                    imageArrayList.add(R.drawable.onion);
                                    toppings_selected.add("onion");
                                    gridView.setAdapter(gridAdapter);
                                    break;
                                case 7:
                                    progressBar.incrementProgressBy(x);
                                    imageArrayList.add(R.drawable.red_pepper);
                                    toppings_selected.add("red pepper");
                                    gridView.setAdapter(gridAdapter);
                                    break;
                            }

                        }else{
                            Toast.makeText(MainActivity.this,"Maximum Capacity!",Toast.LENGTH_SHORT).show();
                        }



                    }
                });


        final AlertDialog alert = builder.create();

        add_topping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.show();

            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int new_progress = progressBar.getProgress() - (progressBar.getMax()/10);
                if(new_progress < 0){
                    new_progress = 0;
                }

                switch (i){
                    case 0:
                        imageArrayList.remove(i);
                        progressBar.setProgress(new_progress);
                        toppings_selected.remove(i);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 1:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 2:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 3:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 4:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 5:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 6:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 7:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 8:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                    case 9:
                        imageArrayList.remove(i);
                        toppings_selected.remove(i);
                        progressBar.setProgress(new_progress);
                        gridView.setAdapter(gridAdapter);
                        break;
                }

            }
        });

        clear_pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageArrayList.clear();
                toppings_selected.clear();
                progressBar.setProgress(0);
                gridView.setAdapter(gridAdapter);
                if(deli.isChecked()){
                    deli.setChecked(false);
                }

            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this,OrderActivity.class);
                    intent.putExtra("value",deli.isChecked());
                    intent.putExtra("array",toppings_selected);


                    startActivity(intent);
                    finish();



            }
        });


    }


}
