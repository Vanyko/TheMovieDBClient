package com.example.themoviedbclient.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.themoviedbclient.repository.db.FilmsDatabase;
import com.example.themoviedbclient.repository.entity.Film;
import com.example.themoviedbclient.repository.entity.FilmsList;
import com.example.themoviedbclient.repository.remote.RemoteEndpoints;
import com.example.themoviedbclient.repository.remote.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private FilmsDatabase filmsDatabase;

    public void setFilmsDatabase(FilmsDatabase filmsDatabase) {
        this.filmsDatabase = filmsDatabase;
    }

    /*Create handle for the RetrofitInstance interface*/
    RemoteEndpoints service = RetrofitClientInstance.getRetrofitInstance().create(RemoteEndpoints.class);

    public LiveData<List<Film>> getFilmsList() {
        return filmsDatabase.filmsDao().getAll();
    }

    public void requestFilmsList() {
        // TODO: Return cached data
//        FilmsList cachedFilmsList = new FilmsList();
//        cachedFilmsList.setResults(new ArrayList<>());
//        filmsList.postValue(cachedFilmsList);

        // Make network request
        Call<FilmsList> call = service.getFilmsList("popularity.desc");
        call.enqueue(new Callback<FilmsList>() {
            @Override
            public void onResponse(Call<FilmsList> call, Response<FilmsList> response) {
                if (response.isSuccessful()) {
                    new Thread(()-> {
                        filmsDatabase.filmsDao().insertAll(response.body().getResults());
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<FilmsList> call, Throwable t) {
                Log.e("REPOSITORY", "onFailure:" + t.toString());
            }
        });
    }

}
