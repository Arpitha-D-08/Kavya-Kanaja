package com.example.kavyakanaja.data

import androidx.compose.runtime.mutableStateListOf

object RecentAudioManager {

    val recentAudios = mutableStateListOf<Poem>()

    fun addPoem(poem: Poem) {

        recentAudios.remove(poem)

        recentAudios.add(0, poem)

        if (recentAudios.size > 10) {

            recentAudios.removeLast()
        }
    }
}

