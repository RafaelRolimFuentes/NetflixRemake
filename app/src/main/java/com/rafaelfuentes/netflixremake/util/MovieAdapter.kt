package com.rafaelfuentes.netflixremake.util

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rafaelfuentes.netflixremake.R
import com.rafaelfuentes.netflixremake.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private val movieList: List<Movie>, private val listener: OnClick? = null) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var coverUrl = itemView.findViewById<ImageView>(R.id.movie_cover)

        fun bind(movie: Movie) {
            //Sem biblioteca externa
            MovieTask(movie.coverUrl, object : MovieTask.CoverUrl {
                override fun getBitmap(response: Bitmap) {
                    coverUrl.setImageBitmap(response)
                }
            }).execute()

//            Usando a biblioteca externa do Picasso para popular as imagens
//            Picasso.get().load(movie.coverUrl).into(coverUrl)
            coverUrl.setOnClickListener {
                listener?.onClick(movie.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size
}