package com.faiz.uas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.faiz.retrofitapi.model.Comic
import com.faiz.retrofitapi.network.ApiClient
import com.faiz.uas.adapter.ComicAdapter
import com.faiz.uas.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var comicAdapter: ComicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve username from SharedPreferences
        val sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val loggedInUser = sharedPreferences.getString("USERNAME", "User")

        // Display username in TextView
        binding.user.text = loggedInUser

        loadComicsFromApi() // Call API to load data

        // Setup bottom navigation
        setupBottomNavigation()
    }

    private fun loadComicsFromApi() {
        val apiService = ApiClient.getInstance()
        apiService.getComicList().enqueue(object : Callback<List<Comic>> {
            override fun onResponse(call: Call<List<Comic>>, response: Response<List<Comic>>) {
                if (response.isSuccessful) {
                    val comics = response.body() ?: emptyList()
                    setupRecyclerView(comics)
                } else {
                    Log.e("MainActivity", "Failed to fetch comics: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Comic>>, t: Throwable) {
                Log.e("MainActivity", "Error: ${t.message}", t)
                Toast.makeText(this@MainActivity, "Failed to load comics", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(comics: List<Comic>) {
        comicAdapter = ComicAdapter(comics) { comicId ->
            Toast.makeText(this, "Clicked: Comic ID $comicId", Toast.LENGTH_SHORT).show()
        }
        binding.rvComic.apply {
            adapter = comicAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2) // 2 columns
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Current activity
                    true
                }
                R.id.nav_bookmarks -> {
                    startActivity(Intent(this, BookmarkActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}