package com.example.themoviedbclient.repository.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.themoviedbclient.repository.entity.Film;

@Database(entities = {Film.class}, version = 1, exportSchema = false)
public abstract class FilmsDatabase extends RoomDatabase {

    private static final String FILMS_DB_NAME = "drivers.db";

    private static FilmsDatabase instance;

    public static FilmsDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (FilmsDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            FilmsDatabase.class, FILMS_DB_NAME).build();
                }
            }
        }
        return instance;
    }

    public abstract FilmsDao filmsDao();
}

