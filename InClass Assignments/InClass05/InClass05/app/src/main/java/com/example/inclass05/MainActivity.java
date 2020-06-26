package com.example.inclass05;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView get_req;
    Button go;
    ImageView img;
    ImageButton next;
    ImageButton prev;
    String[] data;
    ProgressBar pb;
    int count;
    int original = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_req = findViewById(R.id.get_req);
        go = findViewById(R.id.go_btn);
        img = findViewById(R.id.imageView);
        next = findViewById(R.id.next_btn);
        prev = findViewById(R.id.prev_btn);
        pb = findViewById(R.id.progressBar);

        pb.setVisibility(View.INVISIBLE);

//        next.setEnabled(false);
//        prev.setEnabled(false);



        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OnclickGoAsync().execute("http://dev.theappsdr.com/apis/photos/keywords.php");

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                original++;
                if(original>=count){
                    original = 0;
                }
                new FinalImageGetAsync().execute(data[original]);
            }
        });

        Log.d("demo","original: "+original);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo","original inside prev: "+original);
                Log.d("demo","count inside prev: "+count);
                original--;
                if(original < 0){
                    original = count-1;
                }
                new FinalImageGetAsync().execute(data[original]);
            }
        });

    }

//    private boolean isConnected(){
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo ni = cm.getActiveNetworkInfo();
//    }

    public class OnclickGoAsync extends AsyncTask<String,Void,String>{



        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader br = null;
            String result = null;

            try{
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();

                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while((line = br.readLine())!=null){
                    sb.append(line);
                }

                result = sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(connection!=null){
                    connection.disconnect();
                }
                if(br!=null){
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(MainActivity.this,"Data: "+s,Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.INVISIBLE);
            final CharSequence[] items = s.split(";");
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Choose a keyword");

            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    get_req.setText(items[i]);

                    new getImageAsync().execute("http://dev.theappsdr.com/apis/photos/index.php"+"?keyword="+items[i]);

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }

    public class getImageAsync extends AsyncTask<String,Void, String>{


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            Bitmap image = null;
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String result = "";
            try{
                URL url = new URL(strings[0]);

                connection = (HttpURLConnection) url.openConnection();
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                while((line = br.readLine())!=null){
                    sb.append(line+" ");
                }

                result = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("demo","S: "+s);
            data = s.split(" ");
            count = data.length;
            new FinalImageGetAsync().execute(data[0]);


        }

    }

    public class FinalImageGetAsync extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap == null){
                Toast.makeText(MainActivity.this,"No image to display!",Toast.LENGTH_SHORT).show();
            }else{
                img.setImageBitmap(bitmap);
            }

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Log.d("demo","URL's: "+strings);
            HttpURLConnection connection = null;
            Bitmap image = null;
            try{
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    image = BitmapFactory.decodeStream(connection.getInputStream());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }
            }
            return image;
        }
    }
}
