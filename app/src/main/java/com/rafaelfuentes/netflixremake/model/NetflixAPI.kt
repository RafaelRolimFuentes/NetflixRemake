package com.rafaelfuentes.netflixremake.model

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetflixAPI {
    @GET("home")
    fun getCategories(@Query("apiKey") apiKey: String = HttpClient.API_KEY): Call<JsonObject>
}