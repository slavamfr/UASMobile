package com.faiz.uas.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.faiz.retrofitapi.model.Comic
import com.faiz.retrofitapi.network.ApiClient
import com.faiz.uas.DetailItemActivity
import com.faiz.uas.LoginActivity
import com.faiz.uas.PrefManager
import com.faiz.uas.R
import com.faiz.uas.adapter.ComicAdminAdapter
import com.faiz.uas.databinding.ActivityAdminHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHomeBinding
    private lateinit var comicAdapter: ComicAdminAdapter
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefManager = PrefManager(this)

        val btnAddComic: Button = findViewById(R.id.btn_add_comic)
        btnAddComic.setOnClickListener {
            // Navigate to AdminCreateActivity
            val intent = Intent(this, AdminCreateActivity::class.java)
            startActivity(intent)
        }

        val btnLogout: Button = findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener {
            prefManager.clear()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        setupRecyclerView()
        fetchComics()
    }

    override fun onResume() {
        super.onResume()
        fetchComics() // Reload data when the activity resumes
    }

    private fun setupRecyclerView() {
        binding.rvComic.layoutManager = GridLayoutManager(this, 2)
        comicAdapter = ComicAdminAdapter(emptyList(), this::onEditClick, this::onDeleteClick, this::onItemClick)
        binding.rvComic.adapter = comicAdapter
    }

    private fun fetchComics() {
        ApiClient.getInstance().getComicList().enqueue(object : Callback<List<Comic>> {
            override fun onResponse(call: Call<List<Comic>>, response: Response<List<Comic>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        comicAdapter = ComicAdminAdapter(it, this@AdminHomeActivity::onEditClick, this@AdminHomeActivity::onDeleteClick, this@AdminHomeActivity::onItemClick)
                        binding.rvComic.adapter = comicAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Comic>>, t: Throwable) {
                // Log the error
                Log.e("AdminHomeActivity", "Failed to fetch comics", t)

                // Show a message to the user
                Toast.makeText(this@AdminHomeActivity, "Failed to load comics. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onEditClick(comic: Comic) {
        val intent = Intent(this, AdminUpdateActivity::class.java)
        intent.putExtra("comic_id", comic.id)
        intent.putExtra("comic_name", comic.nama)
        intent.putExtra("comic_image", comic.gambar)
        intent.putExtra("comic_author", comic.author)
        intent.putExtra("comic_genre", comic.genre)
        intent.putExtra("comic_description", comic.deskripsi)
        startActivity(intent)
    }

    private fun onDeleteClick(comic: Comic) {
        ApiClient.getInstance().deleteComic(comic.id!!).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminHomeActivity, "Comic deleted successfully", Toast.LENGTH_SHORT).show()
                    fetchComics() // Refresh the list after deletion
                } else {
                    Toast.makeText(this@AdminHomeActivity, "Failed to delete comic", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AdminHomeActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onItemClick(comic: Comic) {
        val intent = Intent(this, DetailItemActivity::class.java)
        intent.putExtra("COMIC_TITLE", comic.nama)
        intent.putExtra("COMIC_GENRE", comic.genre)
        intent.putExtra("COMIC_AUTHOR", comic.author)
        intent.putExtra("COMIC_IMAGE", comic.gambar)
        intent.putExtra("COMIC_DESCRIPTION", comic.deskripsi)
        startActivity(intent)
    }
}