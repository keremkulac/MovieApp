package com.keremkulac.movieapp

import androidx.fragment.app.Fragment
import com.keremkulac.movieapp.adapter.*
import com.keremkulac.movieapp.ui.account.my_list.MyListFragment
import com.keremkulac.movieapp.ui.movie.MovieFragment
import com.keremkulac.movieapp.ui.search.SearchFragment
import com.keremkulac.movieapp.ui.tv_series.TvSeriesFragment
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val myListAdapter: MyListAdapter,
    private val popularMovieAdapter: MovieAdapter,
    private val upcomingAdapter: MovieAdapter,
    private val trendAdapter: MovieAdapter,
    private val popularTvSeriesAdapter : TvSeriesAdapter,
    private val topRatedTvSeriesAdapter : TvSeriesAdapter,
    private val searchAdapter: SearchAdapter,
    private val genreAdapter : SearchGenreAdapter
) : androidx.fragment.app.FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            SearchFragment::class.java.name -> SearchFragment(searchAdapter,genreAdapter)
            MyListFragment::class.java.name -> MyListFragment(myListAdapter)
            MovieFragment::class.java.name -> MovieFragment(popularMovieAdapter,upcomingAdapter,trendAdapter)
            TvSeriesFragment::class.java.name -> TvSeriesFragment(popularTvSeriesAdapter,topRatedTvSeriesAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}