package com.rafaelfuentes.netflixremake.model

import java.util.*

data class Category(
    val title: String,
    val movieList: List<Movie>
)
