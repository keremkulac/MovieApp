package com.keremkulac.movieapp.ui.account.my_list

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.keremkulac.movieapp.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(private val db : FirebaseFirestore,private val auth: FirebaseAuth) : ViewModel() {
    val myList = MutableLiveData<ArrayList<Movie>>()
    private val mylist1 = ArrayList<Movie>()
    init {
        getMyList()
    }
    private fun getMyList(){
        val user = auth.currentUser
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