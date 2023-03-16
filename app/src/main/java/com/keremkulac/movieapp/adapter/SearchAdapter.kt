package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.databinding.ItemSearchMovieBinding
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar

class SearchAdapter(var list : ArrayList<Movie>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(val binding : ItemSearchMovieBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieList : Movie){
            binding.movie = movieList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemSearchMovieBinding>(inflater,
            R.layout.item_search_movie,parent,false)
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
}