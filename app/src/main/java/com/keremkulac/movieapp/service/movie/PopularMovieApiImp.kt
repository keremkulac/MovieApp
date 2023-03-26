package com.keremkulac.movieapp.service.movie

import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PopularMovieApiImp {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PopularMovieApi::class.java)

    fun getPopularMovies(key : String) : Single<MovieResult> {
        return api.getPopularMovies(key)
    }

}