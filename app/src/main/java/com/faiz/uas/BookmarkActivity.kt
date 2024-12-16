package com.faiz.uas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.faiz.uas.adapter.BookmarkAdapter
import com.faiz.uas.database.AppDatabase
import com.faiz.uas.database.Bookmark
import com.faiz.uas.databinding.ActivityBookmarkBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadBookmarks()
        setupBottomNavigation()
    }

    private fun loadBookmarks() {
        val sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val loggedInUser = sharedPreferences.getString("USERNAME", "User") ?: "User"

        GlobalScope.launch {
            val bookmarks = AppDatabase.getDatabase(this@BookmarkActivity).bookmarkDao().getBookmarksByUsername(loggedInUser)
            runOnUiThread {
                setupRecyclerView(bookmarks.toMutableList())
            }
        }
    }

    private fun setupRecyclerView(bookmarks: MutableList<Bookmark>) {
        bookmarkAdapter = BookmarkAdapter(bookmarks)
        binding.rvBookmarks.apply {
            adapter = bookmarkAdapter
            layoutManager = LinearLayoutManager(this@BookmarkActivity)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_bookmarks -> {
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