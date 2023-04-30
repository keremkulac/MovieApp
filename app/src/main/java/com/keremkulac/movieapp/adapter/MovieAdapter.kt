package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar

class MovieAdapter(
    private val clickListener : ClickListener,
    var popularMovieList : ArrayList<Movie>,
    ): RecyclerView.Adapter<MovieAdapter.PopularViewHolder>(){

    class PopularViewHolder (val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(popularMovies: Movie){
            binding.movie =popularMovies
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemMovieBinding>(inflater,R.layout.item_movie,parent,false)
        return PopularViewHolder(view)
    }

    override fun getItemCount(): Int {
        return popularMovieList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.apply { bind(popularMovieList[position]) }

        popularMovieList[position].poster_path?.let {
            holder.binding.moviePoster.downloadFromUrl(
                it, placeHolderProgressBar(holder.itemView.context))
        }
        holder.itemView.setOnClickListener {
            clickListener.ClickedMovieItem(popularMovieList[position])
        }
    }
    interface ClickListener {
        fun ClickedMovieItem(movie : Movie)
    }
}


