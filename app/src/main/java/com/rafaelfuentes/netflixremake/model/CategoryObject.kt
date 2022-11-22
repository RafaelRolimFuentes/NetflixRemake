package com.rafaelfuentes.netflixremake.model

import com.google.gson.annotations.SerializedName

data class CategoryObject(
    @SerializedName("category")val category: List<Category>
)
