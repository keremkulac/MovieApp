package com.keremkulac.movieapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.R

class SearchActivity : AppCompatActivity() {
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.menu.findItem(R.id.search).isChecked = true
        bottomNav.menu.findItem(R.id.home).isChecked = false
        auth = Firebase.auth
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment3) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.searchFragment)
        bottomNavMenuSelect()
    }



    private fun bottomNavMenuSelect(){
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.search->{
                    navController.navigate(R.id.searchFragment)
                    true
                }
                R.id.home-> {
                    navController.navigate(R.id.mainActivity)
                    true
                }
                R.id.logout->{
                    auth.signOut()
                    navController.navigate(R.id.loginActivity)
                    true
                }
                else -> {false   }
            }
        }
    }

}