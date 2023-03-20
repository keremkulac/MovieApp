package com.keremkulac.movieapp.service

import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LatestMovieApiImp {
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(LatestMovieApi::class.java)

    fun getLatestMovie(key : String) : Single<LatestMovie> {
        return api.getLatestMovie(key)
    }
}