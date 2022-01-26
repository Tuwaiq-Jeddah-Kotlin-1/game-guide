package com.example.gameguide.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GameBuilder {
    private const val BASE_URL = "https://api.rawg.io/api/"
    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val Game_API: GameApi = retrofit().create(GameApi::class.java)
}