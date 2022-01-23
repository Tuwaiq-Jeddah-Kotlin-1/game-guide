package com.example.gameguide.network

import com.example.gameguide.data.GDdata.GameDetailsdata
import com.example.gameguide.data.Json4Kotlin_Base
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
const val key = "33696672b6694a45b50b82f503395fe6"

interface GameApi {
    @GET("games?key=$key")
    suspend fun fetchContent(@Query("page")pageNumber: String) : Json4Kotlin_Base

    @GET("games/{id}?key=$key")
    suspend fun gamesContent(@Path("id")gameId: Int) : GameDetailsdata

    @GET("games?key=$key")
    suspend fun searchQuery(@Query("search")searchKeyWord: String/*, @Query("page")pageNumber: String*/) : Json4Kotlin_Base

}