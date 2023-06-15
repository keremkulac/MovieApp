package com.keremkulac.movieapp.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.repository.MovieRepositoryImp
import com.keremkulac.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MovieViewModel
@Inject constructor(private val movieRepositoryImp: MovieRepositoryImp): ViewModel() {
    private val _popularMovieList = MutableLiveData<Resource<MovieResult>>()
    val popularMoviesList : LiveData<Resource<MovieResult>>
        get() = _popularMovieList

    private val _trendMovieList = MutableLiveData<Resource<MovieResult>>()
    val trendMovieList : LiveData<Resource<MovieResult>>
        get() = _trendMovieList

    private val _upcomingMovieList = MutableLiveData<Resource<MovieResult>>()
    val upcomingMovieList : LiveData<Resource<MovieResult>>
        get() = _upcomingMovieList

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.localizedMessage?.let { Log.d("TAG", it) }
    }

    init {
        getPopularMovies()
        getTrendMovies()
        getUpcomingMovies()
        getPopularMovies1()
    }

    private fun getPopularMovies1() = viewModelScope.launch {
        val result = movieRepositoryImp.getPopularMovies()
        result.let {
            _popularMovieList.postValue(it)
        }
    }
    private fun getPopularMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getPopularMovies()
            result.let {
                _popularMovieList.postValue(it)
            }
        }


    }
    private fun getTrendMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTrendMovies()
            result.let {
                _trendMovieList.postValue(it)
            }
        }
    }
    private fun getUpcomingMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getUpcomingMovies()
            result.let {
                _upcomingMovieList.postValue(it)
            }
        }
    }

}