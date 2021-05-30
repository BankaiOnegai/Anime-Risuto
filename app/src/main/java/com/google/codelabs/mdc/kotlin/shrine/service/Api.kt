package com.google.codelabs.mdc.kotlin.shrine.service


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.*
import retrofit2.http.GET
import java.io.File
import java.io.IOException


class Api {
    @GET("anime")
    fun getEpisode(id: Int): JsonObject {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://api.jikan.moe/v3/anime/$id/episode")
                .build()
        client.newCall(request).execute()
                .use { response -> return JsonParser.parseString(response.body!!.string()).asJsonObject }
    }

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
        val activeNetwork: NetworkInfo? = (connectivityManager as ConnectivityManager).activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }


    fun getTrending(context: Context?): JsonObject {

        try {
            val client = OkHttpClient.Builder()
            val request = Request.Builder()
                    .url("https://kitsu.io/api/edge/trending/anime/")
                    .build()
            client.build().newCall(request).execute()
                    .use {
                        response -> return JsonParser.parseString(response.body!!.string()).asJsonObject
                    }
        } catch (e: IOException) {
            println("====================")
            println(e.message)
        }
        return JsonObject()
    }

    fun getAnime(id: String): JsonObject {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://kitsu.io/api/edge/anime/$id")
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
