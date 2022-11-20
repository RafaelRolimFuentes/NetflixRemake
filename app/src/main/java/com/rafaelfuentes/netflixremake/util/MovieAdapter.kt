package com.rafaelfuentes.netflixremake.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rafaelfuentes.netflixremake.R
import com.rafaelfuentes.netflixremake.model.Movie

class MovieAdapter(private val movieList: List<Movie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val coverUrl = itemView.findViewById<ImageView>(R.id.movie_cover)

        fun bind(movie: Movie){
            coverUrl.setImageResource(movie.coverUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.movie_item, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size

}