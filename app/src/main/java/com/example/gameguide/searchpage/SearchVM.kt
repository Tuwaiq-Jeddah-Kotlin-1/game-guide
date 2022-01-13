package com.example.gameguide.searchpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameguide.data.Json4Kotlin_Base
import com.example.gameguide.network.GameRepo
import kotlinx.coroutines.launch

class SearchVM: ViewModel() {

    val repo = GameRepo()
    fun searchGames(searchKeyWord: String?/*,pageNumber:String*/): LiveData<Json4Kotlin_Base> {
        val game = MutableLiveData<Json4Kotlin_Base>()
        viewModelScope.launch {
            try {
                if (searchKeyWord.isNullOrBlank()){
                    game.postValue(repo.fetchInterestingList("1"))
                } else{
                    game.postValue(repo.searchGames(searchKeyWord/*,pageNumber*/))
                }

            } catch (e: Throwable){
                Log.e("game data","issue in game data: ${e.localizedMessage}")
            }
        }
        return game
    }

}