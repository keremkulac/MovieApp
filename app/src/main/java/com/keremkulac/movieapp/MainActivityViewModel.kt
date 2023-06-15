package com.keremkulac.movieapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keremkulac.movieapp.repository.FirebaseRepositoryImp
import com.keremkulac.movieapp.repository.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor( private val firebaseRepositoryImp: FirebaseRepositoryImp) : ViewModel(){
    private val _user = MutableLiveData<User?>()
    val user : MutableLiveData<User?> = _user

    init {
        viewModelScope.launch{
             _user.value = firebaseRepositoryImp.getUserFromFirebase(
                 firebaseRepositoryImp.getCurrentUserUid())
        }
    }
}