package com.rafaelfuentes.netflixremake.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetflixAPI {
    @GET("home")
    fun getCategories(@Query("apiKey") apiKey: String = HttpClient.API_KEY): Call<CategoryObject>

    @GET("movie/{id}")
    fun getMovie(@Path(value = "id") id: Int, @Query("apiKey")apiKey: String = HttpClient.API_KEY): Call<MovieDetail>
}