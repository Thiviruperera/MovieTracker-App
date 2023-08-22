package com.example.movie_tracker.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final String DATABASE = "db_movie";
    private static final String TABLE = "movie";
    private static final String COL1 = "movie_title";
    private static final String COL2 = "movie_director";
    private static final String COL3 = "movie_year";
    private static final String COL4 = "movie_rating";
    private static final String COL5 = "movie_actors";
    private static final String COL6 = "movie_favourite";
    private static final String COL7 = "movie_review";

    public DatabaseHandler(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE+"("+COL1+" TEXT PRIMARY KEY,"+COL2+" TEXT,"+COL3+" INTEGER,"+COL4+" DECIMAL,"+COL5+" TEXT,"+COL6+" BOOLEAN,"+COL7+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }


    public void add(MovieEntity movieEntity) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + TABLE + " VALUES ("+"'"+ movieEntity.getTitle()+"'"+"," +
                    "'"+ movieEntity.getDirector()+"',"+
                    movieEntity.getYear()+"," +
                    movieEntity.getRating()+"," +
                    "'"+ movieEntity.getActors()+"',"+
                    (movieEntity.isFavourite() ? 1 : 0)+"," +
                    "'"+ movieEntity.getReview()+"');";

        database.execSQL(sql);
        database.close();
    }

    public ArrayList<MovieEntity> getAll() {
        ArrayList<MovieEntity> movieEntityArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE +" ORDER BY "+COL1+" COLLATE NOCASE ASC",null);
        if (cursor.moveToFirst()){
            do {
                // Passing values
                String column1 = cursor.getString(0);
                String column2 = cursor.getString(1);
                int column3 = cursor.getInt(2);
                double column4 = cursor.getDouble(3);
                String column5 = cursor.getString(4);
                boolean column6 = cursor.getInt(5) == 1;
                String column7 = cursor.getString(6);

                movieEntityArrayList.add(new MovieEntity(column1, column2, column3, column4, column5, column6, column7));
            } while(cursor.moveToNext());
        }

        return movieEntityArrayList;
    }

    public ArrayList<MovieEntity> getAllFavs() {
        ArrayList<MovieEntity> movieEntityArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE+" WHERE "+COL6+" = 1" ,null);
        if (cursor.moveToFirst()){
            do {
                // Passing values
                String column1 = cursor.getString(0);
                String column2 = cursor.getString(1);
                int column3 = cursor.getInt(2);
                double column4 = cursor.getDouble(3);
                String column5 = cursor.getString(4);
                boolean column6 = cursor.getInt(5) == 1;
                String column7 = cursor.getString(6);

                movieEntityArrayList.add(new MovieEntity(column1, column2, column3, column4, column5, column6, column7));
            } while(cursor.moveToNext());
        }

        return movieEntityArrayList;
    }

    public void updateFavs(ArrayList<MovieEntity> updatedMovieEntities) {
        SQLiteDatabase database = getWritableDatabase();

        int bool;
        for (int i = 0; i< updatedMovieEntities.size() ; i++){
            if(updatedMovieEntities.get(i).isFavourite())
                bool = 1;
            else
                bool = 0;

            String sql = "UPDATE "+TABLE+" SET "+COL6+" = "+bool+" WHERE "+COL1+" = "+"'"+ updatedMovieEntities.get(i).getTitle()+"'"+";";
            Log.w("LOG","dd "+sql);
            database.execSQL(sql);
        }

        database.close();
    }

    public ArrayList<MovieEntity> search(String criteria) {
        ArrayList<MovieEntity> movieEntityArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE+" WHERE UPPER("+COL1+") LIKE UPPER('%"+criteria+"%') OR UPPER("+COL2 +") LIKE UPPER('%"+criteria+"%') OR UPPER("+COL5+") LIKE UPPER('%"+criteria+"%');" ,null);
        if (cursor.moveToFirst()){
            do {
                // Passing values
                String column1 = cursor.getString(0);
                String column2 = cursor.getString(1);
                int column3 = cursor.getInt(2);
                double column4 = cursor.getDouble(3);
                String column5 = cursor.getString(4);
                boolean column6 = cursor.getInt(5) == 1;
                String column7 = cursor.getString(6);

                movieEntityArrayList.add(new MovieEntity(column1, column2, column3, column4, column5, column6, column7));
            } while(cursor.moveToNext());
        }

        return movieEntityArrayList;
    }

    public void update(MovieEntity updatedMovieEntity) {
        SQLiteDatabase database = getWritableDatabase();

        int bool;
        if(updatedMovieEntity.isFavourite())
            bool = 1;
        else
            bool = 0;

        String sql = "UPDATE "+TABLE+" SET "+COL1+" = '"+ updatedMovieEntity.getTitle()+"' , "+COL2+" = '"+ updatedMovieEntity.getDirector()+"' , "+COL3+" = "+ updatedMovieEntity.getYear()+" , "+COL4+" = "+ updatedMovieEntity.getRating()+" , "+COL5+" = '"+ updatedMovieEntity.getActors()+"' , "+COL6+" = "+bool+" , "+COL7+" = '"+ updatedMovieEntity.getReview()+"' WHERE "+COL1+" = '"+ updatedMovieEntity.getTitle()+"'"+";";
        database.execSQL(sql);

        database.close();
    }
}
