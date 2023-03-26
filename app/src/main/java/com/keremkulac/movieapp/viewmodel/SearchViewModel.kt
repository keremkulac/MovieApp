package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.adapter.SearchAdapter
import com.keremkulac.movieapp.model.*
import com.keremkulac.movieapp.service.movie.MovieGenreApiImp
import com.keremkulac.movieapp.service.movie.PopularMovieApiImp
import com.keremkulac.movieapp.service.movie.TrendMovieApiImp
import com.keremkulac.movieapp.service.movie.UpcomingMovieApiImp
import com.keremkulac.movieapp.service.tv_series.PopularTvApiImp
import com.keremkulac.movieapp.service.tv_series.TopRatedTvSeriesApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SearchViewModel : ViewModel(){
    val movieFoundError = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    var popularMovies = MutableLiveData<ArrayList<Movie>>()
    var trendMovies = MutableLiveData<ArrayList<Movie>>()
    var upcomingMovies = MutableLiveData<ArrayList<Movie>>()
    var genres = MutableLiveData<ArrayList<Genre>>()
    private val sameMovies = ArrayList<Movie>()
    private val sameTvSeries = ArrayList<TvSeries>()
    private val popularMovieAPIImp = PopularMovieApiImp()
    private val trendMovieApiImp = TrendMovieApiImp()
    private val movieGenreApiImp = MovieGenreApiImp()
    private val upcomingMovieApiImp = UpcomingMovieApiImp ()
    val topRatedTvSeries = MutableLiveData<ArrayList<TvSeries>>()
    val popularTvSeries = MutableLiveData<ArrayList<TvSeries>>()
    private val popularTvApiImp = PopularTvApiImp()
    private val topRatedTvSeriesApiImp = TopRatedTvSeriesApiImp()
    private  var combinedMovieList : ArrayList<Movie>
    private  var combinedTvSeriesList : ArrayList<TvSeries>
    private  var actionMovieList = ArrayList<Movie>()
    private  var adventureMovieList = ArrayList<Movie>()
    private  var animationMovieList  = ArrayList<Movie>()
    private  var comedyMovieList = ArrayList<Movie>()
    private  var crimeMovieList = ArrayList<Movie>()
    private  var documentaryMovieList = ArrayList<Movie>()
    private  var dramaMovieList = ArrayList<Movie>()
    private  var familyMovieList = ArrayList<Movie>()
    private  var fantasyMovieList = ArrayList<Movie>()
    private  var historyMovieList = ArrayList<Movie>()
    private  var horrorMovieList = ArrayList<Movie>()
    private  var musicMovieList = ArrayList<Movie>()
    private  var mysteryMovieList = ArrayList<Movie>()
    private  var romanceMovieList = ArrayList<Movie>()
    private  var scienceFictionMovieList = ArrayList<Movie>()
    private  var tvMovieList = ArrayList<Movie>()
    private  var thrillerMovieList = ArrayList<Movie>()
    private  var warMovieList = ArrayList<Movie>()
    private  var westernMovieList = ArrayList<Movie>()
    var allMovieListHm = HashMap<String,ArrayList<Movie>>()
    var genreWithSize = MutableLiveData<java.util.ArrayList<String>>()
    init {

        getPopularMovies()
        getTrendMovies()
        getGenres()
        getUpcomingMovies()
        getTopRatedTvSeries()
        getPopularTvSeries()
        combinedMovieList = ArrayList()
        combinedTvSeriesList = ArrayList()
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
            upcomingMovieApiImp.getUpcomingMovies(API_KEY)
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

    private fun getPopularTvSeries(){
        disposable.add(
            popularTvApiImp.getTvPopular(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvSeriesResult>(){
                    override fun onSuccess(t: TvSeriesResult) {
                        popularTvSeries.value = t.tvSeries
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }

    private fun getTopRatedTvSeries(){
        disposable.add(
            topRatedTvSeriesApiImp.getTopRated(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvSeriesResult>(){
                    override fun onSuccess(t: TvSeriesResult) {
                        topRatedTvSeries.value = t.tvSeries
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }


    fun combineMovies(){
        findSameMovies(trendMovies.value!!,popularMovies.value!!)
        findSameMovies(trendMovies.value!!,upcomingMovies.value!!)
        findSameMovies(upcomingMovies.value!!,popularMovies.value!!)
        findSameTvSeries(topRatedTvSeries.value!!,popularTvSeries.value!!)
        deleteSameMovies()
        movieGenreCount(combinedMovieList)
    }


   private fun movieGenreCount(allMovieList : ArrayList<Movie>){
        for (movie in allMovieList){
            if(movie.genre_ids != null) {
                for (movieID in movie.genre_ids){
                    when(movieID){
                        28-> actionMovieList.add(movie)
                        12-> adventureMovieList.add(movie)
                        16-> animationMovieList.add(movie)
                        35-> comedyMovieList.add(movie)
                        80-> crimeMovieList.add(movie)
                        99-> documentaryMovieList.add(movie)
                        18-> dramaMovieList.add(movie)
                        10751-> familyMovieList.add(movie)
                        14-> fantasyMovieList.add(movie)
                        36-> historyMovieList.add(movie)
                        27-> horrorMovieList.add(movie)
                        10402-> musicMovieList.add(movie)
                        9648-> mysteryMovieList.add(movie)
                        10749-> romanceMovieList.add(movie)
                        878-> scienceFictionMovieList.add(movie)
                        10770-> tvMovieList.add(movie)
                        53-> thrillerMovieList.add(movie)
                        10752-> warMovieList.add(movie)
                        37-> westernMovieList.add(movie)
                    }
                }
            }
        }
       allMovieListHm["All"] = allMovieList
       allMovieListHm["Action"] = actionMovieList
       allMovieListHm["Adventure"] = adventureMovieList
       allMovieListHm["Comedy"] = comedyMovieList
       allMovieListHm["Animation"] = animationMovieList
       allMovieListHm["Comedy"] = comedyMovieList
       allMovieListHm["Crime"] = crimeMovieList
       allMovieListHm["Documentary"] = documentaryMovieList
       allMovieListHm["Drama"] = dramaMovieList
       allMovieListHm["Family"] = familyMovieList
       allMovieListHm["Fantasy"] = fantasyMovieList
       allMovieListHm["History"] = historyMovieList
       allMovieListHm["Horror"] = horrorMovieList
       allMovieListHm["Music"] = musicMovieList
       allMovieListHm["Mystery"] = mysteryMovieList
       allMovieListHm["Romance"] = romanceMovieList
       allMovieListHm["Science Fiction"] = scienceFictionMovieList
       allMovieListHm["TV Movie"] = tvMovieList
       allMovieListHm["Thriller"] = thrillerMovieList
       allMovieListHm["War"] = warMovieList
       allMovieListHm["Western"] = westernMovieList

   }

    fun filter(text: String?,adapter : SearchAdapter){
        val filteredList: ArrayList<Movie> = ArrayList()
        if(text != null) {
            for (listItem in combinedMovieList) {
                if (listItem.original_title != null) {
                    if (listItem.original_title.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                        filteredList.add(listItem)
                    }
                }
            }
        }

        if (filteredList.isEmpty()) {
            movieFoundError.value = true

        } else {
            adapter.filterList(filteredList)
            movieFoundError.value = false
        }
    }

    fun getNames() : ArrayList<String>{
        val genreNames = ArrayList<String>()
        val sizeList = ArrayList<String>()
        var size : String
        genreNames.add("All")
        size = allMovieListHm["All"]!!.size.toString()
        sizeList.add("All"+"(${size})")
        for(genre in genres.value!!){
            genreNames.add(genre.name)
            if(allMovieListHm[genre.name] != null){
                size = allMovieListHm[genre.name]!!.size.toString()
                sizeList.add(genre.name+"(${size})")
            }
        }

        genreWithSize.value = sizeList
        return genreNames
    }


    fun getGenre() : HashMap<String,ArrayList<Movie>>{
        return allMovieListHm
    }
    fun clearList(){
        for (genre in getNames()) {
           allMovieListHm[genre]!!.clear()
        }
    }

    private fun findSameMovies(list1: ArrayList<Movie>,list2: ArrayList<Movie>){
        for (list1Item in list1) {
            for (list2Item in list2) {
                if (list2Item.id != null && list1Item.id != null) {
                    if (list2Item.id == list1Item.id) {
                        sameMovies.add(list2Item)
                    }
                }
            }
        }
    }

    private fun deleteSameMovies(){
        val deletedList = mutableListOf<Movie>()
        deletedList.addAll(trendMovies.value!!)
        for (movie in trendMovies.value!!){
            for (sameMovie in sameMovies.indices){
                if(movie.id == sameMovies[sameMovie].id){
                    deletedList.remove(movie)
                }
            }
        }
        val deletedList1 = mutableListOf<Movie>()
        deletedList1.addAll(popularMovies.value!!)
        for (movie in popularMovies.value!!){
            for (sameMovie in sameMovies.indices){
                if(movie.id == sameMovies[sameMovie].id){
                    deletedList1.remove(movie)
                }
            }
        }
        combinedMovieList.addAll(deletedList)
        combinedMovieList.addAll(deletedList1)
        combinedMovieList.addAll(upcomingMovies.value!!)
    }

    private fun deleteSameTvSeries(){
        val deletedList = mutableListOf<TvSeries>()
        deletedList.addAll(popularTvSeries.value!!)
        for (tvSeries in popularTvSeries.value!!){
            for (same in sameTvSeries.indices){
                if(tvSeries.id == sameTvSeries[same].id){
                    deletedList.remove(tvSeries)
                }
            }
        }
        combinedTvSeriesList.addAll(deletedList)
        combinedTvSeriesList.addAll(topRatedTvSeries.value!!)
    }

    private fun findSameTvSeries(list1: ArrayList<TvSeries>,list2: ArrayList<TvSeries>){
        for (list1Item in list1) {
            for (list2Item in list2) {
                if (list2Item.id != null && list1Item.id != null) {
                    if (list2Item.id == list1Item.id) {
                        sameTvSeries.add(list2Item)
                    }
                }
            }
        }
    }
}