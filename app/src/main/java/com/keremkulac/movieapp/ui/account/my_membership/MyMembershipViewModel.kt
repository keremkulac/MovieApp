package com.keremkulac.movieapp.ui.account.my_membership

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyMembershipViewModel
@Inject constructor(val auth : FirebaseAuth, private val db : FirebaseFirestore) : ViewModel(){
        private var userEmail : String? = null
    fun updateFullname(userFullname : String){
        val user = userFullname.split(" ")
        auth?.let {
             userEmail = auth.currentUser?.email
        }
        userEmail?.let { email ->
            db.collection("Users").document(email).update(
                "userFirstname", user[0].capitalize(),
                "userLastname",user[1].capitalize())
        }

    }

}