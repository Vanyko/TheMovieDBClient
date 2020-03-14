package com.example.themoviedbclient.repository.entity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

import com.example.themoviedbclient.repository.converter.FilmGenresConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Film {

    @SerializedName("id")
    @Expose

    @PrimaryKey()
    @ColumnInfo(name = "id")
    private Integer id;

    @SerializedName("popularity")
    @Expose
    @ColumnInfo(name = "popularity")
    private Float popularity;

    @SerializedName("vote_count")
    @Expose
    @ColumnInfo(name = "vote_count")
    private Integer voteCount;

    @SerializedName("video")
    @Expose
    @ColumnInfo(name = "video")
    private Boolean video;

    @SerializedName("poster_path")
    @Expose
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @SerializedName("adult")
    @Expose
    @ColumnInfo(name = "adult")
    private Boolean adult;

    @SerializedName("backdrop_path")
    @Expose
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @SerializedName("original_language")
    @Expose
    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    @Expose
    @ColumnInfo(name = "original_title")
    private String originalTitle;

    @SerializedName("genre_ids")
    @Expose
    @ColumnInfo(name = "genre_ids")
    @TypeConverters(FilmGenresConverter.class)
    private List<Integer> genreIds = null;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("vote_average")
    @Expose
    @ColumnInfo(name = "vote_average")
    private Float voteAverage;

    @SerializedName("overview")
    @Expose
    @ColumnInfo(name = "overview")
    private String overview;

    @SerializedName("release_date")
    @Expose
    @ColumnInfo(name = "release_date")
    private String releaseDate;


    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (obj != null && getClass() != obj.getClass()) {
            return false;
        }

        Film film = (Film) obj;
        if (film != null) {
            return this.id.equals(film.getId());
        }

        return false;
    }

    @Override
    public String toString() {
        return "Film{" +
                "popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", posterPath='" + posterPath + '\'' +
                ", id=" + id +
                ", adult=" + adult +
                ", backdropPath='" + backdropPath + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
//                ", genreIds=" + genreIds +
                ", title='" + title + '\'' +
                ", voteAverage=" + voteAverage +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    public static final DiffUtil.ItemCallback<Film> DIFF_CALLBACK = new DiffUtil.ItemCallback<Film>() {
        @Override
        public boolean areItemsTheSame(@NonNull Film oldItem, @NonNull Film newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Film oldItem, @NonNull Film newItem) {
            return oldItem.equals(newItem);
        }
    };
}
