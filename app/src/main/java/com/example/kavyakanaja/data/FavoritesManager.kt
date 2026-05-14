package com.example.kavyakanaja.data

import android.content.Context
import androidx.compose.runtime.mutableStateListOf

object FavoritesManager {

    val favoritePoems =
        mutableStateListOf<Poem>()

    fun loadFavorites(
        context: Context
    ) {

        favoritePoems.clear()

        favoritePoems.addAll(

            FavoriteStorage
                .loadFavorites(context)
        )
    }

    fun addToFavorites(

        context: Context,

        poem: Poem

    ) {

        if (!favoritePoems.contains(poem)) {

            favoritePoems.add(poem)

            FavoriteStorage.saveFavorites(

                context,

                favoritePoems
            )
        }
    }

    fun removeFavorite(

        context: Context,

        poem: Poem

    ) {

        favoritePoems.remove(poem)

        FavoriteStorage.saveFavorites(

            context,

            favoritePoems
        )
    }

    fun clearFavorites(
        context: Context
    ) {

        favoritePoems.clear()

        FavoriteStorage.saveFavorites(

            context,

            favoritePoems
        )
    }
}