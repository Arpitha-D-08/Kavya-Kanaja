package com.example.kavyakanaja.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonHelper {

    fun loadPoems(context: Context): List<Poem> {

        val jsonString =
            context.assets.open("poems.json")
                .bufferedReader()
                .use { it.readText() }

        val type =
            object : TypeToken<List<Poem>>() {}.type

        return Gson().fromJson(jsonString, type)
    }
}