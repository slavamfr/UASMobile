package com.faiz.uas.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faiz.retrofitapi.model.Comic
import com.faiz.uas.DetailItemActivity
import com.faiz.uas.database.AppDatabase
import com.faiz.uas.database.Bookmark
import com.faiz.uas.databinding.ItemComicBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ComicAdapter(
    private val comicList: List<Comic>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(private val binding: ItemComicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comic: Comic) {
            binding.title.text = comic.nama
            binding.genreComic.text = comic.genre

            Glide.with(binding.imgId.context)
                .load(comic.gambar)
                .into(binding.imgId)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailItemActivity::class.java).apply {
                    putExtra("COMIC_ID", comic.id)
                    putExtra("COMIC_TITLE", comic.nama)
                    putExtra("COMIC_GENRE", comic.genre)
                    putExtra("COMIC_AUTHOR", comic.author)
                    putExtra("COMIC_IMAGE", comic.gambar)
                    putExtra("COMIC_DESCRIPTION", comic.deskripsi)
                }
                context.startActivity(intent)
            }

            binding.bookmark.setOnClickListener {
                val context = binding.root.context
                val sharedPreferences = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
                val username = sharedPreferences.getString("USERNAME", "User") ?: "User"
                val bookmark = Bookmark(
                    id = comic.id ?: "",
                    nama = comic.nama,
                    gambar = comic.gambar,
                    author = comic.author,
                    genre = comic.genre,
                    deskripsi = comic.deskripsi,
                    username = username
                )
                GlobalScope.launch {
                    AppDatabase.getDatabase(context).bookmarkDao().insert(bookmark)
                }
                Toast.makeText(context, "Bookmarked: ${comic.nama}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding = ItemComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(comicList[position])
    }

    override fun getItemCount(): Int = comicList.size
}