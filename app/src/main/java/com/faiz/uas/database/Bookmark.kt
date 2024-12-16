package com.faiz.uas.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey val id: String,
    val nama: String,
    val gambar: String,
    val author: String,
    val genre: String,
    val deskripsi: String,
    val username: String
)