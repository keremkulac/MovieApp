package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemSearchMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import javax.inject.Inject

class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    var onItemClick: ((Movie) -> Unit)? = null

    class SearchViewHolder(val binding : ItemSearchMovieBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieList : Movie){
            binding.movie = movieList
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemSearchMovieBinding>(inflater,
            R.layout.item_search_movie,parent,false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.apply { bind(movies[position]) }
        movies[position].poster_path?.let {
            holder.binding.moviePoster.downloadFromUrl(
                it,
                placeHolderProgressBar(holder.itemView.context)
            )
        }
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(movies[position])
        }
    }
    fun filterList(filterList: ArrayList<Movie>) {
        movies = filterList
        notifyDataSetChanged()
    }

}