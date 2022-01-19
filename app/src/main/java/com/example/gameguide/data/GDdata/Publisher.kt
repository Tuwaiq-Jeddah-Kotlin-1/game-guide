package com.example.gameguide.data.GDdata

data class Publisher(
    val games_count: Int,
    val id: Int,
    val image_background: String,
    override val name: String,
    val slug: String
):Name