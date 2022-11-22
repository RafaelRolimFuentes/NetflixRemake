package com.rafaelfuentes.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.rafaelfuentes.netflixremake.model.Category
import com.rafaelfuentes.netflixremake.model.Movie
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask(private val listener: Callback) {

    private var executors = Executors.newSingleThreadExecutor()
    private var handler = Handler(Looper.getMainLooper())

    interface Callback {
        fun getCategories(response: List<Category>)
    }
    fun execute(path: String) {
        executors.execute {
            var urlConnection: HttpsURLConnection? = null
            var inputStream: InputStream? = null

            try {
                val urlrequest = URL(path)
                urlConnection = urlrequest.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000
                val responseCode = urlConnection.responseCode

                if (responseCode > 400) {
                    throw IOException("Erro na comunicação com o servidor")
                }

                inputStream = urlConnection.inputStream
                val jsonAsString = inputStream.bufferedReader().use { it.readText() }

                val categories = toCategories(jsonAsString)

                handler.post {
                    listener.getCategories(categories)
                }
            } catch (e: IOException) {
                Log.e("ERROR", e.message ?: "Erro desconhecido", e)
            } finally {
                inputStream?.close()
                urlConnection?.disconnect()
            }
        }
    }

    private fun toCategories(jsonAsString: String): List<Category> {
        val categories = ArrayList<Category>()
        val jsonObject = JSONObject(jsonAsString)
        val jsonCategoryArray = jsonObject.getJSONArray("category")

        for (i in 0 until jsonCategoryArray.length()) {
            val categoryObject = jsonCategoryArray.getJSONObject(i)
            val title = categoryObject.getString("title")
            val movieArray = categoryObject.getJSONArray("movie")

            val movies = mutableListOf<Movie>()
            for (j in 0 until movieArray.length()) {

                val movieObject = movieArray.getJSONObject(j)
                val id = movieObject.getInt("id")
                val coverUrl = movieObject.getString("cover_url")

                val movie = Movie(id, coverUrl)
                movies.add(movie)
            }
            val category = Category(title, movies)
            categories.add(category)
        }
        return categories
    }
}