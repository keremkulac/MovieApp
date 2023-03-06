package com.keremkulac.movieapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.keremkulac.movieapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(PopularMovieFragment(),this.supportFragmentManager,R.id.popularTrendFrameLayout)
        replaceFragment(TrendMovieFragment(),this.supportFragmentManager,R.id.trendTrendFrameLayout)
    }

    private fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager,layout :Int){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(layout,fragment)
            fragmentTransaction.commit()
        }
    }



