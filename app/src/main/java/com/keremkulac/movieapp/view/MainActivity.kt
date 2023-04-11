package com.keremkulac.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.util.replaceFragment
import com.keremkulac.movieapp.viewmodel.MainActivityViewModel
import com.keremkulac.movieapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var popularMovieViewModel: MovieViewModel
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var posterImageView: ImageView
    private var popularMovie : Movie? = null
    private  var popularTvSeries : Movie? = null
    private lateinit var selectedTV : View
    private lateinit var selectedMovie : View
    private var randomNumber : Int = 0
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  (this as AppCompatActivity?)!!.supportActionBar!!.hide()
        viewModel = MainActivityViewModel()
        popularMovieViewModel = MovieViewModel()
        posterImageView = findViewById(R.id.latestMoviePoster)
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.menu.findItem(R.id.home).isChecked = true
        bottomNav.menu.findItem(R.id.search).isChecked = false
        selectedTV = findViewById(R.id.selectedTV)
        selectedMovie = findViewById(R.id.selectedMovie)
        auth = Firebase.auth
        setPopularPoster()
        popularPosterClick()
        bottomNavMenuSelect()
        selectMovie()
        selectTVSeries()
        account()

        replaceFragment(TvSeriesFragment(),this.supportFragmentManager,R.id.mainFrameLayout)
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
                R.id.logout-> {
                    auth.signOut()
                    val loginActivity = Intent(this, LoginActivity::class.java)
                    startActivity(loginActivity)
                  // finish()
                    true
                }
                else -> {false   }
            }
        }
    }
    private fun setPopularPoster(){
        if(selectedMovie.visibility == View.VISIBLE) {
            viewModel.popularMovies.observe(this) {
                randomNumber = kotlin.random.Random.nextInt(0, it.size)
                val popularMovie = it[randomNumber]
                this.popularMovie = popularMovie
                if (popularMovie.backdrop_path != null && !popularMovie.adult) {
                    posterImageView.downloadFromUrl(
                        popularMovie.backdrop_path,
                        placeHolderProgressBar(this)
                    )
                    posterImageView.visibility = View.VISIBLE
                }
            }
        }
        if(selectedTV.visibility == View.VISIBLE) {
            viewModel.popularTvSeries.observe(this) {
                randomNumber = kotlin.random.Random.nextInt(0, it.size)
                val popularTvSeries = it[randomNumber]
                this.popularTvSeries = popularTvSeries
                if (popularTvSeries.poster_path != null && !popularTvSeries.adult) {
                    posterImageView.visibility = View.VISIBLE
                    posterImageView.downloadFromUrl(
                        popularTvSeries.poster_path,
                        placeHolderProgressBar(this)
                    )
                }
            }
        }
    }
    private fun popularPosterClick(){
            posterImageView.setOnClickListener {
                val fragmentTransaction = this.supportFragmentManager.beginTransaction()
                val args = Bundle()
                val movieDetailFragment = MovieDetailFragment()
                if(selectedMovie.visibility == View.VISIBLE) {
                    args.putSerializable("movie", popularMovie)
                }
                if(selectedTV.visibility == View.VISIBLE) {
                    args.putSerializable("tvSeries", popularTvSeries)
                }
                movieDetailFragment.arguments = args
                movieDetailFragment.show(this.supportFragmentManager, "TAG")
                fragmentTransaction.commit()
            }

    }

    private fun selectTVSeries(){
        val tv = findViewById<TextView>(R.id.selectTVSeries)
        tv.setOnClickListener {
            selectedMovie.visibility = View.INVISIBLE
            selectedTV.visibility = View.VISIBLE
             replaceFragment(TvSeriesFragment(),this.supportFragmentManager,R.id.mainFrameLayout)
            setPopularPoster()

        }
    }
    private fun selectMovie(){
        val tv = findViewById<TextView>(R.id.selectMovie)
        tv.setOnClickListener {
            selectedMovie.visibility = View.VISIBLE
            selectedTV.visibility = View.INVISIBLE
            replaceFragment(MovieFragment(),this.supportFragmentManager,R.id.mainFrameLayout)
            setPopularPoster()
        }
    }

    private fun account(){
        val accountTextView = findViewById<TextView>(R.id.account)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        val imageView = findViewById<ImageView>(R.id.latestMoviePoster)
        val view = findViewById<View>(R.id.gradient)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolBar)
        val frameLayout = findViewById<FrameLayout>(R.id.mainFrameLayout)
        val accountFrameLayout = findViewById<FrameLayout>(R.id.accountFrameLayout)

        accountTextView.setOnClickListener {
            accountFrameLayout.visibility = View.VISIBLE
            bottomNav.visibility = View.GONE
            imageView.visibility = View.GONE
            view.visibility = View.GONE
            toolbar.visibility = View.GONE
            frameLayout.visibility = View.GONE
            replaceFragment(AccountFragment(),this.supportFragmentManager,R.id.accountFrameLayout)
        }
    }


}



