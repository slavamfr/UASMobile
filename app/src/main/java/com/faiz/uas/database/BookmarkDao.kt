package com.faiz.uas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks WHERE username = :username")
    suspend fun getBookmarksByUsername(username: String): List<Bookmark>

    @Delete
    suspend fun delete(bookmark: Bookmark)
}