package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.Movie
import com.keremkulac.movieapp.R
import javax.inject.Inject

class SearchGenreAdapter @Inject constructor(): RecyclerView.Adapter<SearchGenreAdapter.SearchGenreAdapterViewHolder>(){
    var onItemClick: ((String) -> Unit)? = null

    class SearchGenreAdapterViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var button : Button
        init {
            button = view.findViewById(R.id.itemButton)
        }

    }

    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
    var list : List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGenreAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre,parent,false)
        return SearchGenreAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchGenreAdapterViewHolder, position: Int) {
        holder.button.text = list[position]
        holder.button.setOnClickListener {
             val parts = holder.button.text.split("(")
                onItemClick?.invoke(parts[0])
        }
    }

    interface ClickListener {
        fun ClickedItem(genre : String)
    }

}

