package com.keremkulac.movieapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.model.User

class RegisterViewModel : ViewModel() {
    private lateinit var navController : NavController


   private fun saveUserFromFirebase(user : User?,fragmentManager: FragmentManager){
       val navHostFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
       navController = navHostFragment.navController
        val firestore = FirebaseFirestore.getInstance()
        val hm = HashMap<String,Any>()
       user?.let {
           hm["userEmail"] = user.email
           hm["userPassword"] = user.password
           firestore.collection("Users").document(user.email)
               .set(hm)
               .addOnSuccessListener {
                   //replaceFragment(LoginFragment(),fragmentManager, R.id.loginFrameLayout)
                   navController.navigate(R.id.loginFragment)
               }
       }

    }
    fun saveUser(user : User?,context: Context,fragmentManager: FragmentManager){
        val auth = Firebase.auth
        user?.let {
            auth.createUserWithEmailAndPassword(user.email,user.password).addOnSuccessListener {
                Toast.makeText(context,"Registration Successful",Toast.LENGTH_SHORT).show()
                saveUserFromFirebase(user, fragmentManager)
            }.addOnFailureListener {
                Toast.makeText(context, it.localizedMessage!!.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }
}