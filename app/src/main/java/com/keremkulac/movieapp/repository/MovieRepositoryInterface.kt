package com.keremkulac.movieapp.repository

import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.repository.model.Genres
import com.keremkulac.movieapp.repository.model.LatestTvSeries
import com.keremkulac.movieapp.util.Resource
import retrofit2.Response

interface MovieRepositoryInterface {

    suspend fun getPopularMovies() : Resource<MovieResult>

    suspend fun getTrendMovies() : Resource<MovieResult>

    suspend  fun getUpcomingMovies() : Resource<MovieResult>

    suspend fun getMovieGenre() : Resource<Genres>

    suspend fun getLatest() : Response<LatestTvSeries>

    suspend fun getTvPopular() : Resource<MovieResult>

    suspend fun getTopRated() : Resource<MovieResult>

    suspend fun getTvSeriesGenre() : Resource<Genres>

    suspend fun getLatestMovie() : Response<LatestMovie>
}