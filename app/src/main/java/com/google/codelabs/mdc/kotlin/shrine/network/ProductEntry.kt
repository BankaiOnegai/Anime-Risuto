package com.google.codelabs.mdc.kotlin.shrine.network

import com.google.codelabs.mdc.kotlin.shrine.service.Api
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

/**
 * A product entry in the list of products.
 */
class ProductEntry(
        val id: String, val title: String, val synopsis: String, val status: String, val nb_episode: String, val start_date: String, val url: String) {

    companion object {
        /**
         * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
         */
        fun initProductEntryList(): List<ProductEntry> {
            val api = Api()
            val data = api.getTrending().getAsJsonArray("data")
            val mangas: ArrayList<ProductEntry> = ArrayList()

            data.forEach {
                val id = it.asJsonObject.get("id").asString
                val attributes = it.asJsonObject.getAsJsonObject("attributes")
                println(attributes)
                val title = attributes.get("canonicalTitle").asString
                val synopsis = attributes.get("synopsis").asString
                val status =  if (attributes.get("status") is JsonNull) "0" else attributes.get("status").asString
                val nbEpisode = if (attributes.get("episodeCount") is JsonNull) "0" else attributes.get("episodeCount").asString

                val startDate = attributes.get("startDate").asString
                val posterImage = attributes.asJsonObject.getAsJsonObject("posterImage").get("tiny").asString
                val coverImage = attributes.getAsJsonObject("coverImage").get("tiny").asString
                /*
                println("id " + id)
                println("title " + title)
                println("synopsis " + synopsis)
                println("status " + status)
                println("nbEpisode " + nbEpisode)
                println("startDate " + startDate)
                println("image " + posterImage)
                println("coverImage " + coverImage)
                 */
                mangas.add(ProductEntry(id, title, synopsis, status, nbEpisode, startDate, posterImage))
            }
            return mangas
        }

        fun getAnime(id: Int): JsonObject {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://kitsu.io/api/edge/anime/$id")
                    .build()
            client.newCall(request).execute()
                    .use { response -> return JsonParser.parseString(response.body!!.string()).asJsonObject }
        }

        fun getEpisode(id: Int): JsonObject {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://kitsu.io/api/edge/anime/$id/episode")
                    .build()
            client.newCall(request).execute()
                    .use { response -> return JsonParser.parseString(response.body!!.string()).asJsonObject }
        }

        fun getPopularAnime(): JsonObject {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://kitsu.io/api/edge/trending/anime")
                    .build()
            client.newCall(request).execute()
                    .use { response -> return JsonParser.parseString(response.body!!.string()).asJsonObject }
        }
    }
}