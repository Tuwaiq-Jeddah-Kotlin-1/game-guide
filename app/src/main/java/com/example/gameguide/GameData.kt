package com.example.gameguide

import android.os.Parcelable
import com.example.gameguide.data.Parent_platforms
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class GameData(val id:Int
    /*val title: String,
    val rating: String,
    val metacritic: Int,
    val released: String,
    val Background: String,
    val playtime: String,
    val ratingsCount: Int,
    val domin: String,*//*,
                 val genres:List<Genres>,
                 val platform:List<Platforms>,*//*

    val pPlatform:@RawValue  List<Parent_platforms>?*/

):Parcelable