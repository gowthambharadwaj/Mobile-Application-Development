package com.example.homework2_new;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;


public class GridAdapter extends BaseAdapter {


    Context context;
    ArrayList<Integer> imagesArrayList;
    View myView;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, ArrayList<Integer> imagesArrayList) {
        this.context = context;
        this.imagesArrayList = imagesArrayList;
    }

    @Override
    public int getCount() {
        return imagesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            myView = new View(context);
            myView = layoutInflater.inflate(R.layout.single_item,null);
            ImageView imageView = myView.findViewById(R.id.imageView);
            imageView.setImageResource(imagesArrayList.get(i));

        }
        return myView;
    }
}
