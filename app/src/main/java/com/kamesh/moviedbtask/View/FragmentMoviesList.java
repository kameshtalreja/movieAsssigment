package com.kamesh.moviedbtask.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kamesh.moviedbtask.Adapters.MovieAdapter;
import com.kamesh.moviedbtask.Interface.CallBack;
import com.kamesh.moviedbtask.Interface.PosterClick;
import com.kamesh.moviedbtask.Model.MoviesObject;
import com.kamesh.moviedbtask.Network.ServiceLayer;
import com.kamesh.moviedbtask.Presenter.PresenterMovieList;
import com.kamesh.moviedbtask.R;
import com.kamesh.moviedbtask.Utility.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentMoviesList extends Fragment implements PresenterMovieList.MovieListView, PosterClick, TextWatcher {

    View view;

    GridView gridMoviesList;
    EditText eSearch;
    TextView tvMessage;

    MovieAdapter movieAdapter;

    PresenterMovieList presenter;

    ArrayList<MoviesObject.Result> arrayList;

    //Variables
    private static final String TAG = FragmentMoviesList.class.getName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_list,container,false);

        presenter = new PresenterMovieList(getContext(),this);

        setWidgets();


        arrayList = new ArrayList<>();
        movieAdapter = new MovieAdapter(getContext(),arrayList,this);
        gridMoviesList.setAdapter(movieAdapter);



        return view;

    }

    /*
    *  Create Menu For Sorting Option */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.movies_sort_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.popular:

                sort(2);

                break;
            case R.id.rating:

                sort(1);

                break;
        }
        return super.onOptionsItemSelected(item);
    }



    /*
    * set widgets
    * */
    private void setWidgets() {

        eSearch = view.findViewById(R.id.eSearch);
        tvMessage = view.findViewById(R.id.tvMessage);
        gridMoviesList = view.findViewById(R.id.moviesList);

        eSearch.addTextChangedListener(this);

    }


    /*
    * Call Service For get Movies Details
    * */
    @Override
    public void onStart() {
        super.onStart();

        if(arrayList.size() <= 0){
            arrayList.clear();
            presenter.moviesObjectsList.clear();
            presenter.getMoviesList();
        }
    }


    /*
    Get the movies list if success
    */
    @Override
    public void onSuccess() {
        if(presenter.moviesObjectsList.size() > 0){

            tvMessage.setVisibility(View.GONE);
            gridMoviesList.setVisibility(View.VISIBLE);

            arrayList.addAll(presenter.moviesObjectsList);
            movieAdapter.notifyDataSetChanged();

        }else{

            gridMoviesList.setVisibility(View.GONE);
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText("No Movies Found");
        }
    }

    /*
    Get the error message if in case it failed
    */
    @Override
    public void onFailed(String message) {
        Log.d(TAG,message);
        gridMoviesList.setVisibility(View.GONE);
        gridMoviesList.setVisibility(View.VISIBLE);
        tvMessage.setText(message);
    }


    /*
    On Poster Click Action
    */
    @Override
    public void onPosterClick(int position) {
        Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
        intent.putExtra(Constants.MOVIES_DETAILS, arrayList.get(position));
        startActivity(intent);
    }


    /*
    * Method for Sorting
    * */
    private void sort(int sortType){
        switch (sortType){
            case 1: // Rating

                Collections.sort(arrayList, new Comparator<MoviesObject.Result>() {
                    @Override
                    public int compare(MoviesObject.Result result, MoviesObject.Result t1) {
                        Double rating  = new Double(result.getVoteAverage());
                        Double rating2 = new Double(t1.getVoteAverage());
                        return rating2.compareTo(rating);
                    }
                });
                movieAdapter.notifyDataSetChanged();

                break;
            default:   //Popular

                Collections.sort(arrayList, new Comparator<MoviesObject.Result>() {
                    @Override
                    public int compare(MoviesObject.Result result, MoviesObject.Result t1) {
                        Double popular  = new Double(result.getPopularity());
                        Double popular2 = new Double(t1.getPopularity());
                        return popular2.compareTo(popular);
                    }
                });
                movieAdapter.notifyDataSetChanged();

                break;
        }
    }



    /*
    * Edit Text for search */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(eSearch.getText().toString().trim().length() > 0){
            applySearch(eSearch.getText().toString().trim().toLowerCase());
        }else{
            arrayList.clear();
            arrayList.addAll(presenter.moviesObjectsList);
            movieAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /*
    *  Apply Search
    * */
    private void applySearch(String search) {

        arrayList.clear();

        for (int i = 0; i < presenter.moviesObjectsList.size(); i++) {
            if (presenter.moviesObjectsList.get(i).getOriginalTitle().toLowerCase().contains(search) || presenter.moviesObjectsList.get(i).getOriginalTitle().toLowerCase().contains(search)) {
                //Do whatever you want here
                arrayList.add(presenter.moviesObjectsList.get(i));
            }
        }
        movieAdapter.notifyDataSetChanged();

    }
}
