package com.keremkulac.movieapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MovieResult(
    @SerializedName("results")
    val movies: ArrayList<Movie>
) : Serializable
data class Movie(
    @SerializedName("adult")
    val adult: Boolean?= null,
    @SerializedName("first_air_date")
    val first_air_date: String?= null,
    @SerializedName("backdrop_path")
    val backdrop_path: String?= null,
    @SerializedName("genre_ids")
    val genre_ids: ArrayList<Int>?= null,
    @SerializedName("id")
    val id: Long?= null,
    @SerializedName("original_language")
    val original_language: String?= null,
    @SerializedName("original_title")
    val original_title: String?= null,
    @SerializedName("overview")
    val overview: String?= null,
    @SerializedName("name")
    val name: String?= null,
    @SerializedName("popularity")
    val popularity: Double?= null,
    @SerializedName("poster_path")
    val poster_path: String?= null,
    @SerializedName("release_date")
    val release_date: String?= null,
    @SerializedName("title")
    val title: String?= null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val vote_average: Double?= null,
    @SerializedName("vote_count")
    val vote_count: Int?= null,
    @SerializedName("email")
    var email: String?= null,
    @SerializedName("isChecked")
    var isChecked: Boolean? = null,

    ) : Serializable

data class LatestMovie(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("genres")
    val genres: ArrayList<Any>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdb_id: String,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("release_date")
    val release_date: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean ,
    @SerializedName("vote_average")
    val vote_average: Double,
    @SerializedName("vote_count")
    val vote_count: Int,
) : Serializable
