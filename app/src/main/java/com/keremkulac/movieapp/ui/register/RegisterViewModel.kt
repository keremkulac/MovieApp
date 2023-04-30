package com.keremkulac.movieapp.ui.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.repository.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firestore: FirebaseFirestore
,private val auth : FirebaseAuth): ViewModel() {


   private fun saveUserFromFirebase(user : User?, navController: NavController){
       val hm = HashMap<String,Any>()
        user?.let {
           hm["userEmail"] = user.email.toString()
            hm["userFirstname"] = user.firstname.toString()
            hm["userLastname"] = user.lastname.toString()

            firestore.collection("Users").document(user.email.toString())
               .set(hm)
               .addOnSuccessListener {
                   navController.navigate(R.id.loginFragment)
               }
       }
    }
    fun saveUser(user : User?,password: String, context: Context,navController: NavController){
        user?.let {
            auth.createUserWithEmailAndPassword(user.email.toString(),password).addOnSuccessListener {
                Toast.makeText(context,"Registration Successful",Toast.LENGTH_SHORT).show()
                saveUserFromFirebase(user,navController)
            }.addOnFailureListener {
                Toast.makeText(context, it.localizedMessage!!.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }
}