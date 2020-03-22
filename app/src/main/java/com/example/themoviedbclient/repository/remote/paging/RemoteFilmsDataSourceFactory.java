package com.example.themoviedbclient.repository.remote.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.themoviedbclient.repository.entity.FilmsList;

public class RemoteFilmsDataSourceFactory extends DataSource.Factory {

    private static final String TAG = RemoteFilmsDataSourceFactory.class.getSimpleName();

    private MutableLiveData<FilmsRemotePageKeyedDataSource> networkStatus;
    private FilmsRemotePageKeyedDataSource moviesPageKeyedDataSource;

    public RemoteFilmsDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        moviesPageKeyedDataSource = new FilmsRemotePageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(moviesPageKeyedDataSource);
        return moviesPageKeyedDataSource;
    }

    public MutableLiveData<FilmsRemotePageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public MutableLiveData<FilmsList> getMovies() {
        return moviesPageKeyedDataSource.getFilmsListLiveData();
    }

}
