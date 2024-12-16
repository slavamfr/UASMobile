package com.faiz.retrofitapi.model

import com.google.gson.annotations.SerializedName

data class Comic(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("gambar")
    val gambar: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("deskripsi")
    val deskripsi: String
)

