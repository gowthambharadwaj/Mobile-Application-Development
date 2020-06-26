package com.example.hw01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView bmi_res1;
    private TextView bmi_res2;
    private Button calc;
    private EditText lb;
    private EditText ft;
    private EditText inch;
    private double wlb;
    private double ftlen;
    private double inchlen;
    double feet;
    double weight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("BMI CALCULATOR");

        lb = findViewById(R.id.lb);
        ft = findViewById(R.id.ft);
        inch = findViewById(R.id.inch);
        calc = findViewById(R.id.calc);
        bmi_res1 = findViewById(R.id.bmi_res1);
        bmi_res2 = findViewById(R.id.bmi_res2);

        bmi_res1.setText("");
        bmi_res2.setText("");




        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String w_lb = lb.getText().toString();
                String ft_len = ft.getText().toString();
                String inch_len = inch.getText().toString();

                clear();


                if(w_lb.equals("") || ft_len.equals("") || inch_len.equals("")){
                    setError(w_lb,ft_len,inch_len);
                    Log.d("demo", "I am here");

                }

                else if(w_lb!=null && ft_len!=null && inch_len!=null && !(w_lb).equals("") && !(ft_len).equals("") && !(inch_len).equals("")){
                    wlb = Double.parseDouble(w_lb);
                    ftlen = Double.parseDouble(ft_len);
                    inchlen = Double.parseDouble(inch_len);
                    double convlen = ftlen * 12;
                    double total_len = inchlen + convlen;
                    if(wlb == 0){
                        lb.setError("0 not accepted!");
                    }
                    else if(total_len == 0){
                        Log.d("demo","inside total_len");
                        Toast.makeText(getApplicationContext(),"Length cannot be 0!", Toast.LENGTH_SHORT).show();
                    }
                    else if(inchlen>12){
                        inch.setError("Value should be <= 12!");
                    }
                    else{
                        double bmi = (wlb * 703)/(total_len*total_len);

                        DecimalFormat value = new DecimalFormat("#.#");

                        double res = Double.parseDouble(value.format(bmi));

                        bmi_res1.setText("Your BMI: "+res);

                        if(res>=30){
                            bmi_res2.setText("You are Obese");
                        }
                        else if(res>=25 && res<=29.9){
                            bmi_res2.setText("You are Overweight");
                        }
                        else if(res>=18.5 && res<=24.9){
                            bmi_res2.setText("You are Normal weight");
                        }
                        else{
                            bmi_res2.setText("You are underweight");
                        }
                    }

                }
            }
        });
    }

    public void clear(){
        bmi_res1.setText("");
        bmi_res2.setText("");
    }

    public void setError(String w_lb,String ft_len,String inch_len){
        if(w_lb.equals("") && ft_len.equals("") && inch_len.equals("")){
            lb.setError("Need a Value!");
            ft.setError("Need a Value!");
            inch.setError("Need a Value!");
        }
        else if(w_lb.equals("")){
            lb.setError("Need a Value!");
        }
        else if(ft_len.equals("")){
            ft.setError("Need a Value!");
        }
        else{
            inch.setError("Need a Value!");
        }

    }

//    public void setErrorNew(double wlb,double ftlen){
//
//        if(wlb == 0 && ftlen == 0){
//            lb.setError("0 not accepted!");
//            ft.setError("0 not accepted!");
//        }
//        else if(wlb == 0){
//            lb.setError("0 not accepted!");
//        }
//        else{
//            ft.setError("0 not accepted!");
//        }
//    }










}
