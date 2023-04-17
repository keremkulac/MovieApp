package com.keremkulac.movieapp.service

import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.model.LatestTvSeries
import com.keremkulac.movieapp.util.API_KEY
import com.keremkulac.movieapp.util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceImp {
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiService::class.java)
    fun getPopularMovies() : Single<MovieResult> {
        return api.getPopularMovies(API_KEY)
    }
    fun getTrendMovies() : Single<MovieResult>{
        return api.getTrendMovies(API_KEY)
    }
    fun getUpcomingMovies() : Single<MovieResult>{
        return api.getUpcomingMovies(API_KEY)
    }
    fun getMovieGenre() : Single<Genres>{
        return api.getMovieGenre(API_KEY)
    }
    fun getLatest() : Single<LatestTvSeries>{
        return api.getLatest(API_KEY)
    }
    fun getTvPopular() : Single<MovieResult>{
        return api.getTvPopular(API_KEY)
    }
    fun getTopRated() : Single<MovieResult>{
        return api.getTopRated(API_KEY)
    }
    fun getTvSeriesGenre() : Single<Genres>{
        return api.getTvSeriesGenre(API_KEY)
    }
    fun getLatestMovie() : Single<LatestMovie>{
        return api.getLatestMovie(API_KEY)
    }
}