package com.kamesh.moviedbtask.Network;

import android.util.Log;

import com.kamesh.moviedbtask.Interface.CallBack;
import com.kamesh.moviedbtask.Interface.NetworkRequests;
import com.kamesh.moviedbtask.Model.MoviesObject;
import com.kamesh.moviedbtask.Utility.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.kamesh.moviedbtask.Utility.Constants.DB_API_KEY;

public class ServiceLayer {

    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SERVICE_BASE_URL)
            .client(new OkHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static NetworkRequests networkRequests = retrofit.create(NetworkRequests.class);


    public static void getPopularMovies(CallBack callBack){

        Call<MoviesObject> callback2 = networkRequests.getPopularMovies(DB_API_KEY,1);
        callback2.enqueue(new Callback<MoviesObject>() {
            @Override
            public void onResponse(Call<MoviesObject> call, Response<MoviesObject> response) {
                if(response.isSuccessful()){
                    callBack.success(response.body());
                }else{
                    callBack.failed(response.message());
                }

            }

            @Override
            public void onFailure(Call<MoviesObject> call, Throwable t) {
                Log.d("Response",t.getLocalizedMessage());
                callBack.failed(t.getLocalizedMessage());
            }
        });

    }

}
