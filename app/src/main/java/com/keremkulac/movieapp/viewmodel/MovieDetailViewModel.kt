package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.service.MovieGenreApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    var genres = MutableLiveData<ArrayList<Genre>>()
    private val movieGenreApiImp = MovieGenreApiImp()
    init {
        getGenres()
    }
    private fun getGenres(){
        disposable.add(
            movieGenreApiImp.getMovieGenre(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Genres>(){
                    override fun onSuccess(t: Genres) {
                        genres.value = t.genres

                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }
}