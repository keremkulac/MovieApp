package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.PopularMovies
import com.keremkulac.movieapp.service.MovieApiImp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    var popularMovies = MutableLiveData<ArrayList<Movie>>()
    private val movieAPIImp = MovieApiImp()

    init {
        getData()
    }
    private fun getData(){
        disposable.add(
            movieAPIImp.getPopularMovies("4af5441468ab90c82bbdf23668f9244f")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PopularMovies>(){
                    override fun onSuccess(t: PopularMovies) {
                        popularMovies.value = t.popularMovies
                    }
                    override fun onError(e: Throwable) {
                        Log.d("TAG",e.localizedMessage.toString())
                    }
                })
        )
    }

}