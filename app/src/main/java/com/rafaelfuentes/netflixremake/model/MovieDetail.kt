package com.rafaelfuentes.netflixremake.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("id")val id: Int,
    @SerializedName("title")val title: String,
    @SerializedName("desc")val desc: String,
    @SerializedName("cast")val cast: String,
    @SerializedName("cover_url")val coverUrl: String,
    @SerializedName("movie")val movieList: List<Movie>
)
