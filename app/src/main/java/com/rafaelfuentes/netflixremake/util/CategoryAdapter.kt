package com.rafaelfuentes.netflixremake.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafaelfuentes.netflixremake.R
import com.rafaelfuentes.netflixremake.model.Category

class CategoryAdapter(private val categories: List<Category>, private val listener: OnClick? = null) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.category_name)
        val rvMovies = itemView.findViewById<RecyclerView>(R.id.rv_movies)

        fun bind(category: Category) {
            title.text = category.title
            rvMovies.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            rvMovies.adapter = MovieAdapter(category.movie, listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size
}