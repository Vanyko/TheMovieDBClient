package com.example.themoviedbclient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.themoviedbclient.repository.Repository;
import com.example.themoviedbclient.repository.db.FilmsDatabase;
import com.example.themoviedbclient.repository.entity.Film;
import com.example.themoviedbclient.repository.entity.FilmsList;

import java.util.List;

public class FilmsListActivityViewModel extends ViewModel {

    Repository repository = Repository.getInstance();

    public void setFilmsDatabase(FilmsDatabase filmsDatabase) {
        this.repository.setFilmsDatabase(filmsDatabase);
    }

    public LiveData<PagedList<Film>> getFilmsList() {
        return repository.getFilmsList();
    }
}
