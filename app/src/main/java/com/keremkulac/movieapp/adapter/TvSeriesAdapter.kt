package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemTvSeriesBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import javax.inject.Inject

class TvSeriesAdapter @Inject constructor(): RecyclerView.Adapter<TvSeriesAdapter.TvSeriesViewHolder>(){

    class TvSeriesViewHolder (val binding : ItemTvSeriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvSeries: Movie){
            binding.tvseries =tvSeries
        }
    }

    var onItemClick: ((Movie) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
    var tvSeriesList : List<Movie>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

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
            onItemClick?.invoke(tvSeriesList[position])
        }
    }

}