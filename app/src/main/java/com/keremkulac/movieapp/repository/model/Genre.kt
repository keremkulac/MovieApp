package com.keremkulac.movieapp.repository.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres")
    val genres: ArrayList<Genre>
)
data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name:String?
)
