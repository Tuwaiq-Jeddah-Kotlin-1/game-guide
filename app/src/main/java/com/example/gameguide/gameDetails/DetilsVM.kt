package com.example.gameguide.gameDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameguide.data.GDdata.GameDetailsdata
import com.example.gameguide.data.Json4Kotlin_Base
import com.example.gameguide.network.GameRepo
import kotlinx.coroutines.launch

class DetilsVM : ViewModel(){
    val repo = GameRepo()
    fun gamesDetails(GameId: Int): LiveData<GameDetailsdata> {
        val games = MutableLiveData<GameDetailsdata>()
        viewModelScope.launch {
            try {
                games.postValue(repo.gamesDetails(GameId))
            }catch (e: Throwable){
                Log.e("game details","problem: ${e.localizedMessage}")
            }
        }
        return games
    }
}