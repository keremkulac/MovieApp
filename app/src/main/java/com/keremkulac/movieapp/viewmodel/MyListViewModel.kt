package com.keremkulac.movieapp.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyListViewModel : ViewModel() {
    val myList = MutableLiveData<ArrayList<QueryDocumentSnapshot>>()
    private var list = ArrayList<QueryDocumentSnapshot>()
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
                    list.add(document)
                }
                myList.value = list

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

    }
}