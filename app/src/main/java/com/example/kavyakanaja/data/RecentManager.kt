package com.example.kavyakanaja.data

object RecentManager {

    private val recentPoems = mutableListOf<Poem>()

    fun addPoem(poem: Poem) {

        recentPoems.remove(poem)

        recentPoems.add(0, poem)

        if (recentPoems.size > 10) {

            recentPoems.removeAt(recentPoems.size - 1)
        }
    }

    fun getRecentPoems(): List<Poem> {

        return recentPoems
    }
}