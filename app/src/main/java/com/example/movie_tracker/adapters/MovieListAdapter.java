package com.example.movie_tracker.adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.movie_tracker.R;

public class MovieListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] maintitle;
    private boolean[] subtitle;
    private boolean checkBoxEnabled;
    private ItemClickListener itemClickListener;


    public MovieListAdapter(Activity context, String[] maintitle,boolean[] subtitle, boolean checkBoxEnabled) {
        super(context, R.layout.movielist, maintitle);
        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.checkBoxEnabled = checkBoxEnabled;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.movielist, null,true);

        TextView titleText =  rowView.findViewById(R.id.title);
        CheckBox subtitleText = rowView.findViewById(R.id.subtitle);

        subtitleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onCheckBoxClick(v, position);
            }
        });

        try {
            titleText.setText(maintitle[position]);
            if(checkBoxEnabled) {
                subtitleText.setChecked(subtitle[position]);
            }else {
                subtitleText.setVisibility(View.GONE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null) {
                    itemClickListener.onItemClick(v, position);
                }
            }
        });
        return rowView;
    };

    public interface ItemClickListener {
        void onCheckBoxClick(View view, int i);
        void onItemClick(View view, int i);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
