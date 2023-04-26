package com.keremkulac.movieapp.ui.tv_series

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.repository.MovieRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel
    @Inject constructor(private val movieRepositoryImp: MovieRepositoryImp) : ViewModel(){
    val topRatedTvSeries = MutableLiveData<ArrayList<Movie>>()
    val popularTvSeries = MutableLiveData<ArrayList<Movie>>()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.localizedMessage?.let { Log.d("TAG", it) }
    }
    init {
        getPopularTvSeries()
        getTopRatedTvSeries()
    }
    private fun getPopularTvSeries(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTvPopular()
            if(result.isSuccessful){
                result.body()?.let {
                    popularTvSeries.postValue(it.movies)
                }
            }
        }
    }

    private fun getTopRatedTvSeries(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTopRated()
            if(result.isSuccessful){
                result.body()?.let {
                    topRatedTvSeries.postValue(it.movies)
                }
            }
        }
    }

}