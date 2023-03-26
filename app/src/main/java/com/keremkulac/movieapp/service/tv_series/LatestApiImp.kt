package com.keremkulac.movieapp.service.tv_series

import com.keremkulac.movieapp.model.LatestTvSeries
import com.keremkulac.movieapp.util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LatestApiImp {
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(LatestApi::class.java)

    fun getLatest(key : String) : Single<LatestTvSeries> {
        return api.getLatest(key)
    }
}