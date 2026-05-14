package com.example.kavyakanaja.data

object WordMeaningManager {

    suspend fun getMeaning(word: String): String {

        val prompt = """
            Give only one-word English meaning for this Kannada word:
            $word
        """.trimIndent()

        return GroqManager.getResponse(prompt)
    }
}

