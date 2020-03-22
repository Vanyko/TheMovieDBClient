package com.example.themoviedbclient.repository.remote;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.themoviedbclient.repository.entity.Film;
import com.example.themoviedbclient.repository.entity.NetworkState;
import com.example.themoviedbclient.repository.remote.paging.RemoteFilmsDataSourceFactory;
import com.example.themoviedbclient.repository.remote.paging.FilmsRemotePageKeyedDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FilmsRemote {
    final private static String TAG = FilmsRemote.class.getSimpleName();
    private static final int NUMBERS_OF_THREADS = 3;

    final private LiveData<PagedList<Film>> filmsPaged;
    final private LiveData<NetworkState> networkState;

    public FilmsRemote(RemoteFilmsDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Film> boundaryCallback){
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20).setPageSize(20).build();
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<FilmsRemotePageKeyedDataSource, LiveData<NetworkState>>)
                        FilmsRemotePageKeyedDataSource::getNetworkState);
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        filmsPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }

    public LiveData<PagedList<Film>> getPagedFilms(){
        return filmsPaged;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
