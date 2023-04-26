package com.keremkulac.movieapp.repository

import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.repository.model.Genres
import com.keremkulac.movieapp.repository.model.LatestTvSeries
import com.keremkulac.movieapp.util.API_KEY
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val api : MovieRepository){

    suspend fun getPopularMovies() : Response<MovieResult> {
        return api.getPopularMovies(API_KEY)
    }
    suspend fun getTrendMovies() : Response<MovieResult>{
        return api.getTrendMovies(API_KEY)
    }
    suspend  fun getUpcomingMovies() : Response<MovieResult>{
        return api.getUpcomingMovies(API_KEY)
    }
    suspend fun getMovieGenre() : Response<Genres>{
        return api.getMovieGenre(API_KEY)
    }
   suspend fun getLatest() : Response<LatestTvSeries>{
        return api.getLatest(API_KEY)
    }
    suspend fun getTvPopular() : Response<MovieResult>{
        return api.getTvPopular(API_KEY)
    }
    suspend fun getTopRated() : Response<MovieResult>{
        return api.getTopRated(API_KEY)
    }
    suspend fun getTvSeriesGenre() : Response<Genres>{
        return api.getTvSeriesGenre(API_KEY)
    }
    suspend fun getLatestMovie() : Response<LatestMovie>{
        return api.getLatestMovie(API_KEY)
    }
}