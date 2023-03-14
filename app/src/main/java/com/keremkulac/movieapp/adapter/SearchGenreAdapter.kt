package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.keremkulac.movieapp.R

class SearchGenreAdapter(var clickListener : ClickListener, val genreNames: ArrayList<String>): RecyclerView.Adapter<SearchGenreAdapter.SearchGenreAdapterViewHolder>(){

    class SearchGenreAdapterViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var button : Button
        init {
            button = view.findViewById(R.id.itemButton)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGenreAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return SearchGenreAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genreNames.size
    }

    override fun onBindViewHolder(holder: SearchGenreAdapterViewHolder, position: Int) {
        holder.button.text = genreNames[position]
        holder.button.setOnClickListener {
            clickListener.ClickedItem(holder.button.text.toString())
        }
    }

    interface ClickListener {
        fun ClickedItem(genre : String)
    }
}

