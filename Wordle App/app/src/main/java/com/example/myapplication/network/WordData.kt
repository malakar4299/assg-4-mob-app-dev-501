package com.example.myapplication.network

import com.squareup.moshi.Json

data class WordData (
    @Json(name = "title") val title: String?,
    @Json(name = "word") val word: String?
)