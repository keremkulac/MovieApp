package com.keremkulac.movieapp.ui.account.change_password

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.keremkulac.movieapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel
@Inject constructor(private val auth : FirebaseAuth) : ViewModel(){

    fun changePassword(oldPass : String,newPass1 : String,context: Context,navController: NavController,bundle: Bundle){
        val user = auth.currentUser
        user?.let {
            if(user.email == null || user.email.equals("")){
                Toast.makeText(context,"User email is incorrect",Toast.LENGTH_SHORT).show()

            }else{
                val credential = EmailAuthProvider.getCredential(user.email.toString(),oldPass)
                user.reauthenticate(credential).addOnSuccessListener {
                    user.updatePassword(newPass1).addOnSuccessListener {
                        Toast.makeText(context,"Password updated", Toast.LENGTH_SHORT).show()
                        navController.navigate(R.id.action_changePasswordFragment_to_myMembershipFragment,bundle)
                    }.addOnFailureListener {
                        Toast.makeText(context, it.localizedMessage!!.toString(), Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}