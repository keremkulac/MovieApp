package com.keremkulac.movieapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.keremkulac.movieapp.repository.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject constructor(val auth : FirebaseAuth, private val db : FirebaseFirestore) : ViewModel() {
    private val currentUser = auth.currentUser
    val user = MutableLiveData<User>()
    init {
        getUserFullname()
    }
    private fun getUserFullname(){
        currentUser?.let {
            val docRef = db.collection("Users").document(currentUser.email.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        document.data.let {
                            user.value = User(
                                document.data!!["userEmail"].toString(),
                                document.data!!["userFirstname"].toString(),
                                document.data!!["userLastname"].toString())
                        }
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }
        }

    }
}