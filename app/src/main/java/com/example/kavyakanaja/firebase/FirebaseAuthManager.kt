package com.example.kavyakanaja.firebase

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthManager {

    private val auth = FirebaseAuth.getInstance()

    fun loginUser(

        email: String,
        password: String,

        onSuccess: () -> Unit,

        onFailure: (String) -> Unit

    ) {

        auth.signInWithEmailAndPassword(
            email,
            password
        )

            .addOnSuccessListener {

                onSuccess()
            }

            .addOnFailureListener {

                onFailure(
                    it.message ?: "Login Failed"
                )
            }
    }

    fun registerUser(

        email: String,
        password: String,

        onSuccess: () -> Unit,

        onFailure: (String) -> Unit

    ) {

        auth.createUserWithEmailAndPassword(
            email,
            password
        )

            .addOnSuccessListener {

                onSuccess()
            }

            .addOnFailureListener {

                onFailure(
                    it.message ?: "Registration Failed"
                )
            }
    }
}

