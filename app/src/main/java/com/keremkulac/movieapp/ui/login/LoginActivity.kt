package com.keremkulac.movieapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
    }



}