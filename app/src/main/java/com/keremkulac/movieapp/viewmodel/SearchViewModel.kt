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
import com.keremkulac.movieapp.service.tv_series.TvSeriesGenreApiImp
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
    var movieGenres = MutableLiveData<ArrayList<Genre>>()
    var tvSeriesGenres = MutableLiveData<ArrayList<Genre>>()
    private val sameMovies = ArrayList<Movie>()
    private val sameTvSeries = ArrayList<Movie>()
    private val popularMovieAPIImp = PopularMovieApiImp()
    private val trendMovieApiImp = TrendMovieApiImp()
    private val movieGenreApiImp = MovieGenreApiImp()
    private val tvSeriesGenreApiImp = TvSeriesGenreApiImp()
    private val upcomingMovieApiImp = UpcomingMovieApiImp ()
    val topRatedTvSeries = MutableLiveData<ArrayList<Movie>>()
    val popularTvSeries = MutableLiveData<ArrayList<Movie>>()
    private val popularTvApiImp = PopularTvApiImp()
    private val topRatedTvSeriesApiImp = TopRatedTvSeriesApiImp()
    private  var combinedMovieList : ArrayList<Movie>
    private  var combinedTvSeriesList : ArrayList<Movie>
    private  var actionList = ArrayList<Movie>()
    private  var adventureList = ArrayList<Movie>()
    private  var animationList  = ArrayList<Movie>()
    private  var comedyList = ArrayList<Movie>()
    private  var crimeList = ArrayList<Movie>()
    private  var documentaryList = ArrayList<Movie>()
    private  var dramaList = ArrayList<Movie>()
    private  var familyList = ArrayList<Movie>()
    private  var fantasyList = ArrayList<Movie>()
    private  var historyList = ArrayList<Movie>()
    private  var horrorList = ArrayList<Movie>()
    private  var musicList = ArrayList<Movie>()
    private  var mysteryList = ArrayList<Movie>()
    private  var romanceList = ArrayList<Movie>()
    private  var scienceFictionList = ArrayList<Movie>()
    private  var tvMovieList = ArrayList<Movie>()
    private  var thrillerList = ArrayList<Movie>()
    private  var warList = ArrayList<Movie>()
    private  var westernList = ArrayList<Movie>()
    private  var kidsList = ArrayList<Movie>()
    private var newsList = ArrayList<Movie>()
    private var realityList = ArrayList<Movie>()
    private var sciFiList = ArrayList<Movie>()
    private var soapList = ArrayList<Movie>()
    private var talkList = ArrayList<Movie>()
    private var warAndPoliticsList = ArrayList<Movie>()
    private var actionAndAdventureList = ArrayList<Movie>()
    private var allGenres = ArrayList<Genre>()
    var allMovieListHm = HashMap<String,ArrayList<Movie>>()
    var genreWithSize = MutableLiveData<java.util.ArrayList<String>>()
    init {

        getPopularMovies()
        getTrendMovies()
        getTvSeriesGenres()
        getMovieGenres()
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

    private fun getMovieGenres(){
        disposable.add(
            movieGenreApiImp.getMovieGenre(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Genres>(){
                    override fun onSuccess(t: Genres) {
                        movieGenres.value = t.genres
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }

    private fun getTvSeriesGenres(){
        disposable.add(
            tvSeriesGenreApiImp.getTvSeriesGenre(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Genres>(){
                    override fun onSuccess(t: Genres) {
                        tvSeriesGenres.value = t.genres
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

    private fun getTopRatedTvSeries(){
        disposable.add(
            topRatedTvSeriesApiImp.getTopRated(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResult>(){
                    override fun onSuccess(t: MovieResult) {
                        topRatedTvSeries.value = t.movies
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
        deleteSameTvSeries()
        movieGenreCount(combinedMovieList)
        tvSeriesGenreCount()
        getGenreNames()
    }


   private fun movieGenreCount(allMovieList : ArrayList<Movie>){
        for (movie in allMovieList){
            movie.genre_ids.let {
                for (movieID in movie.genre_ids){
                    when(movieID){
                        28-> actionList.add(movie)
                        12-> adventureList.add(movie)
                        16-> animationList.add(movie)
                        35-> comedyList.add(movie)
                        80-> crimeList.add(movie)
                        99-> documentaryList.add(movie)
                        18-> dramaList.add(movie)
                        10751-> familyList.add(movie)
                        14-> fantasyList.add(movie)
                        36-> historyList.add(movie)
                        27-> horrorList.add(movie)
                        10402-> musicList.add(movie)
                        9648-> mysteryList.add(movie)
                        10749-> romanceList.add(movie)
                        878-> scienceFictionList.add(movie)
                        10770-> tvMovieList.add(movie)
                        53-> thrillerList.add(movie)
                        10752-> warList.add(movie)
                        37-> westernList.add(movie)
                    }
                }
            }
        }

       for (tvSeries in combinedTvSeriesList){
           tvSeries.genre_ids.let {
               for (tvSeriesID in tvSeries.genre_ids){
                   when(tvSeriesID){
                       10759-> actionAndAdventureList.add(tvSeries)
                       16-> animationList.add(tvSeries)
                       35-> comedyList.add(tvSeries)
                       80-> crimeList.add(tvSeries)
                       99-> documentaryList.add(tvSeries)
                       18-> dramaList.add(tvSeries)
                       10751-> familyList.add(tvSeries)
                       10762-> kidsList.add(tvSeries)
                       9648-> mysteryList.add(tvSeries)
                       10763-> newsList.add(tvSeries)
                       10764-> realityList.add(tvSeries)
                       10765-> sciFiList.add(tvSeries)
                       10766-> soapList.add(tvSeries)
                       10767-> talkList.add(tvSeries)
                       10768-> warAndPoliticsList.add(tvSeries)
                       37-> westernList.add(tvSeries)
                   }
               }
           }
       }
        val allList = allMovieList+ combinedTvSeriesList
       allMovieListHm["All"] = allList as ArrayList<Movie>
       allMovieListHm["Action"] = actionList
       allMovieListHm["Action & Adventure"] = actionAndAdventureList
       allMovieListHm["Adventure"] = adventureList
       allMovieListHm["Comedy"] = comedyList
       allMovieListHm["Animation"] = animationList
       allMovieListHm["Comedy"] = comedyList
       allMovieListHm["Crime"] = crimeList
       allMovieListHm["Documentary"] = documentaryList
       allMovieListHm["Drama"] = dramaList
       allMovieListHm["Family"] = familyList
       allMovieListHm["Fantasy"] = fantasyList
       allMovieListHm["History"] = historyList
       allMovieListHm["Horror"] = horrorList
       allMovieListHm["Kids"] = kidsList
       allMovieListHm["Music"] = musicList
       allMovieListHm["Mystery"] = mysteryList
       allMovieListHm["News"] = newsList
       allMovieListHm["Reality"] = realityList
       allMovieListHm["Romance"] = romanceList
       allMovieListHm["Sci-Fi & Fantasy"] = sciFiList
       allMovieListHm["Science Fiction"] = scienceFictionList
       allMovieListHm["Soap"] = soapList
       allMovieListHm["Talk"] = talkList
       allMovieListHm["TV Movie"] = tvMovieList
       allMovieListHm["Thriller"] = thrillerList
       allMovieListHm["War"] = warList
       allMovieListHm["War & Politics"] = warAndPoliticsList
       allMovieListHm["Western"] = westernList

   }

    private fun tvSeriesGenreCount(){
        val deletedList = mutableListOf<Genre>()
        val list = ArrayList<Genre>()
        deletedList.addAll(tvSeriesGenres.value!!)
        for(tvSeriesGenres in tvSeriesGenres.value!!){
            for(movieGenres in movieGenres.value!!){
                if(tvSeriesGenres.id == movieGenres.id){
                    deletedList.remove(tvSeriesGenres)
                    list.add(tvSeriesGenres)
                }
            }
        }
    }
    fun filter(text: String?,adapter : SearchAdapter){
        val allList = combinedMovieList + combinedTvSeriesList
        val filteredList: ArrayList<Movie> = ArrayList()
        if(text != null) {
            for (listItem in allList) {
                if (listItem.original_title != null) {
                    if (listItem.original_title.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                        filteredList.add(listItem)
                    }
                }else{
                    if(listItem.name != null) {
                        if (listItem.name.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                            filteredList.add(listItem)
                        }
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
        for(genre in allGenres){
            genreNames.add(genre.name)
            if(allMovieListHm[genre.name] != null){
                size = allMovieListHm[genre.name]!!.size.toString()
                sizeList.add(genre.name+"(${size})")
            }
        }


        sizeList.sortBy {
            it
        }
        val list = ArrayList<String>()
        size = allMovieListHm["All"]!!.size.toString()
        list.add("All"+"(${size})")
        val list1 = list+sizeList
        genreWithSize.value = list1 as ArrayList<String>
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

    private fun getGenreNames(){
        val sameGenres = mutableListOf<Genre>()
        val deletedList = mutableListOf<Genre>()
        deletedList.addAll(movieGenres.value!!)
        for (i in movieGenres.value!!){
            for(x in tvSeriesGenres.value!!){
                if(i.id == x.id){
                    sameGenres.add(i)
                }
            }
        }
        for (genre in movieGenres.value!!){
            for (sameGenre in sameGenres.indices){
                if(genre.id == sameGenres[sameGenre].id){
                    deletedList.remove(genre)
                }
            }
        }
        allGenres.addAll(deletedList)
        allGenres.addAll(tvSeriesGenres.value!!)
    }
    private fun findSameMovies(list1: ArrayList<Movie>,list2: ArrayList<Movie>){
        for (list1Item in list1) {
            for (list2Item in list2) {
                list2Item.id.let {
                    list1Item.id.let {
                        if (list2Item.id == list1Item.id) {
                            sameMovies.add(list2Item)
                        }
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
        val deletedList = mutableListOf<Movie>()
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

    private fun findSameTvSeries(list1: ArrayList<Movie>,list2: ArrayList<Movie>){
        for (list1Item in list1) {
            for (list2Item in list2) {
                list2Item.id.let {
                    list1Item.id.let {
                        if (list2Item.id == list1Item.id) {
                            sameTvSeries.add(list2Item)
                        }
                    }
                }
            }
        }
    }
}