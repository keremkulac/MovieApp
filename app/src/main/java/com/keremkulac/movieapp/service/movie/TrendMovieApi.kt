package com.keremkulac.movieapp.service.movie

import com.keremkulac.movieapp.MovieResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendMovieApi {
    //https://api.themoviedb.org/3/trending/all/day?api_key=4af5441468ab90c82bbdf23668f9244f
    // https://api.themoviedb.org/3/genre/movie/list?api_key=4af5441468ab90c82bbdf23668f9244f -> get movie categories
    @GET("trending/all/day")
    fun getTrendMovies(@Query("api_key") key : String) : Single<MovieResult>
}