package com.keremkulac.movieapp.service

import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.model.LatestTvSeries
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") key : String) : Single<MovieResult>
    @GET("trending/all/day")
    fun getTrendMovies(@Query("api_key") key : String) : Single<MovieResult>
    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") key : String) : Single<MovieResult>
    @GET("genre/movie/list")
    fun getMovieGenre(@Query("api_key") key : String) : Single<Genres>
    @GET("tv/latest")
    fun getLatest(@Query("api_key") key : String) : Single<LatestTvSeries>
    @GET("tv/popular")
    fun getTvPopular(@Query("api_key") key : String) : Single<MovieResult>
    @GET("tv/top_rated")
    fun getTopRated(@Query("api_key") key : String) : Single<MovieResult>
    @GET("genre/tv/list")
    fun getTvSeriesGenre(@Query("api_key") key : String) : Single<Genres>
    @GET("movie/latest")
    fun getLatestMovie(@Query("api_key") key : String) : Single<LatestMovie>
}