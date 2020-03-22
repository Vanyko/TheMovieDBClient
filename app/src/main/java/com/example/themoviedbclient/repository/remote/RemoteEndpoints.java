package com.example.themoviedbclient.repository.remote;

import com.example.themoviedbclient.repository.entity.FilmsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteEndpoints {

    @GET("discover/movie")
    Call<FilmsList> getFilmsList(@Query("sort_by") String sort,
                                 @Query("page")    int page);
}
