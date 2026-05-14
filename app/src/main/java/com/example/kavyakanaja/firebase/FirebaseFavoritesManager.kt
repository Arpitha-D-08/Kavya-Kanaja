package com.example.kavyakanaja.firebase

import com.example.kavyakanaja.data.Poem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object FirebaseFavoritesManager {

    private val auth =
        FirebaseAuth.getInstance()

    private val database =
        FirebaseDatabase
            .getInstance()
            .reference

    fun saveFavoritePoem(
        poem: Poem
    ) {

        val userId =
            auth.currentUser?.uid ?: return

        database

            .child("favorites")

            .child(userId)

            .child(poem.title)

            .setValue(poem)
    }

    fun removeFromFavorites(
        poemTitle: String
    ) {

        val userId =
            auth.currentUser?.uid ?: return

        database

            .child("favorites")

            .child(userId)

            .child(poemTitle)

            .removeValue()
    }

    fun clearFavorites() {

        val userId =
            auth.currentUser?.uid ?: return

        database

            .child("favorites")

            .child(userId)

            .removeValue()
    }

    fun loadFavorites(

        onLoaded:
            (List<Poem>) -> Unit

    ) {

        val userId =
            auth.currentUser?.uid ?: return

        database

            .child("favorites")

            .child(userId)

            .addValueEventListener(

                object : ValueEventListener {

                    override fun onDataChange(
                        snapshot: DataSnapshot
                    ) {

                        val favorites =
                            mutableListOf<Poem>()

                        snapshot.children.forEach {

                            val poem =
                                it.getValue(
                                    Poem::class.java
                                )

                            poem?.let {

                                favorites.add(it)
                            }
                        }

                        onLoaded(favorites)
                    }

                    override fun onCancelled(
                        error: DatabaseError
                    ) {

                    }
                }
            )
    }
}