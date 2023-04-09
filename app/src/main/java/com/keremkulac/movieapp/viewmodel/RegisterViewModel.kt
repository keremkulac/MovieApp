package com.keremkulac.movieapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.model.User
import com.keremkulac.movieapp.util.replaceFragment
import com.keremkulac.movieapp.view.LoginFragment

class RegisterViewModel : ViewModel() {

    fun saveUserFromFirebase(user : User?,fragmentManager: FragmentManager,context: Context){
        val firestore = FirebaseFirestore.getInstance()
        val hm = HashMap<String,Any>()
        hm["userEmail"] = user!!.email
        hm["userPassword"] = user!!.password
        firestore.collection("Users").document(user.email)
            .set(hm)
            .addOnSuccessListener {
                saveUser(user,context)
                replaceFragment(LoginFragment(),fragmentManager, R.id.loginFrameLayout)
            }
    }
    private fun saveUser(user : User?,context: Context){
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(user!!.email,user!!.password).addOnSuccessListener {
            Toast.makeText(context,"Registration Successful",Toast.LENGTH_SHORT).show()
        }
    }
}