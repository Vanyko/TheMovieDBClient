package com.example.themoviedbclient.repository.remote.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.themoviedbclient.repository.entity.Film;
import com.example.themoviedbclient.repository.entity.FilmsList;
import com.example.themoviedbclient.repository.entity.NetworkState;
import com.example.themoviedbclient.repository.remote.RemoteEndpoints;
import com.example.themoviedbclient.repository.remote.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmsRemotePageKeyedDataSource extends PageKeyedDataSource<String, Film> {

    private static final String TAG = FilmsRemotePageKeyedDataSource.class.getSimpleName();

    private final RemoteEndpoints remoteEndpoints;
    private final MutableLiveData networkState;

    private MutableLiveData<FilmsList> filmsListLiveData;

    FilmsRemotePageKeyedDataSource() {
        remoteEndpoints = RetrofitClientInstance.getRetrofitInstance().create(RemoteEndpoints.class);
        networkState = new MutableLiveData();
        filmsListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData<FilmsList> getFilmsListLiveData() {
        return filmsListLiveData;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Film> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        Call<FilmsList> callBack = remoteEndpoints.getFilmsList("popularity.desc", 1);
        callBack.enqueue(new Callback<FilmsList>() {
            @Override
            public void onResponse(Call<FilmsList> call, Response<FilmsList> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body().getResults(), Integer.toString(1), Integer.toString(2));
                    networkState.postValue(NetworkState.LOADED);

//                    new Thread(()-> {
//                        filmsDatabase.filmsDao().insertAll(response.body().getResults()); // TODO: refactor to post in live data, so Repository will handle saving to database
//                    }).start();

                    filmsListLiveData.postValue(response.body());

                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<FilmsList> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }



    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Film> callback) {
        Log.i(TAG, "Loading page " + params.key );
        networkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(Integer.parseInt(params.key));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        Call<FilmsList> callBack = remoteEndpoints.getFilmsList("popularity.desc", page.get());
        callBack.enqueue(new Callback<FilmsList>() {
            @Override
            public void onResponse(Call<FilmsList> call, Response<FilmsList> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body().getResults(),Integer.toString(page.get()+1));
                    networkState.postValue(NetworkState.LOADED);
                    filmsListLiveData.postValue(response.body());
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<FilmsList> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(),Integer.toString(page.get()));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Film> callback) {

    }
}
