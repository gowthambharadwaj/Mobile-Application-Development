package com.example.inclass07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {

    public MusicAdapter(@NonNull Context context, int resource, @NonNull List<Music> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        Music music = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.use_list,parent,false);
        }

        TextView track_name =convertView.findViewById(R.id.trackName);
        TextView artist_name = convertView.findViewById(R.id.artistname);
        TextView album_name = convertView.findViewById(R.id.albumName);
        TextView date = convertView.findViewById(R.id.ddmmyy);



        track_name.setText(music.trackname);
        artist_name.setText(music.artistname);
        album_name.setText(music.albumname);
        date.setText(music.date);


        return convertView;





    }
}
