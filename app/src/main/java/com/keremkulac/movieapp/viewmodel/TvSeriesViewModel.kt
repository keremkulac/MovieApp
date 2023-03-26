package com.keremkulac.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.keremkulac.movieapp.model.TvSeries
import com.keremkulac.movieapp.model.TvSeriesResult
import com.keremkulac.movieapp.service.tv_series.PopularTvApiImp
import com.keremkulac.movieapp.service.tv_series.TopRatedTvSeriesApiImp
import com.keremkulac.movieapp.util.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TvSeriesViewModel {
    private val disposable = CompositeDisposable()
    val topRatedTvSeries = MutableLiveData<ArrayList<TvSeries>>()
    val popularTvSeries = MutableLiveData<ArrayList<TvSeries>>()
    private val popularTvApiImp = PopularTvApiImp()
    private val topRatedTvSeriesApiImp = TopRatedTvSeriesApiImp()

    init {
        getPopularTvSeries()
        getTopRatedTvSeries()
    }
    private fun getPopularTvSeries(){
        disposable.add(
            popularTvApiImp.getTvPopular(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvSeriesResult>(){
                    override fun onSuccess(t: TvSeriesResult) {
                        popularTvSeries.value = t.tvSeries
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }

    private fun getTopRatedTvSeries(){
        disposable.add(
            topRatedTvSeriesApiImp.getTopRated(API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TvSeriesResult>(){
                    override fun onSuccess(t: TvSeriesResult) {
                        topRatedTvSeries.value = t.tvSeries
                    }
                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.d("TAG", it) }
                    }
                })
        )
    }
}