package com.faiz.uas.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.faiz.retrofitapi.model.Comic
import com.faiz.retrofitapi.network.ApiClient
import com.faiz.uas.admin.AdminHomeActivity
import com.faiz.uas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminCreateActivity : AppCompatActivity() {

    private lateinit var etComicTitle: EditText
    private lateinit var etComicGenre: EditText
    private lateinit var etComicAuthor: EditText
    private lateinit var etComicDescription: EditText
    private lateinit var etComicImage: EditText
    private lateinit var btnAddComic: Button
    private lateinit var actionBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_create)

        etComicTitle = findViewById(R.id.et_comic_title)
        etComicGenre = findViewById(R.id.et_comic_genre)
        etComicAuthor = findViewById(R.id.et_comic_author)
        etComicDescription = findViewById(R.id.et_comic_description)
        etComicImage = findViewById(R.id.et_comic_image)
        btnAddComic = findViewById(R.id.btn_add_comic)
        actionBack = findViewById(R.id.action_back)

        actionBack.setOnClickListener {
            finish()
        }

        btnAddComic.setOnClickListener {
            val title = etComicTitle.text.toString()
            val genre = etComicGenre.text.toString()
            val author = etComicAuthor.text.toString()
            val description = etComicDescription.text.toString()
            val image = etComicImage.text.toString()

            if (title.isNotEmpty() && genre.isNotEmpty() && author.isNotEmpty() && description.isNotEmpty() && image.isNotEmpty()) {
                addComic(Comic(nama = title, genre = genre, author = author, deskripsi = description, gambar = image))
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addComic(comic: Comic) {
        ApiClient.getInstance().addComic(comic).enqueue(object : Callback<Comic> {
            override fun onResponse(call: Call<Comic>, response: Response<Comic>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminCreateActivity, "Comic added successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AdminCreateActivity, AdminHomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish() // Close the activity
                } else {
                    Toast.makeText(this@AdminCreateActivity, "Failed to add comic", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Comic>, t: Throwable) {
                Toast.makeText(this@AdminCreateActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}