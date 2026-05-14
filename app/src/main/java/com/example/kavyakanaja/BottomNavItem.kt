package com.example.kavyakanaja

sealed class BottomNavItem(
    val title: String
) {

    object Home : BottomNavItem("Home")

    object Poems : BottomNavItem("Poems")

    object Favorites : BottomNavItem("Favorites")

    object Audio : BottomNavItem("Audio")

    object Poets : BottomNavItem("Poets")

    object ChatBot : BottomNavItem("AI")
}

