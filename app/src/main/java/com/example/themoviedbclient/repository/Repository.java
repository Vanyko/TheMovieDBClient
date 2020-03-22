package com.example.themoviedbclient.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.example.themoviedbclient.repository.db.FilmsDatabase;
import com.example.themoviedbclient.repository.entity.Film;
import com.example.themoviedbclient.repository.entity.FilmsList;
import com.example.themoviedbclient.repository.entity.NetworkState;
import com.example.themoviedbclient.repository.remote.FilmsRemote;
import com.example.themoviedbclient.repository.remote.paging.RemoteFilmsDataSourceFactory;

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();

    // TODO: Observe UI only on DB data, and when Network request completed, update DB.
    // TODO: Request pages from DB, and in the same time request refresh data from network, so when network request is completed, DB will be updated (UI is observed on DB).
    // TODO: Handle Network states properly

    private static Repository instance;

    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }

    private FilmsDatabase filmsDatabase;

    private FilmsRemote filmsRemote;

    private MediatorLiveData liveDataMerger;

    RemoteFilmsDataSourceFactory dataSourceFactory;

    private Repository() {

        dataSourceFactory = new RemoteFilmsDataSourceFactory();

        filmsRemote = new FilmsRemote(dataSourceFactory, boundaryCallback);
        // when we get new movies from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(filmsRemote.getPagedFilms(), value -> {
            liveDataMerger.setValue(value);
            Log.d(TAG, value.toString());
        });
    }

    public void setFilmsDatabase(FilmsDatabase filmsDatabase) {
        this.filmsDatabase = filmsDatabase;

        // save the movies into db
        dataSourceFactory.getMovies().observeForever(new Observer<FilmsList>() { // TODO: check if it's needed to observe forever or just refactor to use a callback
            @Override
            public void onChanged(FilmsList filmsList) {
                new Thread(()-> {
                    filmsDatabase.filmsDao().insertAll(filmsList.getResults());
                }).start();
            }
        });
    }


    private PagedList.BoundaryCallback<Film> boundaryCallback = new PagedList.BoundaryCallback<Film>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(filmsDatabase.getFilmsPaged(), value -> {
                liveDataMerger.postValue(value);
                liveDataMerger.removeSource(filmsDatabase.getFilmsPaged());
            });
        }
    };

    public LiveData<PagedList<Film>> getFilmsList(){
        return  liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return filmsRemote.getNetworkState();
    }
}
