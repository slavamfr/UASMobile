package com.faiz.retrofitapi.network

import com.faiz.retrofitapi.model.Comic
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // GET request untuk mendapatkan daftar komik
    @GET("oZVr0/comic/")
    fun getComicList(): Call<List<Comic>>

    // POST request untuk menambahkan komik baru
    @POST("oZVr0/comic/")
    fun addComic(@Body comic: Comic): Call<Comic>

    // GET request untuk mendapatkan detail komik berdasarkan id
    @GET("oZVr0/comic/{id}")
    fun getComicDetails(@Path("id") id: String): Call<Comic>

    // POST request untuk memperbarui data komik berdasarkan id
    @POST("oZVr0/comic/{id}")
    fun updateComic(@Path("id") id: String, @Body comic: Comic): Call<Comic>

    // DELETE request untuk menghapus komik berdasarkan id
    @DELETE("oZVr0/comic/{id}")
    fun deleteComic(@Path("id") id: String): Call<Void>
}
