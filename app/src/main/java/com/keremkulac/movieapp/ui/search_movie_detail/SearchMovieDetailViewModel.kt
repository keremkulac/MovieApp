package com.keremkulac.movieapp.ui.search_movie_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.repository.model.Genre
import com.keremkulac.movieapp.repository.MovieRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class SearchMovieDetailViewModel
@Inject constructor(private val movieRepositoryImp: MovieRepositoryImp ) : ViewModel() {
    var genres = MutableLiveData<ArrayList<Genre>>()
    init {
        getGenres()
    }
    private fun getGenres(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepositoryImp.getMovieGenre()
            result.data?.let {
                genres.postValue(it.genres)
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