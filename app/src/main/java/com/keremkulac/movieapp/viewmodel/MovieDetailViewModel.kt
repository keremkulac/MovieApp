package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keremkulac.movieapp.model.Genre
import com.keremkulac.movieapp.model.Genres
import com.keremkulac.movieapp.service.movie.MovieGenreApiImp
import com.keremkulac.movieapp.service.tv_series.TvSeriesGenreApiImp
import com.keremkulac.movieapp.util.API_KEY
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
    private val movieGenreApiImp = MovieGenreApiImp()
    private val tvSeriesGenreApiImp = TvSeriesGenreApiImp()
    init {
        getMovieGenres()
        getTvSeriesGenres()
    }
    private fun getMovieGenres(){
        disposable.add(
            movieGenreApiImp.getMovieGenre(API_KEY)
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
            tvSeriesGenreApiImp.getTvSeriesGenre(API_KEY)
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
}