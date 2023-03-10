package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.adapter.TrendMovieAdapter
import com.keremkulac.movieapp.service.TrendMovieApiImp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TrendMovieViewModel {
    private val disposable = CompositeDisposable()
    var trendMovies = MutableLiveData<ArrayList<Movie>>()
    private val trendMovieApiImp = TrendMovieApiImp()

    init {
        getTrendMovies()
    }

    private fun getTrendMovies(){
        disposable.add(
            trendMovieApiImp.getTrendMovies("4af5441468ab90c82bbdf23668f9244f")
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

    fun filter(text: String,trendMovieList: ArrayList<Movie>,adapter: TrendMovieAdapter) {
        val filteredList: ArrayList<Movie> = ArrayList()
        for (item in trendMovieList) {
            if (item.original_title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
                Log.d("TAG1",filteredList.toString())

            }
        }
        if (filteredList.isEmpty()) {
            // Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredList)
        }
    }


}