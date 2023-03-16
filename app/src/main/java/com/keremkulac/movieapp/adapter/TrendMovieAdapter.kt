package com.keremkulac.movieapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.view.MovieDetailFragment
import kotlin.collections.ArrayList

class TrendMovieAdapter (
        var activity : FragmentActivity,
        var trendMovieList : ArrayList<Movie>): RecyclerView.Adapter<TrendMovieAdapter.TrendViewHolder>(){

        var list =  ArrayList<Movie>()
        class TrendViewHolder (val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(popularMovies: Movie){
                binding.movie =popularMovies
            }
        }
        init {
            list = trendMovieList
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = DataBindingUtil.inflate<ItemMovieBinding>(inflater,
                R.layout.item_movie,parent,false)
            return TrendViewHolder(view)
        }

        override fun getItemCount(): Int {
            return trendMovieList.size
        }

        override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
            holder.apply { bind(trendMovieList[position]) }
            holder.binding.moviePoster.downloadFromUrl(trendMovieList[position].poster_path,
                placeHolderProgressBar(holder.itemView.context)
            )
            holder.itemView.setOnClickListener {
                val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
                val args = Bundle()
                val movieDetailFragment = MovieDetailFragment()
                args.putSerializable("movie",trendMovieList[position])
                movieDetailFragment.arguments = args
                movieDetailFragment.show(activity.supportFragmentManager,"TAG")
                fragmentTransaction.commit()

            }
        }
}