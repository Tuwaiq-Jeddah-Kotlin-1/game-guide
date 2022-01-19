package com.example.gameguide.data.GDdata

data class Tag(
    val games_count: Int,
    val id: Int,
    val image_background: String,
    val language: String,
    override val name: String,
    val slug: String
) : namee