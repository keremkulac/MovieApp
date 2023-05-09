package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar

class MyListAdapter(
    private val clickListener : ClickListener,
    var list : ArrayList<Movie>) : RecyclerView.Adapter<MyListAdapter.MyListViewHolder>(){
    val updatedList = ArrayList<Movie>()
    class MyListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.myListMoviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_list, parent, false)
        return MyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.imageView.downloadFromUrl(list[position].poster_path.toString(),
            placeHolderProgressBar(holder.itemView.context)
        )
        holder.itemView.setOnClickListener {
            clickListener.ClickedMyListItem(list[position])
        }
    }

    interface ClickListener {
        fun ClickedMyListItem(movie : Movie)
    }

}