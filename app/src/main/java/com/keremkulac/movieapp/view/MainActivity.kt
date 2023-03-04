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
        replaceFragment(MovieFragment(),this.supportFragmentManager)
    }

    private fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_frame_layout,fragment)
            fragmentTransaction.commit()
        }
    }



