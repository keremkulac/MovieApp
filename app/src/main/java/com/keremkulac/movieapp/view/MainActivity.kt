package com.keremkulac.movieapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ActivityMainBinding
import com.keremkulac.movieapp.viewmodel.MainActivityViewModel
import com.keremkulac.movieapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var popularMovieViewModel: MovieViewModel
    private lateinit var viewModel : MainActivityViewModel
    private lateinit var auth : FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = MainActivityViewModel()
        popularMovieViewModel = MovieViewModel()
        auth = Firebase.auth
        bottomNavMenuSelect()
        selectMovie()
        selectTVSeries()
        account()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.tvSeriesFragment)
    }

    private fun bottomNavMenuSelect(){
        binding.bottomNav.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.search->{
                    navController.navigate(R.id.searchActivity)
                    true
                }
                R.id.home-> {
                    navController.navigate(R.id.mainActivity)

                    true
                }
                R.id.logout-> {
                    auth.signOut()
                    navController.navigate(R.id.loginActivity)
                    true
                }
                else -> {false   }
            }
        }
    }

    private fun selectTVSeries(){
        binding.selectTVSeries.setOnClickListener {
            binding.selectedMovie.visibility = View.INVISIBLE
            binding.selectedTV.visibility = View.VISIBLE
            navController.navigate(R.id.action_movieFragment_to_tvSeriesFragment)
        }
    }
    private fun selectMovie(){
        binding.selectMovie.setOnClickListener {
            binding.selectedMovie.visibility = View.VISIBLE
            binding.selectedTV.visibility = View.INVISIBLE
            navController.navigate(R.id.action_tvSeriesFragment_to_movieFragment)
        }
    }

    private fun account(){
        binding.account.setOnClickListener {
            binding.bottomNav.visibility = View.GONE
            binding.toolBar.visibility = View.GONE
            navController.navigate(R.id.accountFragment)
        }
    }
}



