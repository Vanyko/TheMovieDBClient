package com.example.themoviedbclient.repository.db.paging;

import androidx.paging.DataSource;

import com.example.themoviedbclient.repository.db.FilmsDao;

public class DBFilmsDataSourceFactory extends DataSource.Factory {

    private static final String TAG = DBFilmsDataSourceFactory.class.getSimpleName();

    private DBFilmsPageKeyedDataSource filmsPageKeyedDataSource;

    public DBFilmsDataSourceFactory(FilmsDao dao) {
        filmsPageKeyedDataSource = new DBFilmsPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return filmsPageKeyedDataSource;
    }
}
