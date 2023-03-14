package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.MovieResult
import com.keremkulac.movieapp.adapter.SearchAdapter
import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.service.MovieGenreApiImp
import com.keremkulac.movieapp.service.PopularMovieApiImp
import com.keremkulac.movieapp.service.TrendMovieApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel(){

    private val disposable = CompositeDisposable()
    var popularMovies = MutableLiveData<ArrayList<Movie>>()
    var trendMovies = MutableLiveData<ArrayList<Movie>>()
    var genres = MutableLiveData<ArrayList<Genre>>()
    private val popularMovieAPIImp = PopularMovieApiImp()
    private val trendMovieApiImp = TrendMovieApiImp()
    private val movieGenreApiImp = MovieGenreApiImp()
    private  var combinedList : ArrayList<Movie>
    //  var allMovies = MutableLiveData<ArrayList<Movie>>()
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

    init {

        getPopularMovies()
        getTrendMovies()
        getGenres()
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


    fun combineMovies(){
        //Log.d("TAG1","GİRDİ")
        var sameMovies = ArrayList<Movie>()
            for (trendMovieItems in trendMovies.value!!) {
                for (popularMovieItems in popularMovies.value!!) {
                    if (popularMovieItems.id != null && trendMovieItems.id != null) {
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
         movieGenreCount(combinedList)
    }


   private fun movieGenreCount(allMovieList : ArrayList<Movie>){
        for (movie in allMovieList){
            if(movie.genre_ids != null) {
                for (item in movie.genre_ids){
                    when(item){
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
       allMovieListHm["Mystery"] = mysteryMovieList
       allMovieListHm["Romance"] = romanceMovieList
       allMovieListHm["Science Fiction"] = scienceFictionMovieList
       allMovieListHm["TV Movie"] = tvMovieList
       allMovieListHm["Thriller"] = thrillerMovieList
       allMovieListHm["War"] = warMovieList
       allMovieListHm["Western"] = westernMovieList

     //  allMovieListHm.clear()
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

    fun setViewPager(viewPager: ViewPager, tabLayout: TabLayout, fragmentManager: FragmentManager, viewLifecycleOwner: LifecycleOwner){
       // val adapter = SearchViewPagerAdapter(fragmentManager)

       // adapter.addFragment(SearchFragment(combinedList),"All(${combinedList.size})")
        //adapter.addFragment(SearchFragment(actionMovieList.value!!),"Action")
        //adapter.addFragment(SearchFragment(adventureMovieList.value!!),"Adventure")
       // adapter.addFragment(SearchFragment(animationMovieList.value!!),"Animation")
       // adapter.addFragment(SearchFragment(comedyMovieList.value!!),"Comedy")
        //   adapter.addFragment(SearchFragment(crimeMovieList.value!!),"Crime")
        /*
        adapter.addFragment(SearchFragment(list),"Documentary")
        adapter.addFragment(SearchFragment(list),"Drama")
        adapter.addFragment(SearchFragment(list),"Family")
        adapter.addFragment(SearchFragment(list),"Fantasy")
        adapter.addFragment(SearchFragment(list),"History")
        adapter.addFragment(SearchFragment(list),"Horror")
        adapter.addFragment(SearchFragment(list),"Music")
        adapter.addFragment(SearchFragment(list),"Mystery")
        adapter.addFragment(SearchFragment(list),"Romance")
        adapter.addFragment(SearchFragment(list),"Science Fiction")
        adapter.addFragment(SearchFragment(list),"TV Movie")
        adapter.addFragment(SearchFragment(list),"Thriller")
        adapter.addFragment(SearchFragment(list),"War")
        adapter.addFragment(SearchFragment(list),"Western")
         */
       // viewPager.adapter = adapter
      //  viewPager.offscreenPageLimit = 7
       // tabLayout.setupWithViewPager(viewPager)

    }

    fun getNames() : ArrayList<String>{
        val genreNames = ArrayList<String>()
        for(item in genres.value!!){
            genreNames.add(item.name)
        }
        return genreNames
    }


    fun getGenre() : HashMap<String,ArrayList<Movie>>{
        return allMovieListHm
    }


}