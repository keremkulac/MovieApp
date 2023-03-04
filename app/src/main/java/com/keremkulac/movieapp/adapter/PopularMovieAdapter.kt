package com.keremkulac.movieapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.view.MovieDetailFragment
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar

class PopularMovieAdapter(
    var activity : FragmentActivity,
    var popularMovieList : ArrayList<Movie>): RecyclerView.Adapter<PopularMovieAdapter.PopularViewHolder>(){

    class PopularViewHolder (val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(popularMovies: Movie){
            binding.popularMovies =popularMovies
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
        holder.binding.moviePoster.downloadFromUrl(popularMovieList[position].poster_path,
            placeHolderProgressBar(holder.itemView.context))
        holder.itemView.setOnClickListener {
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            val args = Bundle()
            val movieDetailFragment = MovieDetailFragment()
            args.putSerializable("movie",popularMovieList[position])
            movieDetailFragment.setArguments(args)
            movieDetailFragment.show(activity.supportFragmentManager,"TAG")
            fragmentTransaction.commit()
        }
    }
}


