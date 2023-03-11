package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.adapter.SearchAdapter
import com.keremkulac.movieapp.service.PopularMovieApiImp
import com.keremkulac.movieapp.service.TrendMovieApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchViewModel {

    private val disposable = CompositeDisposable()
    var popularMovies = MutableLiveData<ArrayList<Movie>>()
    var trendMovies = MutableLiveData<ArrayList<Movie>>()
    private val popularMovieAPIImp = PopularMovieApiImp()
    private val trendMovieApiImp = TrendMovieApiImp()
    private  var combinedList : ArrayList<Movie>

    init {
        getPopularMovies()
        getTrendMovies()
        combinedList = ArrayList()
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
    fun combineMovies(){
        var sameMovies = ArrayList<Movie>()
        for (trendMovieItems in trendMovies.value!!){
            for (popularMovieItems in popularMovies.value!!){
                if(popularMovieItems.id != null && trendMovieItems.id != null) {
                    if (popularMovieItems.id == trendMovieItems.id) {
                        sameMovies.add(popularMovieItems)
                    }
                }
            }
        }
        val deletedList = mutableListOf<Movie>()
        deletedList.addAll(trendMovies.value!!)
        for (i in trendMovies.value!!){
            for (x in sameMovies.indices){
                if(i.id == sameMovies[x].id){
                    deletedList.remove(i)
                }
            }
        }
        combinedList.addAll(deletedList)
        combinedList.addAll(popularMovies.value!!)
        Log.d("TAG1",combinedList.size.toString())

    }
    fun filter(text: String,adapter: SearchAdapter){

        val filteredList: ArrayList<Movie> = ArrayList()
        for (item in combinedList) {
            if(item.original_title != null){
                if (item.original_title.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item)
                }
            }
        }

        if (filteredList.isEmpty()) {
            // Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredList)
        }
    }
}