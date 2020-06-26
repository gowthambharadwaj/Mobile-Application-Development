package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView complex;
    Button click;
    int getVal;
    TextView min;
    TextView max;
    TextView avg;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        complex = findViewById(R.id.textView2);
        seekBar = findViewById(R.id.seekBar);
        click = findViewById(R.id.click);

        min = findViewById(R.id.mini);
        max = findViewById(R.id.max);
        avg = findViewById(R.id.avg);
        pb = findViewById(R.id.progressBar);

        pb.setVisibility(View.INVISIBLE);

        complex.setText("");

        min.setText("");
        max.setText("");
        avg.setText("");

        seekBar.setMax(10);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                getVal = i;
                complex.setText(getVal+"");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getVal!=0){
                    new DoWorkAsync().execute(getVal);
                }
                else{
                    Toast.makeText(MainActivity.this,"Set seekbar",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    class DoWorkAsync extends AsyncTask<Integer,Void, ArrayList<Double>>{

        @Override
        protected ArrayList<Double> doInBackground(Integer... integers) {
            ArrayList<Double> resultArray = HeavyWork.getArrayNumbers(getVal);
            return resultArray;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {
            super.onPostExecute(doubles);
            int size = doubles.size();
            if(size == 1){
                min.setText(doubles.get(0)+"");
                max.setText(doubles.get(0)+"");
                avg.setText(doubles.get(0)+"");
            }
            else{
                min.setText(Collections.min(doubles)+"");
                max.setText(Collections.max(doubles)+"");
                double sum =0;
                for (Double d:doubles) {
                    sum+=d;
                }
                avg.setText(sum/size+"");
            }
            pb.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }
}
