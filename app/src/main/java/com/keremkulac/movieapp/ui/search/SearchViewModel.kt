package com.keremkulac.movieapp.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.adapter.SearchAdapter
import com.keremkulac.movieapp.repository.MovieRepositoryImp
import com.keremkulac.movieapp.repository.model.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@HiltViewModel
class SearchViewModel
@Inject constructor(private val movieRepositoryImp: MovieRepositoryImp ): ViewModel(){
    val movieFoundError = MutableLiveData<Boolean>()
    private val popularMovies = MutableLiveData<ArrayList<Movie>>()
    private val trendMovies = MutableLiveData<ArrayList<Movie>>()
    private val upcomingMovies = MutableLiveData<ArrayList<Movie>>()
    private val topRatedTvSeries = MutableLiveData<ArrayList<Movie>>()
    private val popularTvSeries = MutableLiveData<ArrayList<Movie>>()
    private var movieGenres = MutableLiveData<ArrayList<Genre>>()
    private var tvSeriesGenres = MutableLiveData<ArrayList<Genre>>()
    private val sameMovies = ArrayList<Movie>()
    private val sameTvSeries = ArrayList<Movie>()
    private  var combinedMovieList = ArrayList<Movie>()
    private  var combinedTvSeriesList = ArrayList<Movie>()
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
    private var combinedGenreList = ArrayList<Genre>()
    var allMovieListHm = HashMap<String,ArrayList<Movie>>()
    var genreWithSize = MutableLiveData<ArrayList<String>>()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.localizedMessage?.let { Log.d("TAG", it) }
    }
    init {

    }

    fun getData(){
        getPopularMovies()
        getTrendMovies()
        getTvSeriesGenres()
        getMovieGenres()
        getUpcomingMovies()
        getTopRatedTvSeries()
        getPopularTvSeries()
    }

     private fun getPopularMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getPopularMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    popularMovies.postValue(it.movies)
                }
            }
        }
    }

    private fun getTrendMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTrendMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    trendMovies.postValue(it.movies)
                }
            }
        }
    }
    private fun getUpcomingMovies(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getUpcomingMovies()
            if(result.isSuccessful){
                result.body()?.let {
                    upcomingMovies.postValue(it.movies)
                }
            }
        }
    }

     private fun getMovieGenres(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getMovieGenre()
            if(result.isSuccessful){
                result.body()?.let {
                    movieGenres.postValue(it.genres)
                }
            }
        }
    }

    private fun getTvSeriesGenres(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTvSeriesGenre()
            if(result.isSuccessful){
                result.body()?.let {
                    tvSeriesGenres.postValue(it.genres)
                }
            }
        }
    }

    private fun getPopularTvSeries(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTvPopular()
            if(result.isSuccessful){
                result.body()?.let {
                    popularTvSeries.postValue(it.movies)
                }
            }
        }
    }

    private fun getTopRatedTvSeries(){
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val result = movieRepositoryImp.getTopRated()
            if(result.isSuccessful){
                result.body()?.let {
                    topRatedTvSeries.postValue(it.movies)
                }
            }
        }
    }


    fun combineMovies(){
        findSameMovies(trendMovies.value!!,popularMovies.value!!,"movie")
        findSameMovies(trendMovies.value!!,upcomingMovies.value!!,"movie")
        findSameMovies(upcomingMovies.value!!,popularMovies.value!!,"movie")
        findSameMovies(topRatedTvSeries.value!!,popularTvSeries.value!!,"tvSeries")
        combinedTvSeriesList.addAll(deleteSameMovies(popularTvSeries.value!!,sameTvSeries))
        combinedTvSeriesList.addAll(topRatedTvSeries.value!!)
        combinedMovieList.addAll(deleteSameMovies(trendMovies.value!!,sameMovies))
        combinedMovieList.addAll(deleteSameMovies(popularMovies.value!!,sameMovies))
        combinedMovieList.addAll(upcomingMovies.value!!)
        movieGenreCount()
        tvSeriesGenreCount()
        getGenreNames()
    }


   private fun movieGenreCount(){
        for (movie in combinedMovieList){
            movie.genre_ids.let {
                for (movieID in movie.genre_ids!!){
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
               for (tvSeriesID in tvSeries.genre_ids!!){
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
        val allList = combinedMovieList + combinedTvSeriesList
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

    fun getNames() : ArrayList<String>{
        val genreNames = ArrayList<String>()
        val sizeList = ArrayList<String>()
        var size : String
        for(genre in combinedGenreList){
            genre.name?.let { genreNames.add(it) }
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
        allMovieListHm["All"]!!.clear()
    }

    private fun getGenreNames(){
        val sameGenres = ArrayList<Genre>()
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
        combinedGenreList.addAll(deletedList)
        combinedGenreList.addAll(tvSeriesGenres.value!!)
    }
    private fun findSameMovies(list1: ArrayList<Movie>,list2: ArrayList<Movie>,isMovie : String){
        for (list1Item in list1) {
            for (list2Item in list2) {
                list2Item.id.let {
                    list1Item.id.let {
                        if (list2Item.id == list1Item.id) {
                            if(isMovie == "movie"){
                                sameMovies.add(list2Item)
                            }else{
                                sameTvSeries.add(list2Item)
                            }
                        }
                    }
                }
            }
        }
    }

   private fun deleteSameMovies(list : ArrayList<Movie>,sameList : ArrayList<Movie>) : ArrayList<Movie>{
        val deletedList = mutableListOf<Movie>()
            deletedList.addAll(list)
            for (listItem in list){
                for (same in sameList.indices){
                    if(listItem.id == sameList[same].id){
                        deletedList.remove(listItem)
                    }
                }
            }
            return deletedList as ArrayList<Movie>
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
}