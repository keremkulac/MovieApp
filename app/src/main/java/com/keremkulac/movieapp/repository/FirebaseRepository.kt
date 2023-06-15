package com.keremkulac.movieapp.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.repository.model.User
import com.keremkulac.movieapp.util.FirebaseResource


interface FirebaseRepository {

    suspend fun getUserFromFirebase(uid: String) : User

    suspend fun getCurrentUser() : FirebaseUser?

    suspend fun getCurrentUserEmail() : String

    suspend fun getCurrentUserUid(): String

    suspend fun userRegister(hashMap: HashMap<String, Any>, email: String,password: String) : FirebaseResource<AuthResult>

    suspend fun userLogin(email: String,password: String) : FirebaseResource<AuthResult>

    suspend fun updateUser(userFirstname : String, userLastname : String,userUid : String)

    suspend fun changeUserPassword(email : String ,oldPass : String,newPass : String) : FirebaseResource<Boolean?>

    suspend fun getUserFavoriteList(email : String) : FirebaseResource<ArrayList<Movie>>
}