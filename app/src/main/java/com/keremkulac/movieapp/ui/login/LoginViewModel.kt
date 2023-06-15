package com.keremkulac.movieapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.keremkulac.movieapp.repository.FirebaseRepositoryImp
import com.keremkulac.movieapp.util.FirebaseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor( private val firebaseRepositoryImp: FirebaseRepositoryImp) : ViewModel() {

    private val _userSignInStatus = MutableLiveData<FirebaseResource<AuthResult>>()
    val userSignInStatus : MutableLiveData<FirebaseResource<AuthResult>> = _userSignInStatus
    private val _isUserLogged = MutableLiveData<Boolean>()
    val isUserLogged : MutableLiveData<Boolean> = _isUserLogged
    private var currentUser : FirebaseUser? = null

    init {
        viewModelScope.launch{
            currentUser = firebaseRepositoryImp.getCurrentUser()
            _isUserLogged.value = currentUser != null
        }
    }
    fun signInUser(userEmailAddress: String, userLoginPassword: String) {
        if (userEmailAddress.isEmpty() || userLoginPassword.isEmpty()) {
            _userSignInStatus.postValue(FirebaseResource.Error(
                "Please enter all information completely"))
        } else {
            _userSignInStatus.postValue(FirebaseResource.Loading())
            viewModelScope.launch(Dispatchers.Main) {
                val loginResult = firebaseRepositoryImp.userLogin(userEmailAddress,userLoginPassword)
                _userSignInStatus.postValue(loginResult)
            }
        }
    }

}