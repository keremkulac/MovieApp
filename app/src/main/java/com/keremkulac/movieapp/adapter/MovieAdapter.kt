package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar

class MovieAdapter(
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
            val bundle = bundleOf("movie" to popularMovieList[position])
            holder.itemView.findNavController().navigate(R.id.action_movieFragment_to_movieDetailFragment,bundle)
        }
    }
    fun filterList(filterList: ArrayList<Movie>) {
        popularMovieList.addAll(filterList)
        notifyDataSetChanged()
    }
}


