package com.faiz.uas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faiz.retrofitapi.model.Comic
import com.faiz.uas.databinding.ItemComicAdminBinding

class ComicAdminAdapter(
    private val comicList: List<Comic>,
    private val onEditClick: (Comic) -> Unit,
    private val onDeleteClick: (Comic) -> Unit,
    private val onItemClick: (Comic) -> Unit
) : RecyclerView.Adapter<ComicAdminAdapter.ComicAdminViewHolder>() {

    inner class ComicAdminViewHolder(private val binding: ItemComicAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comic: Comic) {
            binding.title.text = comic.nama
            binding.genreComic.text = comic.genre

            Glide.with(binding.imgId.context)
                .load(comic.gambar)
                .into(binding.imgId)

            binding.edit1.setOnClickListener {
                onEditClick(comic)
            }

            binding.edit.setOnClickListener {
                onDeleteClick(comic)
            }

            binding.root.setOnClickListener {
                onItemClick(comic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicAdminViewHolder {
        val binding = ItemComicAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicAdminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicAdminViewHolder, position: Int) {
        holder.bind(comicList[position])
    }

    override fun getItemCount(): Int = comicList.size
}