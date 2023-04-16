package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.model.LatestTvSeries
import com.keremkulac.movieapp.service.movie.LatestMovieApiImp
import com.keremkulac.movieapp.service.movie.PopularMovieApiImp
import com.keremkulac.movieapp.service.tv_series.LatestApiImp
import com.keremkulac.movieapp.service.tv_series.PopularTvApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel {
    private val disposable = CompositeDisposable()
    var latestMovies = MutableLiveData<LatestMovie>()
    var latestTvSeries = MutableLiveData<LatestTvSeries>()
    private val latestMovieApiImp = LatestMovieApiImp()
    private val latestApiImp = LatestApiImp()
    private val popularMovieAPIImp = PopularMovieApiImp()
    val popularMovies = MutableLiveData<ArrayList<Movie>>()
    private val popularTvApiImp = PopularTvApiImp()
    val popularTvSeries = MutableLiveData<ArrayList<Movie>>()
    init {
        getLatestMovies()
        getLatestTvSeries()
        getPopularMovies()
        getPopularTvSeries()

    }
    private fun getLatestMovies(){
        disposable.add(
            latestMovieApiImp.getLatestMovie(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LatestMovie>(){

                    override fun onSuccess(t: LatestMovie) {
                        latestMovies.value = t

                    }

                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }

    private fun getLatestTvSeries(){
        disposable.add(
            latestApiImp.getLatest(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LatestTvSeries>(){

                    override fun onSuccess(t: LatestTvSeries) {
                        latestTvSeries.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
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

    private fun getPopularTvSeries(){
        disposable.add(
            popularTvApiImp.getTvPopular(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResult>(){
                    override fun onSuccess(t: MovieResult) {
                        popularTvSeries.value = t.movies
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }




}