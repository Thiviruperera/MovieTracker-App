package com.example.movie_tracker.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.movie_tracker.R;
import com.example.movie_tracker.database.DatabaseHandler;
import com.example.movie_tracker.database.MovieEntity;

public class AddEditMovie extends AppCompatActivity {

    private MovieEntity selectedMovieEntity;
    private Button button;
    private EditText editTextTextPersonName, editTextTextPersonName2,editTextTextPersonName3,
            editTextTextPersonName4,editTextTextPersonName5,editTextTextPersonName6;
    private CheckBox checkBox;
    private RatingBar ratingBar;
    private int option,year;
    private double rate;
    private LinearLayout linearLayout;
    private boolean isEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie_sub);

        button = findViewById(R.id.button);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3);
        editTextTextPersonName4 = findViewById(R.id.editTextTextPersonName4);
        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5);
        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6);
        linearLayout = findViewById(R.id.linearLayout);
        checkBox = linearLayout.findViewById(R.id.checkBox);
        ratingBar = linearLayout.findViewById(R.id.ratingBar);


        option =  getIntent().getIntExtra("activity",11);
        isEditable =  getIntent().getBooleanExtra("isEditable",true);
        selectedMovieEntity = (MovieEntity) getIntent().getSerializableExtra("selectedMovie");

        if(option == 22){
            linearLayout.setVisibility(View.VISIBLE);
            button.setText("UPDATE");
            editTextTextPersonName4.setVisibility(View.GONE);
            editTextTextPersonName.setText(selectedMovieEntity.getTitle());
            editTextTextPersonName2.setText(selectedMovieEntity.getDirector());
            editTextTextPersonName3.setText(String.valueOf(selectedMovieEntity.getYear()));
            editTextTextPersonName4.setText(String.valueOf(selectedMovieEntity.getRating()));
            editTextTextPersonName5.setText(selectedMovieEntity.getActors());
            editTextTextPersonName6.setText(selectedMovieEntity.getReview());
            checkBox.setChecked(selectedMovieEntity.isFavourite());
            ratingBar.setRating((float) selectedMovieEntity.getRating());
        }

        if(!isEditable){
            button.setVisibility(View.GONE);
            editTextTextPersonName.setEnabled(false);
            editTextTextPersonName2.setEnabled(false);
            editTextTextPersonName3.setEnabled(false);
            editTextTextPersonName4.setEnabled(false);
            editTextTextPersonName5.setEnabled(false);
            editTextTextPersonName6.setEnabled(false);
            checkBox.setEnabled(false);
            ratingBar.setEnabled(false);
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = Integer.parseInt(editTextTextPersonName3.getText().toString().trim());
                if(option ==  11) {
                    rate =  Double.parseDouble((editTextTextPersonName4.getText().toString().trim()));
                }else if(option ==  22){
                    rate = (int) ratingBar.getRating();
                }


                if(year  <= 1895 ){
                    editTextTextPersonName3.setError("Year should be greater than 1895");
                    editTextTextPersonName3.setText("");
                }else{
                    if(rate >= 11 || rate < 1){
                        editTextTextPersonName4.setError("Rate between 1 -10");
                        editTextTextPersonName4.setText("");
                    }else {
                       isAddEdit(option);
                    }
                }
            }
        });
    }

    private void isAddEdit(int opt){
        MovieEntity movieEntity = new MovieEntity(editTextTextPersonName.getText().toString().trim(),
                editTextTextPersonName2.getText().toString().trim(),
                year,rate, editTextTextPersonName5.getText().toString().trim(),
                false,editTextTextPersonName6.getText().toString().trim());

        if(opt == 11){
            try{
                new DatabaseHandler(AddEditMovie.this).add(movieEntity);
                Toast.makeText(AddEditMovie.this, "Movie Added", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(AddEditMovie.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else if(opt ==22){
            try{
                movieEntity.setFavourite(checkBox.isChecked());
                new DatabaseHandler(AddEditMovie.this).update(movieEntity);
                Toast.makeText(AddEditMovie.this, "Movie Updated", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(AddEditMovie.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }




    }
}