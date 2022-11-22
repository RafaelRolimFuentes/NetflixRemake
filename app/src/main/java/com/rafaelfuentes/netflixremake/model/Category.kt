package com.rafaelfuentes.netflixremake.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("title")val title: String,
    @SerializedName("movie")val movie: List<Movie>
)
