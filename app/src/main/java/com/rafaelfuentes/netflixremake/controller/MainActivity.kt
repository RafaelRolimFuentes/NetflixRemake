package com.rafaelfuentes.netflixremake.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaelfuentes.netflixremake.R
import com.rafaelfuentes.netflixremake.util.OnClick
import com.rafaelfuentes.netflixremake.util.CategoryAdapter
import com.rafaelfuentes.netflixremake.databinding.ActivityMainBinding
import com.rafaelfuentes.netflixremake.model.Category
import com.rafaelfuentes.netflixremake.model.CategoryObject
import com.rafaelfuentes.netflixremake.model.HttpClient
import com.rafaelfuentes.netflixremake.model.NetflixAPI
import com.rafaelfuentes.netflixremake.util.CategoryTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), CategoryTask.Callback, OnClick {
    private var binding: ActivityMainBinding? = null
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //Usando biblioteca externa
        HttpClient().getRetrofit().create(NetflixAPI::class.java).getCategories().enqueue(object : Callback<CategoryObject>{
            override fun onResponse(
                call: Call<CategoryObject>,
                response: Response<CategoryObject>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    getCategories(body!!.category)

                }else{
                    //Erro de chamada
                    val error = response.errorBody()?.string()
                    showError(error ?: "Erro desconhecido")
                }
            }
            override fun onFailure(call: Call<CategoryObject>, t: Throwable) {
                //Erro de servidor
                binding?.mainProgress?.visibility = View.GONE
                showError(t.message ?: "Erro interno")
            }
        })
        //Consumindo Api sem uso de biblioteca externa
        //CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=c717d267-7d7b-4876-853b-1645e17d588d")
    }

    override fun getCategories(response: List<Category>) {
        adapter = CategoryAdapter(response, this)
        binding?.let {
            it.rvCategory.layoutManager = LinearLayoutManager(this)
            it.rvCategory.adapter = adapter
        }
        adapter.notifyDataSetChanged()
        binding?.mainProgress?.visibility = View.GONE
    }

    override fun onClick(movieId: Int) {
        if (movieId <= 3) {
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra(MovieActivity.KEY, movieId)
            startActivity(intent)
        } else Toast.makeText(this, R.string.unavailable, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showError(error: String?){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}