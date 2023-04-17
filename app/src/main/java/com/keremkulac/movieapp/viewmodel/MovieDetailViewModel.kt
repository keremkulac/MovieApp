package com.keremkulac.movieapp.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.service.ApiServiceImp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.math.RoundingMode
import java.text.DecimalFormat

class MovieDetailViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    var movieGenres = MutableLiveData<ArrayList<Genre>>()
    var tvSeriesGenres = MutableLiveData<ArrayList<Genre>>()
    val myList = MutableLiveData<ArrayList<QueryDocumentSnapshot>>()
    val list = ArrayList<QueryDocumentSnapshot>()
    var idList = MutableLiveData<ArrayList<String>>()
    private val id = ArrayList<String>()
    private val apiServiceImp = ApiServiceImp()
    init {
        getMovieGenres()
        getTvSeriesGenres()
        getMyList()
    }
    private fun getMovieGenres(){
        disposable.add(
            apiServiceImp.getMovieGenre()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Genres>(){
                    override fun onSuccess(t: Genres) {
                        movieGenres.value = t.genres

                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }

    private fun getTvSeriesGenres(){
        disposable.add(
            apiServiceImp.getTvSeriesGenre()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Genres>(){
                    override fun onSuccess(t: Genres) {
                        tvSeriesGenres.value = t.genres

                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }

    fun splitDate(date : String): String{
        val list = date.split("-")
        return list.get(0)
    }

    fun vote(vote : String) : String{
        val float = vote.toFloat()
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.DOWN
        return df.format(float).toString()
    }
    private fun getMyList(){
        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser

        db.collection("MyList")
            .whereEqualTo("email",user!!.email.toString() )
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    list.add(document)
                    id.add(document.data["id"].toString())

                }
                myList.value = list
                idList.value = id

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

     fun add(movie: Movie?,context : Context){
        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser
        user.let {
            movie.let {
                movie!!.email = user!!.email
                movie.isChecked = true
                db.collection("MyList")
                    .add(movie!!)
                    .addOnSuccessListener { Toast.makeText(context,"Added to your movie list",
                        Toast.LENGTH_SHORT).show()                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                    }
            }

        }

    }

     fun checkAndRemoveList(id : Int,context: Context,imageView: ImageView){
        val db = Firebase.firestore
        val user = FirebaseAuth.getInstance().currentUser
        db.collection("MyList")
            .whereEqualTo("email",user!!.email.toString() )
            .whereEqualTo("id",id)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document["checked"] == true){
                        deleteMovieToList(document.id, context )
                        imageView.setImageResource(R.drawable.ic_add)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
    }

    private fun deleteMovieToList(documentID : String,context: Context){
        val db = Firebase.firestore
        db.collection("MyList")
            .document(documentID)
            .delete()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context,"Removed from list",Toast.LENGTH_SHORT).show()
                }
            }
    }



}