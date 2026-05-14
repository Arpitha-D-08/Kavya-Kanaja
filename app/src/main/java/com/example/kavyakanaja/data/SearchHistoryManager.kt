package com.example.kavyakanaja.data

import androidx.compose.runtime.mutableStateListOf

object SearchHistoryManager {

    val searchHistory =
        mutableStateListOf<String>()

    fun addSearch(

        text: String
    ) {

        if (

            text.isNotBlank()

            &&

            !searchHistory.contains(text)

        ) {

            searchHistory.add(0, text)
        }
    }
    fun clearSearchHistory() {
        searchHistory.clear()
    }
}