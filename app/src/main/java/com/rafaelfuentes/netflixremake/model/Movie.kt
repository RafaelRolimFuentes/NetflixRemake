package com.rafaelfuentes.netflixremake.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")val id: Int,
    @SerializedName("cover_url")val coverUrl: String
)
