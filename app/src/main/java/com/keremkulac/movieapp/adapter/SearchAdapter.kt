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
import com.keremkulac.movieapp.databinding.SearchItemMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import com.keremkulac.movieapp.view.MovieDetailFragment

class SearchAdapter(val activity : FragmentActivity,var list : ArrayList<Movie>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(val binding : SearchItemMovieBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieList : Movie){
            binding.movie = movieList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<SearchItemMovieBinding>(inflater,
            R.layout.search_item_movie,parent,false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.apply { bind(list[position]) }
        holder.binding.moviePoster.downloadFromUrl(list[position].poster_path,
            placeHolderProgressBar(holder.itemView.context)
        )
        holder.itemView.setOnClickListener {
            /*
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            val args = Bundle()
            val movieDetailFragment = MovieDetailFragment()
            args.putSerializable("movie",list[position])
            movieDetailFragment.arguments = args
            movieDetailFragment.show(activity.supportFragmentManager,"TAG")
            fragmentTransaction.commit()

             */
        }
    }
    fun filterList(filterList: ArrayList<Movie>) {
        list = filterList
        notifyDataSetChanged()
    }


    fun updatePatternList(searchMovieList : List<Movie>){
        list.clear()
        list.addAll(searchMovieList)
        notifyDataSetChanged()
    }
}