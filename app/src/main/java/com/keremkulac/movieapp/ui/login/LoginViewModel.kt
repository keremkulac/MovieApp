package com.keremkulac.movieapp.ui.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.keremkulac.movieapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth : FirebaseAuth) : ViewModel() {

    fun signIn(email : String, password : String,navController: NavController,context: Context){
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                navController.navigate(R.id.mainActivity)
            }.addOnFailureListener{
                Toast.makeText(context, it.localizedMessage!!.toString(), Toast.LENGTH_SHORT).show()
            }
    }

     fun loggedUser(navController: NavController){
        if(auth.currentUser != null){
            navController.navigate(R.id.mainActivity)
        }
    }
}