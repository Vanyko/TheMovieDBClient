package com.example.themoviedbclient.repository.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.themoviedbclient.repository.entity.Film;

import java.util.List;

@Dao
public interface FilmsDao {

    @Query("SELECT * FROM film")
    LiveData<List<Film>> getAll();

    @Insert
    long insertFilm(Film film);

    @Insert(onConflict = OnConflictStrategy.REPLACE) // TODO: optimize it! Because every time new data from server is received, all the db entities have to be replaced
    void insertAll(List<Film> films);

}
