package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.service.ApiServiceImp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvSeriesViewModel {
    val topRatedTvSeries = MutableLiveData<ArrayList<Movie>>()
    val popularTvSeries = MutableLiveData<ArrayList<Movie>>()
    private val apiServiceImp = ApiServiceImp()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.localizedMessage?.let { Log.d("TAG", it.toString()) }
    }
    init {
        getPopularTvSeries()
        getTopRatedTvSeries()
    }
    private fun getPopularTvSeries(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = apiServiceImp.getTvPopular()
            if(result.isSuccessful){
                result.body()?.let {
                    popularTvSeries.postValue(it.movies)
                }
            }
        }
    }

    private fun getTopRatedTvSeries(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = apiServiceImp.getTopRated()
            if(result.isSuccessful){
                result.body()?.let {
                    topRatedTvSeries.postValue(it.movies)
                }
            }
        }
    }

}