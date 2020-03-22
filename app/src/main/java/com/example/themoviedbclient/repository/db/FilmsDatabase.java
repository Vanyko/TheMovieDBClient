package com.example.themoviedbclient.repository.db;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.themoviedbclient.repository.db.paging.DBFilmsDataSourceFactory;
import com.example.themoviedbclient.repository.entity.Film;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Film.class}, version = 1, exportSchema = false)
public abstract class FilmsDatabase extends RoomDatabase {

    private static final String FILMS_DB_NAME = "drivers.db";

    private static FilmsDatabase instance;

    private LiveData<PagedList<Film>> filmsPaged;

    public abstract FilmsDao filmsDao();

    public static FilmsDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (FilmsDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            FilmsDatabase.class, FILMS_DB_NAME).build();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false).setPageSize(20).build();
        Executor executor = Executors.newFixedThreadPool(3);
//        DBFilmsDataSourceFactory dataSourceFactory = new DBFilmsDataSourceFactory(filmsDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(filmsDao().getAllPaged(), pagedListConfig);
        filmsPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<Film>> getFilmsPaged() {
        return filmsPaged;
    }
}

