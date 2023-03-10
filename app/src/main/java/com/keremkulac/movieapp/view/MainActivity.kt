package com.keremkulac.movieapp.view

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.adapter.PopularMovieAdapter
import com.keremkulac.movieapp.viewmodel.PopularMovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var popularMovieViewModel: PopularMovieViewModel
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popularMovieViewModel = PopularMovieViewModel()
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
       // get()
        replaceFragment(PopularMovieFragment(),this.supportFragmentManager,R.id.popularTrendFrameLayout)
        replaceFragment(TrendMovieFragment(),this.supportFragmentManager,R.id.trendTrendFrameLayout)
       // setSearchMenu()
        bottomNavMenuSelect()
    }

    private fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager,layout :Int){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(layout,fragment)
            fragmentTransaction.commit()

        }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search,menu)
        val search = menu!!.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
          //      popularMovieViewModel.filter(newText,list,adapter)
                popularMovieViewModel.popularMovies.observe(this@MainActivity, Observer {

                    val adapter = PopularMovieAdapter(this@MainActivity,it)
                    popularMovieViewModel.filter(newText,it,adapter)
                })
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

 */

    private fun bottomNavMenuSelect(){
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.search->{
                    val pfl = findViewById<FrameLayout>(R.id.popularTrendFrameLayout)
                    val tfl = findViewById<FrameLayout>(R.id.trendTrendFrameLayout)
                    tfl.visibility= View.INVISIBLE
                    pfl.visibility= View.INVISIBLE
                    replaceFragment(SearchFragment(),this.supportFragmentManager,R.id.searchFrameLayout)
                    true
                }
                R.id.home-> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {false   }
            }
        }
    }


}



