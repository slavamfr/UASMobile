package com.faiz.uas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.faiz.uas.admin.AdminHomeActivity
import com.faiz.uas.database.AppDatabase
import com.faiz.uas.databinding.ActivityLoginBinding
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username and Password must be filled", Toast.LENGTH_SHORT).show()
            } else {
                // Check admin credentials
                if (username == "admin" && password == "admin1234") {
                    val intent = Intent(this, AdminHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    runBlocking {
                        val user = AppDatabase.getDatabase(this@LoginActivity).userDao().getUserByUsername(username)
                        if (user != null && user.password == password) {
                            prefManager.saveUserData(user.username, user.email, user.phone)
                            prefManager.setActiveUser(user.username)

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        // Navigate to RegisterActivity
        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}