package com.faiz.uas

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Simpan data string
    fun saveString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    // Ambil data string
    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    // Simpan data boolean
    fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    // Ambil data boolean
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // Hapus data tertentu
    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }

    // Hapus semua data
    fun clear() {
        editor.clear()
        editor.apply()
    }
}