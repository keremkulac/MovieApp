package com.keremkulac.movieapp.repository

import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.repository.model.Genres
import com.keremkulac.movieapp.repository.model.LatestTvSeries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRepository {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") key : String) : Response<MovieResult>
    @GET("trending/all/day")
    suspend fun getTrendMovies(@Query("api_key") key : String) : Response<MovieResult>
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") key : String) : Response<MovieResult>
    @GET("genre/movie/list")
    suspend fun getMovieGenre(@Query("api_key") key : String) : Response<Genres>
    @GET("tv/latest")
    suspend fun getLatest(@Query("api_key") key : String) : Response<LatestTvSeries>
    @GET("tv/popular")
    suspend fun getTvPopular(@Query("api_key") key : String) : Response<MovieResult>
    @GET("tv/top_rated")
    suspend fun getTopRated(@Query("api_key") key : String) : Response<MovieResult>
    @GET("genre/tv/list")
    suspend fun getTvSeriesGenre(@Query("api_key") key : String) : Response<Genres>
    @GET("movie/latest")
    suspend fun getLatestMovie(@Query("api_key") key : String) : Response<LatestMovie >
}