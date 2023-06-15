package com.keremkulac.movieapp.ui.account.my_membership

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.keremkulac.movieapp.repository.FirebaseRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMembershipViewModel
@Inject constructor(private val firebaseRepositoryImp: FirebaseRepositoryImp) : ViewModel(){

    fun updateFullname(userFullname : String){
        val user = userFullname.split(" ")
        viewModelScope.launch(Dispatchers.Main) {
            val userUid = firebaseRepositoryImp.getCurrentUser()?.uid
            userUid?.let {
                firebaseRepositoryImp.updateUser(
                    user[0].capitalize(),
                    user[1].capitalize(),
                    it
                )
            }
        }
    }



}