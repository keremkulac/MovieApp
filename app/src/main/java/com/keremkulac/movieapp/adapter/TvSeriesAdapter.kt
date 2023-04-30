package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemTvSeriesBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar

class TvSeriesAdapter(
    private val clickListener : ClickListener,
    private val tvSeriesList : ArrayList<Movie>): RecyclerView.Adapter<TvSeriesAdapter.TvSeriesViewHolder>(){

    class TvSeriesViewHolder (val binding : ItemTvSeriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvSeries: Movie){
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
            tvSeriesList[position].poster_path?.let {
                holder.binding.tvSeriesPoster.downloadFromUrl(
                    it,
                    placeHolderProgressBar(holder.itemView.context)
                )
            }
        }
        holder.itemView.setOnClickListener {
            clickListener.ClickedTvSeriesItem(tvSeriesList[position])
        }
    }

    interface ClickListener {
        fun ClickedTvSeriesItem(movie : Movie)
    }
}