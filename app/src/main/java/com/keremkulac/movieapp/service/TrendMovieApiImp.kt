package com.keremkulac.movieapp.service

import com.keremkulac.movieapp.PopularMovies
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TrendMovieApiImp {
    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TrendMovieApi::class.java)

    fun getTrendMovies(key : String) : Single<PopularMovies> {
        return api.getTrendMovies(key)
    }
}