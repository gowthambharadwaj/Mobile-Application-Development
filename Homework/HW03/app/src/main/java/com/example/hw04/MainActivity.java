package com.example.hw04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 Submitted by Gowtham Bharadwaj and Rajath Anand
 Group 18
 */
public class MainActivity extends AppCompatActivity {

    int count;
    TextView displayComplexity;
    SeekBar seek;
    TextView minDisplay;
    TextView maxDisplay;
    TextView avgDisplay;
    ProgressBar bar;
    Button calculate;
    Executor threadPool;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayComplexity = findViewById(R.id.displayComplexity);
        seek = findViewById(R.id.seekBar);
        minDisplay = findViewById(R.id.minDisplay);
        maxDisplay = findViewById(R.id.maxDisplay);
        avgDisplay = findViewById(R.id.avgDisplay);
        bar = findViewById(R.id.progressBar);
        calculate = findViewById(R.id.calculate);

        displayComplexity.setText("");
        seek.setMax(10);
        minDisplay.setText("");
        maxDisplay.setText("");
        avgDisplay.setText("");
        bar.setVisibility(View.INVISIBLE);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                count = i;
                displayComplexity.setText(String.valueOf(i) + " " + "Times");

                if (i == 0) {
                    Toast.makeText(MainActivity.this, "0 is not allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
                displayComplexity.setText(String.valueOf(i) + " " + "Times");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    Toast.makeText(MainActivity.this, "0 is not allowed", Toast.LENGTH_SHORT).show();
                    return;
                }
                bar.setVisibility(View.VISIBLE);
                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        try {
                            bar.setVisibility(View.VISIBLE);
                            TimeUnit.SECONDS.sleep(1);
                        } catch (Exception e) {

                        }
                        bar.setVisibility(View.INVISIBLE);
                        minDisplay.setText(message.getData().get("min").toString());
                        maxDisplay.setText(message.getData().get("max").toString());
                        avgDisplay.setText(message.getData().get("avg").toString());
                        return true;
                    }
                });
                threadPool = Executors.newFixedThreadPool(2);
                threadPool.execute(new compute());
            }
        });
    }

    private class compute implements Runnable {
        @Override
        public void run() {
            ArrayList list = HeavyWork.getArrayNumbers(count);
            Double max = Double.parseDouble(Collections.max(list).toString());
            Double min = Double.parseDouble(Collections.min(list).toString());
            double sum = 0;
            for (int i = 0; i < list.size(); i++) {
                sum = sum + Double.parseDouble(list.get(i).toString());
            }
            Double average = (sum / count);
            Bundle bundle = new Bundle();
            bundle.putDouble("max", max);
            bundle.putDouble("min", min);
            bundle.putDouble("avg", average);

            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }
}