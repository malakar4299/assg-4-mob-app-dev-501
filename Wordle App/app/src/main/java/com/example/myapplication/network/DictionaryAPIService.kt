package com.example.myapplication.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val BASE_URL =
    "https://api.dictionaryapi.dev/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

class DictionaryAPIService {


    public object DictionaryAPI {
        val retrofitService : DictionaryAPIService by lazy {
            retrofit.create(DictionaryAPIService::class.java)
        }
    }

    interface DictionaryAPIService {
        @GET("entries/en/{word}")
        suspend fun getWord(@Path("word") word:String): List<WordData>
    }

}