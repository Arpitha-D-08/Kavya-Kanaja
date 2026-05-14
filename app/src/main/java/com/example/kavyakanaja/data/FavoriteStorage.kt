package com.example.kavyakanaja.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoriteStorage {

    private const val PREF_NAME =
        "favorites_pref"

    private const val KEY_FAVORITES =
        "favorites"

    fun saveFavorites(

        context: Context,

        favorites: List<Poem>

    ) {

        val sharedPreferences =

            context.getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE
            )

        val editor =
            sharedPreferences.edit()

        val gson = Gson()

        val json =
            gson.toJson(favorites)

        editor.putString(
            KEY_FAVORITES,
            json
        )

        editor.apply()
    }

    fun loadFavorites(

        context: Context

    ): MutableList<Poem> {

        val sharedPreferences =

            context.getSharedPreferences(

                PREF_NAME,

                Context.MODE_PRIVATE
            )

        val gson = Gson()

        val json =

            sharedPreferences.getString(

                KEY_FAVORITES,

                null
            )

        return if (json != null) {

            val type =

                object :
                    TypeToken<MutableList<Poem>>() {}.type

            gson.fromJson(json, type)

        } else {

            mutableListOf()
        }
    }
}

