package com.faiz.uas

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Save user data
    fun saveUserData(username: String, email: String, phone: String) {
        editor.putString("USERNAME_$username", username)
        editor.putString("EMAIL_$username", email)
        editor.putString("PHONE_$username", phone)
        editor.apply()
    }

    // Get user data
    fun getUserData(username: String): Map<String, String?> {
        return mapOf(
            "USERNAME" to sharedPreferences.getString("USERNAME_$username", null),
            "EMAIL" to sharedPreferences.getString("EMAIL_$username", null),
            "PHONE" to sharedPreferences.getString("PHONE_$username", null)
        )
    }

    // Set active user
    fun setActiveUser(username: String) {
        editor.putString("ACTIVE_USER", username)
        editor.apply()
    }

    // Get active user
    fun getActiveUser(): String? {
        return sharedPreferences.getString("ACTIVE_USER", null)
    }

    // Remove user data
    fun clearUserData(username: String) {
        editor.remove("USERNAME_$username")
        editor.remove("EMAIL_$username")
        editor.remove("PHONE_$username")
        editor.apply()
    }

    // Clear all data
    fun clear() {
        editor.clear()
        editor.apply()
    }
}