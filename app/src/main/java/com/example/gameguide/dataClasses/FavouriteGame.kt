package com.example.gameguide.dataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavouriteGame(
    val id:Int = 0,
    val name: String="",
    val background:String= "",
    val ratings:String="",
    val parent_platforms:String="",
    val metacritic:Int = 0,
    val added:Int= 0,
    val genres:String="",
    val released:String= "",
    var visibility: Boolean = false
                        ):Parcelable
