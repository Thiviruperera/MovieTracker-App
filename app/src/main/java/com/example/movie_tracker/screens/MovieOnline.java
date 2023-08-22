package com.example.movie_tracker.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie_tracker.R;
import com.example.movie_tracker.adapters.MovieListImdbAdapter;
import com.example.movie_tracker.database.MovieEntity;

import com.example.movie_tracker.json.HttpHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieOnline extends AppCompatActivity {
    private static final String API_KEY = "k_u1y688e2/";
    private HttpHandler handler = new HttpHandler();
    private ArrayList<MovieEntity> movieEntities = new ArrayList<>();
    private ListView list;
    private MovieListImdbAdapter movieListAdapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_online);
        list= findViewById(R.id.list);
        textView = findViewById(R.id.textView);

        GetRelatedMovies task1 = new GetRelatedMovies();
        task1.execute(getIntent().getStringExtra("selectedMovieTitle"));
    }

    public class GetMovieRating extends AsyncTask<ArrayList<MovieEntity> , Integer, ArrayList<MovieEntity>> {


        @Override
        protected ArrayList<MovieEntity> doInBackground(ArrayList<MovieEntity> ... strings) {
            String jsonStr ;

            for (int i=0; i< strings[0].size(); i++){
                jsonStr = handler.makeServiceCall("https://imdb-api.com/en/API/UserRatings/" + API_KEY + strings[0].get(i).getImdbId());
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String rating = jsonObj.getString("totalRating");
                    strings[0].get(i).setImdbRating(Double.parseDouble(rating));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error", "error: " + e.getMessage());
                }
            }


            return movieEntities;
        }


        @Override
        protected void onPostExecute(ArrayList<MovieEntity> movieEntities) {
            String[] maintitle = new String[movieEntities.size()];
            double[] subtitle = new double[movieEntities.size()];
            for (int i = 0; i< movieEntities.size(); i++){
                maintitle[i] = movieEntities.get(i).getImdbTitle();
                subtitle[i] = movieEntities.get(i).getImdbRating();
            }

            list.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            movieListAdapter = new MovieListImdbAdapter(MovieOnline.this, maintitle, subtitle);
            movieListAdapter.setClickListener(new MovieListImdbAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    Intent intent = new Intent(MovieOnline.this, ImageActivity.class);
                    intent.putExtra("url",movieEntities.get(i).getImdbUrl());
                    startActivity(intent);
                }
            });
            list.setAdapter(movieListAdapter);

            Toast.makeText(getApplicationContext(),"Data loaded",Toast.LENGTH_SHORT).show();
        }
    }

    class GetRelatedMovies extends AsyncTask<String, Void, ArrayList<MovieEntity>> {

        @Override
        protected ArrayList<MovieEntity> doInBackground(String... arg0) {
            String jsonStr = handler.makeServiceCall("https://imdb-api.com/en/API/SearchTitle/" + API_KEY + arg0[0]);

            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray results = jsonObj.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject c = results.getJSONObject(i);
                    MovieEntity movieEntity = new MovieEntity(c.getString("image"), c.getString("id"), 0, c.getString("title"));
                    movieEntities.add(movieEntity);
                }
            }  catch (Exception e) {
                Log.e("error", "error: " + e.getMessage());
            }


            return movieEntities;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieEntity> movieEntities) {
               new MovieOnline.GetMovieRating().execute(movieEntities);
        }
    }
}