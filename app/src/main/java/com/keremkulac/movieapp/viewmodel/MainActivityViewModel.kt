package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.service.LatestMovieApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel {
    private val disposable = CompositeDisposable()
    var latestMovies = MutableLiveData<LatestMovie>()
    private val latestMovieApiImp = LatestMovieApiImp()

    init {
        getLatestMovies()
    }
    private fun getLatestMovies(){
        disposable.add(
            latestMovieApiImp.getLatestMovie(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LatestMovie>(){

                    override fun onSuccess(t: LatestMovie) {
                        latestMovies.value = t
                        Log.d("TASD",t.original_title)
                    }

                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }

}