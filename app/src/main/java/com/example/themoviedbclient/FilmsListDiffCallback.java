package com.example.themoviedbclient;

import androidx.recyclerview.widget.DiffUtil;

import com.example.themoviedbclient.repository.entity.Film;

import java.util.List;

public class FilmsListDiffCallback extends DiffUtil.Callback {

    List<Film> oldFilmsList, newFilmsList;

    public FilmsListDiffCallback(List<Film> oldFilmsList, List<Film> newFilmsList) {
        this.oldFilmsList = oldFilmsList;
        this.newFilmsList = newFilmsList;
    }

    @Override
    public int getOldListSize() {
        return oldFilmsList.size();
    }

    @Override
    public int getNewListSize() {
        return newFilmsList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFilmsList.get(oldItemPosition).getId().equals(newFilmsList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFilmsList.get(oldItemPosition).equals(newFilmsList.get(newItemPosition));
    }
}
