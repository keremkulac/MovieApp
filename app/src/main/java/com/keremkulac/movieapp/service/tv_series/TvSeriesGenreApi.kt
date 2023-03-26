package com.keremkulac.movieapp.service.tv_series

import com.keremkulac.movieapp.model.Genres
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TvSeriesGenreApi {

    @GET("genre/tv/list")
    fun getTvSeriesGenre(@Query("api_key") key : String) : Single<Genres>
}