package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.service.ApiServiceImp
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class MovieViewModel : ViewModel() {
    val popularMovies = MutableLiveData<ArrayList<Movie>>()
    val trendMovies = MutableLiveData<ArrayList<Movie>>()
    val upcomingMovies = MutableLiveData<ArrayList<Movie>>()
    private val apiServiceImp = ApiServiceImp()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.localizedMessage?.let { Log.d("TAG", it.toString()) }
    }

    init {
        getPopularMovies()
        getTrendMovies()
        getUpcomingMovies()
    }
    private fun getPopularMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = apiServiceImp.getPopularMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    popularMovies.postValue(it.movies)
                }
            }
        }
    }
    private fun getTrendMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = apiServiceImp.getTrendMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    trendMovies.postValue(it.movies)
                }
            }
        }
    }
    private fun getUpcomingMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = apiServiceImp.getUpcomingMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    upcomingMovies.postValue(it.movies)
                }
            }
        }
    }

}