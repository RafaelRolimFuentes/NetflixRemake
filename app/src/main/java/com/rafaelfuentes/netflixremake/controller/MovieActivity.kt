package com.rafaelfuentes.netflixremake.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.rafaelfuentes.netflixremake.R
import com.rafaelfuentes.netflixremake.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {
    private var binding: ActivityMovieBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.movieToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}