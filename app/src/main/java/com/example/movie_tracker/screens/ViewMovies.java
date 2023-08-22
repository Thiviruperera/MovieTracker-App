package com.example.movie_tracker.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movie_tracker.R;
import com.example.movie_tracker.adapters.MovieListAdapter;
import com.example.movie_tracker.database.DatabaseHandler;
import com.example.movie_tracker.database.MovieEntity;

import java.util.ArrayList;

public class ViewMovies extends AppCompatActivity implements MovieListAdapter.ItemClickListener {


    private MovieListAdapter movieListAdapter;
    private ListView list;
    private ArrayList<MovieEntity> movieEntities = new ArrayList<>();
    private Button button7, button8;
    private int option;
    private LinearLayout searchlayout;
    private EditText editTextTextPersonName7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);
        searchlayout = findViewById(R.id.searchlayout);
        editTextTextPersonName7 = searchlayout.findViewById(R.id.editTextTextPersonName7);
        button8 = searchlayout.findViewById(R.id.button8);
        button7 = findViewById(R.id.button7);
        list= findViewById(R.id.list);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //list.setSelector(android.R.color.holo_blue_dark);
        //list.setBackgroundColor(android.R.color.holo_blue_dark);

        option = getIntent().getIntExtra("activity",0);
        try{
            if(option == 0 || option == 2 || option == 4){
                movieEntities = new DatabaseHandler(ViewMovies.this).getAll();
            }else if(option == 1){
                button7.setText("SAVE");
                movieEntities = new DatabaseHandler(ViewMovies.this).getAllFavs();
            }



            if(option == 2){
                button7.setVisibility(View.GONE);

            }



            if(option == 3 ){
                searchlayout.setVisibility(View.VISIBLE);
                button7.setVisibility(View.GONE);
            }else if(option == 4){
                button7.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Toast.makeText(ViewMovies.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        String[] maintitle = new String[movieEntities.size()];
        boolean[] subtitle = new boolean[movieEntities.size()];
        for (int i = 0; i< movieEntities.size(); i++){
            maintitle[i] = movieEntities.get(i).getTitle();
            subtitle[i] = movieEntities.get(i).isFavourite();
        }

        if(option == 0 || option == 1){
            movieListAdapter = new MovieListAdapter(this, maintitle, subtitle,true);
        }else{
            movieListAdapter = new MovieListAdapter(this, maintitle, subtitle,false);

        }
        movieListAdapter.setClickListener(this);
        list.setAdapter(movieListAdapter);


        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatabaseHandler(ViewMovies.this).updateFavs(movieEntities);
                Toast.makeText(ViewMovies.this, "favourites updated", Toast.LENGTH_SHORT).show();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{
                    movieEntities.clear();
                    list.setAdapter(null);

                    movieEntities = new DatabaseHandler(ViewMovies.this).search(editTextTextPersonName7.getText().toString().trim());
                    boolean[] subtitle = new boolean[movieEntities.size()];
                    String[] maintitle = new String[movieEntities.size()];
                    for (int i = 0; i< movieEntities.size(); i++){
                        maintitle[i] = movieEntities.get(i).getTitle();
                        subtitle[i] = movieEntities.get(i).isFavourite();
                    }
                    movieListAdapter = new MovieListAdapter(ViewMovies.this, maintitle, subtitle,false);
                    movieListAdapter.setClickListener(ViewMovies.this);
                    list.setAdapter(movieListAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ViewMovies.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onCheckBoxClick(View view, int i) {
        CheckBox subtitle = view.findViewById(R.id.subtitle);
        if (subtitle.isChecked()) {
            movieEntities.get(i).setFavourite(true);
        } else {
            movieEntities.get(i).setFavourite(false);
        }
    }

    @Override
    public void onItemClick(View view, int i) {
        if(option == 2 ){
            Intent intent = new Intent(ViewMovies.this, AddEditMovie.class);
            intent.putExtra("selectedMovie",  movieEntities.get(i));
            intent.putExtra("activity",22);
            this.finish();
            startActivity(intent);
        }else if(option == 4){
            Intent intent = new Intent(ViewMovies.this, MovieOnline.class);
            intent.putExtra("selectedMovieTitle",  movieEntities.get(i).getTitle());
            startActivity(intent);
        }else if(option == 3){
            Intent intent = new Intent(ViewMovies.this, AddEditMovie.class);
            intent.putExtra("selectedMovie",  movieEntities.get(i));
            intent.putExtra("activity",22);
            intent.putExtra("isEditable",false);
            this.finish();
            startActivity(intent);
        }
    }
}