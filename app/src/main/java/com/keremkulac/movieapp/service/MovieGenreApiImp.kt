package com.keremkulac.movieapp.service

import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieGenreApiImp {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieGenreApi::class.java)

    fun getMovieGenre(key : String) : Single<Genres> {
        return api.getMovieGenre(key)
    }
}