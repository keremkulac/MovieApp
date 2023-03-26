package com.keremkulac.movieapp.service.tv_series

import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TvSeriesGenreApiImp {
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(TvSeriesGenreApi::class.java)

    fun getTvSeriesGenre(key : String) : Single<Genres> {
        return api.getTvSeriesGenre(key)
    }
}