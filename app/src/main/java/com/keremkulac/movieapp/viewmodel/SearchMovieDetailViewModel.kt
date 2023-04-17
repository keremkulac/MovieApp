package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.service.ApiServiceImp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.math.RoundingMode
import java.text.DecimalFormat

class SearchMovieDetailViewModel {
    private val disposable = CompositeDisposable()
    var genres = MutableLiveData<ArrayList<Genre>>()
    private val apiServiceImp = ApiServiceImp()
    init {
        getGenres()
    }
    private fun getGenres(){
        disposable.add(
            apiServiceImp.getMovieGenre()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Genres>(){
                    override fun onSuccess(t: Genres) {
                        genres.value = t.genres

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

    

}