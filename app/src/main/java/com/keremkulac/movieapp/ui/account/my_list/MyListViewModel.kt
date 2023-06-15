package com.keremkulac.movieapp.ui.account.my_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.repository.FirebaseRepositoryImp
import com.keremkulac.movieapp.util.FirebaseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(private val firebaseRepoImp: FirebaseRepositoryImp) : ViewModel() {
    private val _myList = MutableLiveData<FirebaseResource<ArrayList<Movie>>>()
    val myList : MutableLiveData<FirebaseResource<ArrayList<Movie>>> = _myList
    init {
        viewModelScope.launch {
            val result = firebaseRepoImp.getUserFavoriteList(firebaseRepoImp.getCurrentUser()?.email!!)
            _myList.postValue(result)
        }
    }

}