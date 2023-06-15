package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar
import javax.inject.Inject

class MyListAdapter @Inject constructor() : RecyclerView.Adapter<MyListAdapter.MyListViewHolder>(){
    class MyListViewHolder(view : View) : RecyclerView.ViewHolder(view)
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
    var movies : List<Movie>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_list, parent, false)
        return MyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.myListMoviePoster)
        val movie = movies[position]
        holder.itemView.apply {
            imageView.downloadFromUrl(movie.poster_path.toString(),
                placeHolderProgressBar(holder.itemView.context))
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(movie)
            }
        }
    }
}




