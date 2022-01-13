package com.example.gameguide.network

import com.example.gameguide.data.GDdata.GameDetailsdata
import com.example.gameguide.data.Json4Kotlin_Base
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepo {

    private val api = GameBuilder.Game_API

    suspend fun fetchInterestingList(pageNumber: String): Json4Kotlin_Base = withContext(Dispatchers.IO){
        api.fetchContent(pageNumber)
    }
    suspend fun searchGames(searchKeyWord: String/*,pageNumber:String*/): Json4Kotlin_Base = withContext(Dispatchers.IO){
        api.searchQuery(searchKeyWord/*,pageNumber*/)
    }
    suspend fun gamesDetails(GameId: Int): GameDetailsdata = withContext(Dispatchers.IO){
        api.gamesContent(GameId)
    }
}