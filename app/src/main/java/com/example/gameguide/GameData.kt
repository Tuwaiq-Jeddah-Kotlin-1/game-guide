package com.example.gameguide

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameData(
    val id: Int
) : Parcelable