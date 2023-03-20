package com.keremkulac.movieapp.service

import com.keremkulac.movieapp.LatestMovie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestMovieApi {
    @GET("movie/latest")
    fun getLatestMovie(@Query("api_key") key : String) : Single<LatestMovie>
}