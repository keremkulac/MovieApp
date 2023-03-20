package com.keremkulac.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.viewmodel.MainActivityViewModel
import com.keremkulac.movieapp.viewmodel.PopularMovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var popularMovieViewModel: PopularMovieViewModel
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var posterImageView: ImageView
    private lateinit var latestMovie : LatestMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = MainActivityViewModel()
        popularMovieViewModel = PopularMovieViewModel()
        posterImageView = findViewById(R.id.latestMoviePoster)
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.menu.findItem(R.id.home).isChecked = true
        bottomNav.menu.findItem(R.id.search).isChecked = false
        setLatestPoster()
        latestPosterClick()
        replaceFragment(PopularMovieFragment(),this.supportFragmentManager,R.id.popularFrameLayout)
        replaceFragment(TrendMovieFragment(),this.supportFragmentManager,R.id.trendFrameLayout)
        bottomNavMenuSelect()
    }



    private fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager,layout :Int){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(layout,fragment)
            fragmentTransaction.commit()

        }

    private fun bottomNavMenuSelect(){
        bottomNav.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.search->{
                    val searchActivityIntent = Intent(this,SearchActivity::class.java)
                    startActivity(searchActivityIntent)
                    true
                }
                R.id.home-> {
                    val mainActivityIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainActivityIntent)
                    true
                }
                else -> {false   }
            }
        }
    }
    private fun setLatestPoster(){

        viewModel.latestMovies.observe(this){

            if(it.backdrop_path !=null && !it.adult){
                latestMovie = it
                posterImageView.downloadFromUrl(it.backdrop_path, placeHolderProgressBar(this))
                posterImageView.visibility = View.VISIBLE
            }else{
                //posterImageView.visibility = View.GONE
            }
            if(it.poster_path != null && !it.adult){
                latestMovie = it
                posterImageView.visibility = View.VISIBLE
                posterImageView.downloadFromUrl(it.poster_path, placeHolderProgressBar(this))
            }else{
           //     posterImageView.visibility = View.GONE
            }
        }
        posterImageView.downloadFromUrl("/2Eewgp7o5AU1xCataDmiIL2nYxd.jpg", placeHolderProgressBar(this))
        posterImageView.visibility = View.VISIBLE
    }
    private fun latestPosterClick(){
        posterImageView.setOnClickListener {
            val fragmentTransaction = this.supportFragmentManager.beginTransaction()
            val args = Bundle()
            val movieDetailFragment = MovieDetailFragment()
            args.putSerializable("latestMovie",latestMovie)
            movieDetailFragment.arguments = args
            movieDetailFragment.show(this.supportFragmentManager,"TAG")
            fragmentTransaction.commit()

        }
    }
}



