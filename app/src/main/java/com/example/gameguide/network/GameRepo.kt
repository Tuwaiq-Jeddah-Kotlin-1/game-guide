package com.example.gameguide.network

import com.example.gameguide.data.Json4Kotlin_Base
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepo {

    private val api = GameBuilder.Game_API

    suspend fun fetchInterestingList(pageNumber: String): Json4Kotlin_Base = withContext(Dispatchers.IO){
        api.fetchContent(pageNumber)
    }
    /*suspend fun searchPhotos(searchKeyWord: String): Json4Kotlin_Base = withContext(Dispatchers.IO){
        api.searchQuery(searchKeyWord)
    }*/
}