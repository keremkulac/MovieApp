package com.keremkulac.movieapp.service

import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.model.Genres
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieGenreApi {
    //https://api.themoviedb.org/3/genre/movie/list?api_key=4af5441468ab90c82bbdf23668f9244f&language=en-US

    @GET("genre/movie/list")
    fun getMovieGenre(@Query("api_key") key : String) : Single<Genres>
}