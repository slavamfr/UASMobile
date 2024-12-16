package com.faiz.uas.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.faiz.retrofitapi.model.Comic
import com.faiz.retrofitapi.network.ApiClient
import com.faiz.uas.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUpdateActivity : AppCompatActivity() {

    private lateinit var etComicTitle: EditText
    private lateinit var etComicGenre: EditText
    private lateinit var etComicAuthor: EditText
    private lateinit var etComicDescription: EditText
    private lateinit var etComicImage: EditText
    private lateinit var btnUpdateComic: Button

    private var comicId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_update)

        etComicTitle = findViewById(R.id.et_comic_title)
        etComicGenre = findViewById(R.id.et_comic_genre)
        etComicAuthor = findViewById(R.id.et_comic_author)
        etComicDescription = findViewById(R.id.et_comic_description)
        etComicImage = findViewById(R.id.et_comic_image)
        btnUpdateComic = findViewById(R.id.btn_update_comic)

        comicId = intent.getStringExtra("comic_id")
        etComicTitle.setText(intent.getStringExtra("comic_name"))
        etComicGenre.setText(intent.getStringExtra("comic_genre"))
        etComicAuthor.setText(intent.getStringExtra("comic_author"))
        etComicDescription.setText(intent.getStringExtra("comic_description"))
        etComicImage.setText(intent.getStringExtra("comic_image"))

        btnUpdateComic.setOnClickListener {
            updateComic()
        }
    }

    private fun updateComic() {
        val updatedComic = Comic(
            id = comicId,
            nama = etComicTitle.text.toString(),
            gambar = etComicImage.text.toString(),
            author = etComicAuthor.text.toString(),
            genre = etComicGenre.text.toString(),
            deskripsi = etComicDescription.text.toString()
        )

        ApiClient.getInstance().updateComic(comicId!!, updatedComic).enqueue(object : Callback<Comic> {
            override fun onResponse(call: Call<Comic>, response: Response<Comic>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminUpdateActivity, "Comic updated successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AdminUpdateActivity, AdminHomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@AdminUpdateActivity, "Failed to update comic", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Comic>, t: Throwable) {
                Toast.makeText(this@AdminUpdateActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}