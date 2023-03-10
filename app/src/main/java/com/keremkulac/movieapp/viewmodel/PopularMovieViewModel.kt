package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.adapter.PopularMovieAdapter
import com.keremkulac.movieapp.service.PopularMovieApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PopularMovieViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    var popularMovies = MutableLiveData<ArrayList<Movie>>()
    private val popularMovieAPIImp = PopularMovieApiImp()

    init {
        getPopularMovies()
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
    fun filter(text: String,popularMovieList: ArrayList<Movie>,adapter: PopularMovieAdapter) {
        val filteredList: ArrayList<Movie> = ArrayList()
      //  Log.d("TAG2",popularMovieList.toString())
        for (item in popularMovieList) {
            if (item.original_title!!.toLowerCase().contains(text.toLowerCase())) {
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