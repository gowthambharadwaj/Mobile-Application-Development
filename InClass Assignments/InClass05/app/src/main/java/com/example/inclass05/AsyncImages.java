package com.example.inclass05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncImages extends AsyncTask<String, Void, Bitmap> {
    int number;
    public int size;
    ProgressBar pg;
    MainActivity pgbar;
    ImageView image;

    public AsyncImages(int i, MainActivity activity) {
        number = i;
        this.pgbar = activity;
        image = (ImageView) activity.findViewById(R.id.imageView);

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        pgbar.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        image.setImageBitmap(bitmap);
        image.setVisibility(View.VISIBLE);
        //Log.d("num", String.valueOf(number));
        super.onPostExecute(bitmap);
    }

    @Override
    protected void onPreExecute() {
        pgbar.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        image.setVisibility(View.INVISIBLE);
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            String url1 = "http://dev.theappsdr.com/apis/photos/index.php";
            String string;
            string = (url1 + "?keyword=" + strings[0]);
            URL url = new URL(string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.connect();
            StringBuilder stringUrl = new StringBuilder();
            String nama12 = IOUtils.toString(connection.getInputStream(), "UTF8");
            String[] stringSpilt = nama12.toString().split("http");
            size = stringSpilt.length;
            Log.d("size", String.valueOf(size));
            String getimage;
            if (size == 1) {
                getimage = "http://www.clker.com/cliparts/q/L/P/Y/t/6/no-image-available-md.png";

            } else {
                getimage = "http" + stringSpilt[number + 1];
            }
            URL urlimage = new URL(getimage);
            connection = (HttpURLConnection) urlimage.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input1 = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input1);
            return myBitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
