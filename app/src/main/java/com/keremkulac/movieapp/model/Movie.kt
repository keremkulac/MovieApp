package com.keremkulac.movieapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MovieResult(
    @SerializedName("results")
    val movies: ArrayList<Movie>
) : Serializable
data class Movie(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("genre_ids")
    val genre_ids: ArrayList<Int>,
    @SerializedName("id")
    val id: Long,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("name")
    val name: String?,
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
