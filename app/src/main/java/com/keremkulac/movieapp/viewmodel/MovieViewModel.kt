package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.service.movie.PopularMovieApiImp
import com.keremkulac.movieapp.service.movie.TrendMovieApiImp
import com.keremkulac.movieapp.service.movie.UpcomingMovieApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class MovieViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    val popularMovies = MutableLiveData<ArrayList<Movie>>()
    val trendMovies = MutableLiveData<ArrayList<Movie>>()
    val upcomingMovies = MutableLiveData<ArrayList<Movie>>()
    private val popularMovieAPIImp = PopularMovieApiImp()
    private val trendMovieApiImp =  TrendMovieApiImp()
    private val upcomingMovieApiImp = UpcomingMovieApiImp()

    init {
        getPopularMovies()
        getTrendMovies()
        getUpcomingMovies()
    }
    private fun getPopularMovies(){
        disposable.add(
            popularMovieAPIImp.getPopularMovies(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResult>(){
                    override fun onSuccess(t: MovieResult) {
                        popularMovies.value = t.movies
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }
    private fun getTrendMovies(){
        disposable.add(
            trendMovieApiImp.getTrendMovies(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResult>(){
                    override fun onSuccess(t: MovieResult) {
                        trendMovies.value = t.movies
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }
    private fun getUpcomingMovies(){
        disposable.add(
            upcomingMovieApiImp.getUpcomingMovies("4af5441468ab90c82bbdf23668f9244f")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResult>(){
                    override fun onSuccess(t: MovieResult) {
                        upcomingMovies.value = t.movies
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }
}