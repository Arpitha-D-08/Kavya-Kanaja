package com.example.kavyakanaja.firebase

import com.google.firebase.database.FirebaseDatabase

object FirebaseDatabaseManager {

    private val database =
        FirebaseDatabase.getInstance()

    fun saveFavoritePoem(

        userId: String,

        poemTitle: String

    ) {

        database
            .reference
            .child("favorites")
            .child(userId)
            .push()
            .setValue(poemTitle)
    }
}

