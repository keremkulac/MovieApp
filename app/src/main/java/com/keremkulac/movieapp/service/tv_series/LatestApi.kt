package com.keremkulac.movieapp.service.tv_series

import com.keremkulac.movieapp.model.LatestTvSeries
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestApi {
    @GET("tv/latest")
    fun getLatest(@Query("api_key") key : String) : Single<LatestTvSeries>
}