package com.keremkulac.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.keremkulac.movieapp.LatestMovie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.viewmodel.MainActivityViewModel
import com.keremkulac.movieapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var popularMovieViewModel: MovieViewModel
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var posterImageView: ImageView
    private lateinit var latestMovie : LatestMovie
    private lateinit var selectedTV : View
    private lateinit var selectedMovie : View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (this as AppCompatActivity?)!!.supportActionBar!!.hide()
        viewModel = MainActivityViewModel()
        popularMovieViewModel = MovieViewModel()
        posterImageView = findViewById(R.id.latestMoviePoster)
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.menu.findItem(R.id.home).isChecked = true
        bottomNav.menu.findItem(R.id.search).isChecked = false
        selectedTV = findViewById(R.id.selectedTV)
        selectedMovie = findViewById(R.id.selectedMovie)
        setLatestPoster()
        latestPosterClick()
        bottomNavMenuSelect()
        selectMovie()
        selectTVSeries()
        replaceFragment(TvSeriesFragment(),this.supportFragmentManager,R.id.mainFrameLayout)
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
        val tv = findViewById<TextView>(R.id.latestText)
        val gradientView = findViewById<View>(R.id.gradient)
        viewModel.latestMovies.observe(this){

            if(it.backdrop_path !=null && !it.adult){
                latestMovie = it
                posterImageView.downloadFromUrl(it.backdrop_path, placeHolderProgressBar(this))
                posterImageView.visibility = View.VISIBLE
            }else{
                posterImageView.visibility = View.GONE
                tv.visibility = View.GONE
                gradientView.visibility = View.GONE

            }
            if(it.poster_path != null && !it.adult){
                latestMovie = it
                posterImageView.visibility = View.VISIBLE
                posterImageView.downloadFromUrl(it.poster_path, placeHolderProgressBar(this))
            }else{
                posterImageView.visibility = View.GONE
                tv.visibility = View.GONE
                gradientView.visibility = View.GONE
            }
        }
    }
    private fun latestPosterClick(){
        if(selectedMovie.visibility == View.VISIBLE) {
            posterImageView.setOnClickListener {
                val fragmentTransaction = this.supportFragmentManager.beginTransaction()
                val args = Bundle()
                val movieDetailFragment = MovieDetailFragment()
                args.putSerializable("latestMovie", latestMovie)
                movieDetailFragment.arguments = args
                movieDetailFragment.show(this.supportFragmentManager, "TAG")
                fragmentTransaction.commit()
            }
        }else{
            posterImageView.setOnClickListener {
                val fragmentTransaction = this.supportFragmentManager.beginTransaction()
                val args = Bundle()
                val movieDetailFragment = MovieDetailFragment()
                args.putSerializable("latestTvSeries", latestMovie)
                movieDetailFragment.arguments = args
                movieDetailFragment.show(this.supportFragmentManager, "TAG")
                fragmentTransaction.commit()
            }
        }
    }

    private fun selectTVSeries(){
        val tv = findViewById<TextView>(R.id.selectTVSeries)
        tv.setOnClickListener {
            selectedMovie.visibility = View.INVISIBLE
            selectedTV.visibility = View.VISIBLE
             replaceFragment(TvSeriesFragment(),this.supportFragmentManager,R.id.mainFrameLayout)

        }
    }
    private fun selectMovie(){
        val tv = findViewById<TextView>(R.id.selectMovie)
        tv.setOnClickListener {
            selectedTV.visibility = View.INVISIBLE
            selectedMovie.visibility = View.VISIBLE
            replaceFragment(MovieFragment(),this.supportFragmentManager,R.id.mainFrameLayout)
        }
    }
}



