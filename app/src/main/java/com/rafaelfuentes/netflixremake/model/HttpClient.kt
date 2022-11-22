package com.rafaelfuentes.netflixremake.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient {

    companion object {
        const val BASE_URL = "https://api.tiagoaguiar.co/netflixapp/"
        const val API_KEY = "c717d267-7d7b-4876-853b-1645e17d588d"
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}