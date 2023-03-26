package com.keremkulac.movieapp.service.movie

import com.keremkulac.movieapp.MovieResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingMovieApi {
    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") key : String) : Single<MovieResult>
}