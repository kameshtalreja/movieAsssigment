package com.kamesh.moviedbtask.Interface;

import com.kamesh.moviedbtask.Model.MoviesObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.kamesh.moviedbtask.Utility.Constants.ACCEPT;
import static com.kamesh.moviedbtask.Utility.Constants.ACCEPT_LANGUAGE;
import static com.kamesh.moviedbtask.Utility.Constants.API_KEY;
import static com.kamesh.moviedbtask.Utility.Constants.APPLICATION_JSON;
import static com.kamesh.moviedbtask.Utility.Constants.CONTENT_TYPE;

import static com.kamesh.moviedbtask.Utility.Constants.EN_US;
import static com.kamesh.moviedbtask.Utility.Constants.PAGE;
import static com.kamesh.moviedbtask.Utility.Constants.MOVIE_ID;

public interface NetworkRequests {

    @Headers({
            CONTENT_TYPE + ": " + APPLICATION_JSON,
            ACCEPT + ": " + APPLICATION_JSON,
            ACCEPT_LANGUAGE + ": " + EN_US,

    } )
    @PUT("3/movie/popular")
    Call<MoviesObject> getPopularMovies(
            @Query(API_KEY) String api,
            @Query(PAGE) int page);



}
