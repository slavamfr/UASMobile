package com.faiz.uas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.faiz.uas.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)

        val activeUser = prefManager.getActiveUser()
        if (activeUser != null) {
            val userData = prefManager.getUserData(activeUser)
            binding.txtUserNameProfile.text = userData["USERNAME"]
            binding.txtProfileUserName.text = userData["USERNAME"]
            binding.txtProfileEmail.text = userData["EMAIL"]
            binding.txtProfilePhone.text = userData["PHONE"]
        }

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
            editor.remove("IS_LOGGED_IN")
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}