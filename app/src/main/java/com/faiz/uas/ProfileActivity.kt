package com.faiz.uas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.faiz.uas.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve user data from SharedPreferences
        val sharedPreferences = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", "User")
        val email = sharedPreferences.getString("EMAIL", "email@example.com")
        val phone = sharedPreferences.getString("PHONE", "081234567890")

        // Display user data in TextView
        binding.txtUserNameProfile.text = username
        binding.txtProfileUserName.text = username
        binding.txtProfileEmail.text = email
        binding.txtProfilePhone.text = phone

        setupBottomNavigation()
        setupLogoutButton()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_bookmarks -> {
                    startActivity(Intent(this, BookmarkActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Current activity
                    true
                }
                else -> false
            }
        }
    }

    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}