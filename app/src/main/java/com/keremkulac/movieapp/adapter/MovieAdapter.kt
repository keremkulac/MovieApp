package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import javax.inject.Inject

class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.PopularViewHolder>(){

    var onItemClick: ((Movie) -> Unit)? = null

    class PopularViewHolder (val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(popularMovies: Movie){
            binding.movie =popularMovies
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
    var movies : List<Movie>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemMovieBinding>(inflater,R.layout.item_movie,parent,false)
        return PopularViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.apply { bind(movies[position]) }

        movies[position].poster_path?.let {
            holder.binding.moviePoster.downloadFromUrl(
                it, placeHolderProgressBar(holder.itemView.context))
        }
        holder.itemView.setOnClickListener {
            onItemClick?.let {
                it.invoke(movies[position])
            }
        }
    }
    interface ClickListener {
        fun ClickedMovieItem(movie : Movie)
    }
}


