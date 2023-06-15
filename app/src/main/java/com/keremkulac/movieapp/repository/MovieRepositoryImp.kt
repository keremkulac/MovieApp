package com.keremkulac.movieapp.repository

import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.repository.model.Genres
import com.keremkulac.movieapp.repository.model.LatestTvSeries
import com.keremkulac.movieapp.util.API_KEY
import com.keremkulac.movieapp.util.Resource
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val api : MovieRepository) : MovieRepositoryInterface{
    override suspend fun getPopularMovies(): Resource<MovieResult> {
        return try {
            val response = api.getPopularMovies(API_KEY)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("No data",null)
        }
    }

    override suspend fun getTrendMovies(): Resource<MovieResult> {
        return try {
            val response = api.getTrendMovies(API_KEY)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("No data",null)
        }
    }

    override suspend fun getUpcomingMovies(): Resource<MovieResult> {
        return try {
            val response = api.getUpcomingMovies(API_KEY)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("No data",null)
        }
    }

    override suspend fun getMovieGenre(): Resource<Genres> {
        return try {
            val response = api.getMovieGenre(API_KEY)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("No data",null)
        }
    }

    override suspend fun getLatest(): Response<LatestTvSeries> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvPopular(): Resource<MovieResult> {
        return try {
            val response = api.getTvPopular(API_KEY)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("No data",null)
        }
    }

    override suspend fun getTopRated(): Resource<MovieResult> {
        return try {
            val response = api.getTopRated(API_KEY)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("No data",null)
        }
    }

    override suspend fun getTvSeriesGenre(): Resource<Genres> {
        return try {
            val response = api.getTvSeriesGenre(API_KEY)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e : java.lang.Exception){
            Resource.error("No data",null)
        }
    }

    override suspend fun getLatestMovie(): Response<LatestMovie> {
        TODO("Not yet implemented")
    }


}