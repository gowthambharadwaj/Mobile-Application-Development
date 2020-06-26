package com.example.homework2_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    TextView res2;
    TextView array;
    TextView res3;
    TextView res4;
    Button finish;
    float deli_cost;
    float total;
    float toppings;
    float base_price = (float) 6.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle("Order Activity");

        res2 = findViewById(R.id.res_2);
        res3 = findViewById(R.id.res_3);
        array = findViewById(R.id.list_array);
        res4 = findViewById(R.id.res_4);
        finish = findViewById(R.id.finish);

        boolean s = getIntent().getExtras().getBoolean("value");

        ArrayList<String> received_image = getIntent().getExtras().getStringArrayList("array");

        if(s == true){
            res3.setText("4.0$");
            deli_cost = 4;
        }
        else{
            res3.setText("0.0$");
            deli_cost = 0;
        }

        Log.d("demo", String.valueOf(received_image));

        StringBuilder sb = new StringBuilder();

        for(String details: received_image){
            sb.append(details+",");
        }

        if(sb.length()>0){
            sb.setLength(sb.length()-1);
        }



        array.setText(sb);

        toppings = (float) (1.5 * received_image.size());

        total = toppings + deli_cost + base_price;

        res2.setText(String.valueOf(toppings)+"$");
        res4.setText(String.valueOf(total)+"$");

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this,MainActivity.class);
                startActivity(intent);
                //array.setText("");
                finish();
            }
        });





    }
}
