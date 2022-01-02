package com.example.gameguide.dataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavouriteGame(val title: String = "",
                         val rating:String= "",
                         val metacritic:Int = 0,
                         val released:String= "",
                         val background:String= "",
                         val playtime:String= "",
                         val ratingsCount:Int= 0):Parcelable