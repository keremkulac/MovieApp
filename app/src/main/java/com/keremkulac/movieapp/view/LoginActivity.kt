package com.keremkulac.movieapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.keremkulac.movieapp.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        replaceFragment(LoginFragment(),this.supportFragmentManager,R.id.loginFrameLayout)
    }
    private fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, layout :Int){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layout,fragment)
        fragmentTransaction.commit()

    }

}