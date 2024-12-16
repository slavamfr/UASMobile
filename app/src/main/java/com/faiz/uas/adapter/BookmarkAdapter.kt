package com.faiz.uas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faiz.uas.database.AppDatabase
import com.faiz.uas.database.Bookmark
import com.faiz.uas.databinding.ItemBookmarkBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookmarkAdapter(
    private val bookmarkList: MutableList<Bookmark>
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    inner class BookmarkViewHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmark: Bookmark) {
            binding.title.text = bookmark.nama
            binding.genreComic.text = bookmark.genre

            Glide.with(binding.imgId.context)
                .load(bookmark.gambar)
                .into(binding.imgId)

            binding.bookmark.setOnClickListener {
                removeBookmark(bookmark, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(bookmarkList[position])
    }

    override fun getItemCount(): Int = bookmarkList.size

    private fun removeBookmark(bookmark: Bookmark, context: android.content.Context) {
        GlobalScope.launch {
            AppDatabase.getDatabase(context).bookmarkDao().delete(bookmark)
            withContext(Dispatchers.Main) {
                bookmarkList.remove(bookmark)
                notifyDataSetChanged()
            }
        }
    }
}