package com.example.gameguide.homepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameguide.data.Json4Kotlin_Base
import com.example.gameguide.network.GameRepo
import kotlinx.coroutines.launch

class HomeVM: ViewModel(){
    val repo = GameRepo()
    fun fetchIntrestingList(pagenumber: String): LiveData<Json4Kotlin_Base>{
       val games = MutableLiveData<Json4Kotlin_Base>()
       viewModelScope.launch {
           try {
               games.postValue(repo.fetchInterestingList(pagenumber))
           }catch (e: Throwable){
               Log.e("game data","problem: ${e.localizedMessage}")
           }
       }
        return games
    }
}