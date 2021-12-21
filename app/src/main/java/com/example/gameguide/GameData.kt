package com.example.gameguide

import android.os.Parcelable
import com.example.gameguide.data.Genres
import com.example.gameguide.data.Platforms
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameData (val title: String,
                     val rating:String,
                     val metacritic:Int,
                     val released:String,
                     val Background:String,
                     val playtime:String,
                     val ratingsCount:Int/*,
                 val genres:List<Genres>,
                 val platform:List<Platforms>,*/
                 ):Parcelable