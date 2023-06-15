package com.keremkulac.movieapp.ui.account.change_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keremkulac.movieapp.repository.FirebaseRepositoryImp
import com.keremkulac.movieapp.util.FirebaseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel
@Inject constructor(private val firebaseImp: FirebaseRepositoryImp) : ViewModel(){
    private val _changePasswordStatus = MutableLiveData<FirebaseResource<Boolean?>>()
    val changePasswordStatus: MutableLiveData<FirebaseResource<Boolean?>> = _changePasswordStatus
    fun changePassword(oldPass : String,newPass1 : String,newPass2 : String) {
        if (oldPass.isEmpty() || newPass1.isEmpty() || newPass2.isEmpty()) {
            _changePasswordStatus.postValue(
                FirebaseResource.Error(
                    "Please enter all information completely"))
        } else {
            if (newPass1 != newPass2) {
                _changePasswordStatus.postValue(
                    FirebaseResource.Error(
                        "New passwords are not the same"))
            } else {
                viewModelScope.launch {
                    val result = firebaseImp.changeUserPassword(firebaseImp.getCurrentUserEmail(), oldPass, newPass1)
                    _changePasswordStatus.postValue(result)
                }
            }
        }
    }
}
