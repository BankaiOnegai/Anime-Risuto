package com.google.codelabs.mdc.kotlin.shrine.service


import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.http.GET
import java.io.IOException

class Api {
    @GET("anime")
    fun getAnime(id: Int): JsonObject {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://api.jikan.moe/v3/anime/$id")
                .build()
        client.newCall(request).execute()
                .use { response -> return JsonParser.parseString(response.body!!.string()).asJsonObject }
    }

    fun getEpisode(id: Int): JsonObject {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://api.jikan.moe/v3/anime/$id/episode")
                .build()
        client.newCall(request).execute()
                .use { response -> return JsonParser.parseString(response.body!!.string()).asJsonObject }
    }

    fun getTrending(): JsonObject {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://kitsu.io/api/edge/trending/anime/")
                    .build()
            client.newCall(request).execute()
                    .use { response -> return JsonParser.parseString(response.body!!.string()).asJsonObject }
        } catch (e: IOException) {
            println("====================")
            println(e.message)
        }
        return JsonObject()
    }

}
