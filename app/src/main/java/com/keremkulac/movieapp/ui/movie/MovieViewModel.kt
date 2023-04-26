package com.keremkulac.movieapp.ui.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.repository.MovieRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MovieViewModel
@Inject constructor(private val movieRepositoryImp: MovieRepositoryImp ): ViewModel() {
    val popularMovies = MutableLiveData<ArrayList<Movie>>()
    val trendMovies = MutableLiveData<ArrayList<Movie>>()
    val upcomingMovies = MutableLiveData<ArrayList<Movie>>()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.localizedMessage?.let { Log.d("TAG", it) }
    }

    init {
        getPopularMovies()
        getTrendMovies()
        getUpcomingMovies()
    }
    private fun getPopularMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getPopularMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    popularMovies.postValue(it.movies)
                }
            }
        }
    }
    private fun getTrendMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTrendMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    trendMovies.postValue(it.movies)
                }
            }
        }
    }
    private fun getUpcomingMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getUpcomingMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    upcomingMovies.postValue(it.movies)
                }
            }
        }
    }

}