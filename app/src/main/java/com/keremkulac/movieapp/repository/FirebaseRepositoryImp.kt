package com.keremkulac.movieapp.repository


import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.repository.model.User
import com.keremkulac.movieapp.util.FirebaseResource
import com.keremkulac.movieapp.util.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepositoryImp @Inject constructor(
    private val fb: FirebaseFirestore,
    private val auth : FirebaseAuth
) : FirebaseRepository {

    override suspend fun getUserFromFirebase(uid: String): User {
        val document = fb.collection("Users").document(uid).get().await()
       return User(document.data!!["userEmail"].toString(),document.data!!["userFirstname"].toString(),document.data!!["userLastname"].toString())
    }

    override suspend fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun getCurrentUserUid(): String {
        return auth.currentUser?.uid.toString()
    }

    override suspend fun getCurrentUserEmail() : String{
        return auth.currentUser?.email.toString()
    }

    override suspend fun userRegister(hashMap: HashMap<String, Any>, email: String, password : String): FirebaseResource<AuthResult> {
        return withContext(Dispatchers.IO){
            safeCall {
                val registrationResult = auth.createUserWithEmailAndPassword(email, password).await()
                val userId = registrationResult.user?.uid!!
                fb.collection("Users").document(userId).set(hashMap).await()
                FirebaseResource.Success(registrationResult)
            }
        }
    }

    override suspend fun userLogin(email: String, password: String): FirebaseResource<AuthResult> {
        return withContext(Dispatchers.IO){
            safeCall {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                FirebaseResource.Success(result)
            }
        }
    }

    override suspend fun updateUser(userFirstname: String, userLastname: String,userUid : String) {
        fb.collection("Users").document(userUid).update(
            "userFirstname", userFirstname,
            "userLastname",userLastname)
    }

    override suspend fun changeUserPassword(email : String,oldPass: String, newPass: String): FirebaseResource<Boolean?> {
        return withContext(Dispatchers.IO){
            safeCall {
                var isSuccess : Boolean? = null
                val user = auth.currentUser
                user!!.updatePassword(newPass)
                    .addOnSuccessListener {
                            isSuccess = true
                    }.addOnFailureListener {
                        FirebaseResource.Error(it.localizedMessage,null)
                    }.await()
                FirebaseResource.Success(isSuccess)
            }
        }
    }

    override suspend fun getUserFavoriteList(email: String): FirebaseResource<ArrayList<Movie>> {
        return withContext(Dispatchers.IO){
            safeCall {
                val list = ArrayList<Movie>()
                val result = fb.collection("MyList").whereEqualTo("email", email).get().await()
                for (document in result){
                    val movie: Movie = document.toObject(Movie::class.java)
                    list.add(movie)
                }
                FirebaseResource.Success(list)
            }
        }

    }


}