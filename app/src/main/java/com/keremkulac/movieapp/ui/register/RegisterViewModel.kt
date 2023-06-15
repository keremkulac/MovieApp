package com.keremkulac.movieapp.ui.register

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.AuthResult
import com.keremkulac.movieapp.repository.FirebaseRepositoryImp
import com.keremkulac.movieapp.util.FirebaseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseRepositoryImp: FirebaseRepositoryImp): ViewModel() {
    private val _userRegistrationStatus = MutableLiveData<FirebaseResource<AuthResult>>()
    val userRegistrationStatus: MutableLiveData<FirebaseResource<AuthResult>> = _userRegistrationStatus

    fun createUser(userFirstname : String,userLastname : String,userEmail: String,password : String) {
        val hm = HashMap<String, Any>()
        var error =
            if (userFirstname.isEmpty() || userLastname.isEmpty() || userEmail.isEmpty() || password.isEmpty()) {
                "Please enter all information completely"
            } else null

        error?.let {
            _userRegistrationStatus.postValue(FirebaseResource.Error(it))
            return
        }
        hm["userEmail"] = userEmail
        hm["userFirstname"] = userFirstname
        hm["userLastname"] = userLastname
        _userRegistrationStatus.postValue(FirebaseResource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            val result = firebaseRepositoryImp.userRegister(hm,userEmail,password)
            _userRegistrationStatus.postValue(result)
        }
    }

}