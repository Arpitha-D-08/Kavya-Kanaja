package com.example.kavyakanaja.data

import android.util.Log
import com.google.firebase.database.*

object FirebasePoemManager {

    private val database =
        FirebaseDatabase.getInstance()
            .reference
            .child("poems")

    fun fetchPoems(
        onResult: (List<Poem>) -> Unit
    ) {

        database.addListenerForSingleValueEvent(

            object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    Log.d("FIREBASE", snapshot.value.toString())

                    val poemList = mutableListOf<Poem>()

                    for (child in snapshot.children) {

                        val poem =
                            child.getValue(Poem::class.java)

                        if (poem != null) {

                            poemList.add(poem)
                        }
                    }

                    Log.d(
                        "FIREBASE",
                        "Poems Loaded = ${poemList.size}"
                    )

                    onResult(poemList)
                }

                override fun onCancelled(error: DatabaseError) {

                    Log.e(
                        "FIREBASE",
                        error.message
                    )
                }
            }
        )
    }
}