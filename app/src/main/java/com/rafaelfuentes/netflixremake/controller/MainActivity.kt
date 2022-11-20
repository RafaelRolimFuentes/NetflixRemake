package com.rafaelfuentes.netflixremake.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaelfuentes.netflixremake.util.CategoryAdapter
import com.rafaelfuentes.netflixremake.R
import com.rafaelfuentes.netflixremake.databinding.ActivityMainBinding
import com.rafaelfuentes.netflixremake.model.Category
import com.rafaelfuentes.netflixremake.model.Movie

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val categories = mutableListOf<Category>()
        for (i in 0 until 4) {
            val movies = mutableListOf<Movie>()
            for (j in 0 until 8) {
                val movie = Movie(j,R.drawable.fake_drawble)
                movies.add(movie)
            }
            val category = Category("Category ${i + 1}", movies)
            categories.add(category)
        }

        adapter = CategoryAdapter(categories)
        binding?.let {
            it.rvCategory.layoutManager = LinearLayoutManager(this)
            it.rvCategory.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}