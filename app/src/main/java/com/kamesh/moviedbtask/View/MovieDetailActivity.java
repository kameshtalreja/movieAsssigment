package com.kamesh.moviedbtask.View;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kamesh.moviedbtask.Model.MoviesObject;
import com.kamesh.moviedbtask.R;
import com.kamesh.moviedbtask.Utility.Constants;


public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgBackground,imgPoster,imgBack;
    TextView tvMovieTitle,tvMovieOverview,tvMovieRating,tvMovieReleaseDate;

    MoviesObject.Result movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imgBack = findViewById(R.id.imgBack);
        imgBackground = findViewById(R.id.imgBackground);
        imgPoster = findViewById(R.id.imgPoster);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieOverview = findViewById(R.id.tvMovieOverview);
        tvMovieRating = findViewById(R.id.tvMovieRating);
        tvMovieReleaseDate = findViewById(R.id.tvMovieReleaseDate);

        imgBack.setOnClickListener(this);


        if(getIntent().getSerializableExtra(Constants.MOVIES_DETAILS) != null){
            movieDetails = (MoviesObject.Result) getIntent().getSerializableExtra(Constants.MOVIES_DETAILS);

            /*
            * Set the data to widgets */
            setData();
        }



    }

    private void setData() {

        if(movieDetails.getPosterPath() != null){
            Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_URL + movieDetails.getPosterPath()).into(imgPoster);
        }

        if(movieDetails.getBackdropPath() != null){
            Glide.with(getApplicationContext()).load(Constants.IMAGE_BASE_URL + movieDetails.getBackdropPath()).into(imgBackground);
        }


        tvMovieTitle.setText(movieDetails.getOriginalTitle());
        tvMovieOverview.setText(movieDetails.getOverview());
        if(movieDetails.getVoteAverage() != null){
            tvMovieRating.setText(String.valueOf(movieDetails.getVoteAverage()));
        }

        if(movieDetails.getReleaseDate() != null){
            tvMovieReleaseDate.setVisibility(View.VISIBLE);
            tvMovieReleaseDate.setText(movieDetails.getReleaseDate());
        }else{
            tvMovieReleaseDate.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack:
                    finish();
                break;
        }
    }
}