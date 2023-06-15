package com.keremkulac.movieapp.util

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.keremkulac.movieapp.R

fun ImageView.downloadFromUrl(url: String, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    val posterUrl =  "https://image.tmdb.org/t/p/w500/"+url
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(posterUrl)
        .into(this)

}

fun placeHolderProgressBar(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }

}

fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager,layoutID : Int){
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(layoutID,fragment)
    fragmentTransaction.commit()
}

sealed class FirebaseResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : FirebaseResource<T>(data)
    class Loading<T>(data: T? = null) : FirebaseResource<T>(data)
    class Error<T>(message: String, data: T? = null) : FirebaseResource<T>(data, message)
}

inline fun <T> safeCall(action: () -> FirebaseResource<T>): FirebaseResource<T> {
    return try {
        action()
    } catch (e: Exception) {
        FirebaseResource.Error(e.message ?: "An unknown Error Occurred")
    }
}


