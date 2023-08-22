package com.example.movie_tracker.database;

import java.io.Serializable;

public class MovieEntity implements Serializable {

    private String title;
    private String director;
    private int year;
    private double rating;
    private String actors;
    private boolean favourite;
    private String review;

    private String imdbUrl;
    private String imdbId;
    private double imdbRating;
    private String imdbTitle;

    public MovieEntity(String imdbUrl, String imdbId, double imdbRating, String imdbTitle) {
        this.imdbUrl = imdbUrl;
        this.imdbId = imdbId;
        this.imdbRating = imdbRating;
        this.imdbTitle = imdbTitle;
    }

    public MovieEntity(String title, String director, int year, double rating, String actors, boolean favourite, String review) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.actors = actors;
        this.favourite = favourite;
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getImdbUrl() {
        return imdbUrl;
    }

    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbTitle() {
        return imdbTitle;
    }

    public void setImdbTitle(String imdbTitle) {
        this.imdbTitle = imdbTitle;
    }
}
