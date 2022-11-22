package com.rafaelfuentes.netflixremake.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class MovieTask(private val path: String, private val listener: CoverUrl) {

    private var executors = Executors.newSingleThreadExecutor()
    private var handler = Handler(Looper.getMainLooper())

    interface CoverUrl {
        fun getBitmap(response: Bitmap)
    }
    fun execute() {
        executors.execute {
            var urlConnection: HttpsURLConnection? = null
            var inputStream: InputStream? = null

            try {

                val urlRequest = URL(path)
                urlConnection = urlRequest.openConnection() as HttpsURLConnection
                urlConnection.connectTimeout = 2000
                urlConnection.readTimeout = 2000
                val responseCode = urlConnection.responseCode

                if (responseCode > 200) {
                    throw IOException("Erro com o servidor")
                }

                inputStream = urlConnection.inputStream

                val bitmap = BitmapFactory.decodeStream(inputStream)

                handler.post {
                    listener.getBitmap(bitmap)

                }
            } catch (e: Exception) {
            } finally {
                urlConnection?.disconnect()
                inputStream?.close()
            }
        }
    }
}