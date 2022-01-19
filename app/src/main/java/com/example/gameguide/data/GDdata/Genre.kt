package com.example.gameguide.data.GDdata

import kotlinx.android.parcel.Parcelize

data class Genre(
    val games_count: Int,
    val id: Int,
    val image_background: String,
    override val name: String,
    val slug: String
): namee