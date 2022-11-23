package com.rafaelfuentes.netflixremake.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.rafaelfuentes.netflixremake.R
import com.rafaelfuentes.netflixremake.databinding.ActivityMovieBinding
import com.rafaelfuentes.netflixremake.model.HttpClient
import com.rafaelfuentes.netflixremake.model.Movie
import com.rafaelfuentes.netflixremake.model.MovieDetail
import com.rafaelfuentes.netflixremake.model.NetflixAPI
import com.rafaelfuentes.netflixremake.util.MovieAdapter
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {
    private var binding: ActivityMovieBinding? = null
    private lateinit var adapter: MovieAdapter
    private lateinit var movies: List<Movie>

    companion object {
        const val KEY = "movieId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.movieToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val id =
            intent?.getIntExtra(KEY, 0) ?: throw IllegalStateException("MovieId não encontrado!")

        HttpClient().getRetrofit().create(NetflixAPI::class.java).getMovie(id)
            .enqueue(object : Callback<MovieDetail> {
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        showMovieDetails(movie)

                    } else {
                        val error = response.errorBody()?.string()
                        showError(error ?: "Erro desconhecido")
                    }
                }
                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    binding?.movieProgress?.visibility = View.GONE
                    showError(t.message ?: "Erro interno")
                }
            })
    }

    private fun showMovieDetails(movieDetail: MovieDetail?) {
        binding?.let {
            Picasso.get().load(movieDetail?.coverUrl).into(it.movieThumbnail)
            it.movieName.text = movieDetail?.title
            it.movieDesc.text = movieDetail?.desc
            it.movieCast.text = movieDetail?.cast
            it.similarContent.setText(R.string.similar_content)

            it.rvSimilarMovies.layoutManager = GridLayoutManager(this, 3)
            movies = movieDetail?.movieList!!
            adapter = MovieAdapter(movies)
            it.rvSimilarMovies.adapter = adapter

            it.icPlay.visibility = View.VISIBLE
            it.movieProgress.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}