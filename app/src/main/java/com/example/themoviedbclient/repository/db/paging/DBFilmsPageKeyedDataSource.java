package com.example.themoviedbclient.repository.db.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.themoviedbclient.repository.db.FilmsDao;
import com.example.themoviedbclient.repository.entity.Film;

import java.util.List;

public class DBFilmsPageKeyedDataSource extends PageKeyedDataSource<String, Film> {
    
    public static final String TAG = DBFilmsPageKeyedDataSource.class.getSimpleName();
    
    private final FilmsDao filmsDao;
    
    public DBFilmsPageKeyedDataSource(FilmsDao dao) {
        filmsDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Film> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        List<Film> movies = filmsDao.getAll();
        if(movies.size() != 0) {
            callback.onResult(movies, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Film> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Film> callback) {
    }
}

