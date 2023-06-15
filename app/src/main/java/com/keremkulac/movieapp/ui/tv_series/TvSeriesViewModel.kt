package com.keremkulac.movieapp.ui.tv_series

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.repository.MovieRepositoryImp
import com.keremkulac.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel
    @Inject constructor(private val movieRepositoryImp: MovieRepositoryImp) : ViewModel(){
    private val _popularTvSeriesList = MutableLiveData<Resource<MovieResult>>()
    val popularTvSeriesList : LiveData<Resource<MovieResult>>
        get() = _popularTvSeriesList

    private val _topRatedTvSeriesList = MutableLiveData<Resource<MovieResult>>()
    val topRatedTvSeriesList : LiveData<Resource<MovieResult>>
        get() = _topRatedTvSeriesList


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
            result.let {
                _popularTvSeriesList.postValue(it)
            }
        }
    }

    private fun getTopRatedTvSeries(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTopRated()
            result.let {
                _topRatedTvSeriesList.postValue(it)
            }
        }
    }

}