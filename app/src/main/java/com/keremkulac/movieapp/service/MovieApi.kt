package com.keremkulac.movieapp.service

import com.keremkulac.movieapp.PopularMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    // https://api.themoviedb.org/3/movie/popular?api_key=4af5441468ab90c82bbdf23668f9244f
    //https://api.themoviedb.org/3/movie/550?api_key=4af5441468ab90c82bbdf23668f9244f
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") key : String) : Single<PopularMovies>
}