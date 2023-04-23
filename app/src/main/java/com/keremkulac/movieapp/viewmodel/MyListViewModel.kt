package com.keremkulac.movieapp.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.Movie

class MyListViewModel : ViewModel() {
    val myList = MutableLiveData<ArrayList<Movie>>()
    private var mylist1 = ArrayList<Movie>()
    init {
        getMyList()
    }
    private fun getMyList(){
        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser

        db.collection("MyList")
            .whereEqualTo("email",user!!.email.toString() )
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val movie: Movie = document.toObject(Movie::class.java)
                    mylist1.add(movie)
                }
                myList.value = mylist1
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

    }
}