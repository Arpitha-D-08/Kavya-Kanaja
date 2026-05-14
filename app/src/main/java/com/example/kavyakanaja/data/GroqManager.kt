package com.example.kavyakanaja.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object GroqManager {

    private const val API_KEY = "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"

    suspend fun getResponse(prompt: String): String {

        return withContext(Dispatchers.IO) {

            try {

                val client = OkHttpClient()

                val safePrompt =
                    prompt
                        .replace("\n", " ")
                        .replace("\"", "\\\"")

                val jsonBody = """
{
    "model": "llama-3.3-70b-versatile",
    "messages": [
        {
            "role": "user",
            "content": "$safePrompt"
        }
    ]
}
"""

                val body = jsonBody.toRequestBody(
                    "application/json".toMediaType()
                )

                val request = Request.Builder()
                    .url("https://api.groq.com/openai/v1/chat/completions")
                    .addHeader("Authorization", "Bearer $API_KEY")
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()

                val responseText = response.body?.string()

                if (responseText == null) {
                    return@withContext "No response from server"
                }

                val jsonObject = JSONObject(responseText)

                if (jsonObject.has("error")) {
                    return@withContext jsonObject
                        .getJSONObject("error")
                        .getString("message")
                }

                jsonObject
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")

            } catch (e: Exception) {
                "Error: ${e.message}"
            }
        }
    }
}