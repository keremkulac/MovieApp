package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.service.ApiServiceImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

class SearchMovieDetailViewModel {
    var genres = MutableLiveData<ArrayList<Genre>>()
    private val apiServiceImp = ApiServiceImp()
    init {
        getGenres()
    }
    private fun getGenres(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiServiceImp.getMovieGenre()
            if(result.isSuccessful){
                result.body()?.let {
                    genres.postValue(it.genres)
                }
            }else{
                Log.d("TAG","ERROR TREN MOVIE")
            }

        }
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

    

}