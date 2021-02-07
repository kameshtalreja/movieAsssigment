package com.kamesh.moviedbtask.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kamesh.moviedbtask.Interface.PosterClick;
import com.kamesh.moviedbtask.Model.MoviesObject;
import com.kamesh.moviedbtask.R;
import com.kamesh.moviedbtask.Utility.Constants;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MoviesObject.Result> moviesObjectsList;
    private PosterClick posterClick;

    public MovieAdapter(Context context, ArrayList<MoviesObject.Result> moviesObjectsList, PosterClick posterClick) {
        this.context = context;
        this.moviesObjectsList = moviesObjectsList;
        this.posterClick = posterClick;
    }

    @Override
    public int getCount() {
        return moviesObjectsList.size();
    }

    @Override
    public Object getItem(int i) {
        return moviesObjectsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        MoviesObject.Result moviesObject = (MoviesObject.Result) getItem(i);

        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(context).inflate(R.layout.item_movie_poster,viewGroup,false);
        }


        ImageView imgPoster = v.findViewById(R.id.imgPoster);
        TextView tvMovieTitle =  v.findViewById(R.id.tvMovieTitle);
        TextView tvMovieRating = v.findViewById(R.id.tvMovieRating);

        Glide.with(context).load(Constants.IMAGE_BASE_URL + moviesObject.getPosterPath()).into(imgPoster);

        tvMovieTitle.setText(moviesObject.getOriginalTitle());
        tvMovieRating.setText(String.valueOf(moviesObject.getVoteAverage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posterClick.onPosterClick(i);
            }
        });


//        if(convertView == null){
//
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_movie_poster,viewGroup,false);
//        }
//
//        ImageView imgPoster = viewGroup.findViewById(R.id.imgPoster);





//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        View gridView;
//
//        if(convertView != null){
//
//            gridView = new View(context);
//
//            // get layout from mobile.xml
//            gridView = inflater.inflate(R.layout.item_movie_poster, null);
//
//
//        }else{
//
//            gridView = convertView;
//        }

        return v;
    }
}
