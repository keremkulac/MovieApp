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
   // private  var list3 : ArrayList<Movie>
   var i = 0
    var l = 0

    init {
        getPopularMovies()
        getTrendMovies()
       // list3 = ArrayList()
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
        var list1 = trendMovies.value!!
        var list2 = popularMovies.value!!


            for (x in list1){
                for (y in list2){
                    if(x.original_title != null && y.original_title != null){
                        if(x.original_title == y.original_title){
                            Log.d("TAGG",l.toString())
                       //     list1.removeAt()
                        }
                    }
                   l +=1

                }
                i +=1
            }
        /*
        for (trendMovieItems in list1!!){
            for (popularMovieItems in list2!!){
                if(popularMovieItems.original_title != null && trendMovieItems.original_title != null) {

                    if (popularMovieItems.original_title == trendMovieItems.original_title) {
                        sameMovies.add(popularMovieItems)
                        list1.remove(trendMovieItems)
                      //  trendMovies.value!!.removeAll(listOf(popularMovieItems))
                    }
                }
            }
            Log.d("TAG12323142134",list1.size.toString())

        }


         */
        Log.d("TAG1134",list1.size.toString())
    }
    fun filter(text: String,adapter: SearchAdapter){
        combineMovies()
        val filteredList: ArrayList<Movie> = ArrayList()
        for (item in trendMovies.value!!) {
            if(item.original_title != null){
                if (item.original_title.toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item)
                }
            }
        }

        if (filteredList.isEmpty()) {
            // Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
           // Log.d("TAG1",filteredList.toString())
            adapter.filterList(filteredList)

        }
    }



}