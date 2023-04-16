package com.keremkulac.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.keremkulac.movieapp.R
import com.keremkulac.movieapp.util.downloadFromUrl
import com.keremkulac.movieapp.util.placeHolderProgressBar

class MyListAdapter(val list : ArrayList<QueryDocumentSnapshot>) : RecyclerView.Adapter<MyListAdapter.MyListViewHolder>(){

    class MyListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.moviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
            holder.imageView.downloadFromUrl(list[position].get("poster_path").toString(), placeHolderProgressBar(holder.itemView.context))


    }

}