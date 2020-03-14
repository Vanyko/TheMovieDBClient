package com.example.themoviedbclient.repository.entity;

import java.util.List;

import com.example.themoviedbclient.repository.entity.Film;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilmsList {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("results")
    @Expose
    private List<Film> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Film> getResults() {
        return results;
    }

    public void setResults(List<Film> Films) {
        this.results = Films;
    }

    @Override
    public String toString() {
        return "FilmsList{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", results=" + results +
                '}';
    }
}
