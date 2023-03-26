package com.keremkulac.movieapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemTvSeriesBinding
import com.keremkulac.movieapp.model.TvSeries
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.view.MovieDetailFragment

class TvSeriesAdapter(
    var activity : FragmentActivity,
    var tvSeriesList : ArrayList<TvSeries>): RecyclerView.Adapter<TvSeriesAdapter.TvSeriesViewHolder>(){

    class TvSeriesViewHolder (val binding : ItemTvSeriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvSeries: TvSeries){
            binding.tvseries =tvSeries
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSeriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemTvSeriesBinding>(inflater,
            R.layout.item_tv_series,parent,false)
        return TvSeriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tvSeriesList.size
    }

    override fun onBindViewHolder(holder: TvSeriesViewHolder, position: Int) {
        holder.apply { bind(tvSeriesList[position]) }
        if(tvSeriesList[position].poster_path != null) {
            holder.binding.tvSeriesPoster.downloadFromUrl(
                tvSeriesList[position].poster_path,
                placeHolderProgressBar(holder.itemView.context)
            )
        }
        holder.itemView.setOnClickListener {
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            val args = Bundle()
            val movieDetailFragment = MovieDetailFragment()
            args.putSerializable("tvSeries",tvSeriesList[position])
            movieDetailFragment.arguments = args
            movieDetailFragment.show(activity.supportFragmentManager,"TAG")
            fragmentTransaction.commit()
        }
    }
    fun filterList(filterList: ArrayList<TvSeries>) {
        tvSeriesList.addAll(filterList)
        notifyDataSetChanged()
    }
}