package com.keremkulac.movieapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.viewmodel.PopularMovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var popularMovieViewModel: PopularMovieViewModel
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popularMovieViewModel = PopularMovieViewModel()
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.menu.findItem(R.id.home).isChecked = true
        replaceFragment(PopularMovieFragment(),this.supportFragmentManager,R.id.popularTrendFrameLayout)
        replaceFragment(TrendMovieFragment(),this.supportFragmentManager,R.id.trendTrendFrameLayout)
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
}



