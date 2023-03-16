package com.keremkulac.movieapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.keremkulac.movieapp.R

class SearchActivity : AppCompatActivity() {
    private lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.menu.findItem(R.id.search).isChecked = true
        bottomNav.menu.findItem(R.id.home).isChecked = false

        bottomNavMenuSelect()
        replaceFragment(SearchFragment(),supportFragmentManager,R.id.searchFrameLayout)
    }



    private fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, layout :Int){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layout,fragment)
        fragmentTransaction.commit()
    }

    private fun bottomNavMenuSelect(){
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.search->{
                    val searchActivityIntent = Intent(this, SearchActivity::class.java)
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