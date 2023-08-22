package com.example.movie_tracker.adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.movie_tracker.R;

public class MovieListImdbAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] maintitle;
    private double[] subtitle;
    private ItemClickListener itemClickListener;


    public MovieListImdbAdapter(Activity context, String[] maintitle, double[] subtitle) {
        super(context, R.layout.movielist2, maintitle);
        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.movielist2, null,true);

        TextView titleText =  rowView.findViewById(R.id.title);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);


        titleText.setText(maintitle[position]);
        subtitleText.setText(String.valueOf(subtitle[position]));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v,position);
            }
        });
        return rowView;
    };

    public interface ItemClickListener {
        void onItemClick(View view, int i);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
