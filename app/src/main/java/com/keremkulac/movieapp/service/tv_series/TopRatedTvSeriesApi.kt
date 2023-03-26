package com.keremkulac.movieapp.service.tv_series

import com.keremkulac.movieapp.model.TvSeriesResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TopRatedTvSeriesApi {
    @GET("tv/top_rated")
    fun getTopRated(@Query("api_key") key : String) : Single<TvSeriesResult>
}