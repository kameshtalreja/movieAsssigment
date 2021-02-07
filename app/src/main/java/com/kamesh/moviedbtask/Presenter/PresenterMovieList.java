package com.kamesh.moviedbtask.Presenter;

import android.content.Context;
import android.util.Log;

import com.kamesh.moviedbtask.Interface.CallBack;
import com.kamesh.moviedbtask.Model.MoviesObject;
import com.kamesh.moviedbtask.Network.ServiceLayer;

import java.util.ArrayList;

public class PresenterMovieList {

    Context context;
    MovieListView view;

    public ArrayList<MoviesObject.Result> moviesObjectsList;



    public PresenterMovieList(Context context, MovieListView view) {
        this.context = context;
        this.view = view;
        moviesObjectsList = new ArrayList<>();
    }


    public void getMoviesList(){
           ServiceLayer.getPopularMovies(new CallBack() {
               @Override
               public void success(MoviesObject moviesObject) {
                    if(moviesObject.getResults().size() > 0){
                        moviesObjectsList.addAll(moviesObject.getResults());
                        view.onSuccess();
                    }else{
                        view.onFailed("failed");
                    }
               }

               @Override
               public void failed(String message) {
                   view.onFailed(message);
               }
        });
    }

    public interface MovieListView {

        void onSuccess();
        void onFailed(String message);

    }
}
