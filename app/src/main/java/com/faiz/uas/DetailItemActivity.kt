package com.faiz.uas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.faiz.uas.databinding.ActivityDetailItemBinding

class DetailItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val comicTitle = intent.getStringExtra("COMIC_TITLE")
        val comicGenre = intent.getStringExtra("COMIC_GENRE")
        val comicAuthor = intent.getStringExtra("COMIC_AUTHOR")
        val comicImage = intent.getStringExtra("COMIC_IMAGE")
        val comicDescription = intent.getStringExtra("COMIC_DESCRIPTION")

        // Isi data ke dalam tampilan
        binding.titleComicDetail.text = comicTitle
        binding.genreComic.text = comicGenre
        binding.authorComic.text = comicAuthor
        binding.txtDesc.text = comicDescription

        Glide.with(this)
            .load(comicImage)
            .into(binding.imageComicDetail)

        binding.actionBack.setOnClickListener {
            finish() // Kembali ke halaman sebelumnya
        }
    }
}