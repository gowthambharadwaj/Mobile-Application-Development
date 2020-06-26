package com.example.hw05;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsData> {
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<NewsData> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsData newsData = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.use_list_view,parent,false);
        }

        ImageView img = convertView.findViewById(R.id.image);
        TextView newsAuthor = convertView.findViewById(R.id.newsAuthor);
        TextView newsTitle = convertView.findViewById(R.id.newsTitle);
        TextView newsDate = convertView.findViewById(R.id.newsDate);

        Log.d("demo","data in adapter: "+newsData.title+" "+newsData.author+" "+newsData.urlToImage+" "+newsData.publishedAt);

        if(newsData.author.equals("")||newsData.publishedAt.equals("")||newsData.urlToImage.equals("")||newsData.title.equals("")){
            Toast.makeText(getContext(),"Data is null",Toast.LENGTH_SHORT).show();
            Log.d("demo","data null: "+newsData.title+","+newsData.urlToImage+","+newsData.publishedAt+","+newsData.author);
        }
        else {
            newsAuthor.setText(newsData.author);
            newsTitle.setText(newsData.title);
            newsDate.setText(newsData.publishedAt);
            Picasso.get().load(newsData.urlToImage).into(img);

        }
        return convertView;

    }
}
