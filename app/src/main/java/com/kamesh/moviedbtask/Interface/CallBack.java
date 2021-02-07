package com.kamesh.moviedbtask.Interface;

import com.kamesh.moviedbtask.Model.MoviesObject;

public interface CallBack {
    public void success(MoviesObject moviesObject);
    public void failed(String message);
}
