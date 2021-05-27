package com.google.codelabs.mdc.kotlin.shrine.network

import com.google.codelabs.mdc.kotlin.shrine.service.Api
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import java.util.*

/**
 * A product entry in the list of products.
 */
class ProductEntry(
        val id: String, val title: String, val synopsis: String, val status: String, val nb_episode: String, val start_date: String, val url: String) {

    companion object {

        fun parseProductEntry(json: JsonElement): ProductEntry {
            val id = json.asJsonObject.get("id").asString
            val attributes = json.asJsonObject.getAsJsonObject("attributes")
            val title = attributes.get("canonicalTitle").asString
            val synopsis = attributes.get("synopsis").asString
            val status = if (attributes.get("status") is JsonNull) "0" else attributes.get("status").asString
            val nbEpisode = if (attributes.get("episodeCount") is JsonNull) "0" else attributes.get("episodeCount").asString
            val startDate = attributes.get("startDate").asString
            val posterImage = attributes.asJsonObject.getAsJsonObject("posterImage").get("tiny").asString
            val coverImage = attributes.getAsJsonObject("coverImage").get("tiny").asString
            return ProductEntry(
                    id = id, title = title, synopsis = synopsis, status = status,
                    nb_episode = nbEpisode, start_date = startDate, url = posterImage
            )
        }

        /**
         * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
         */
        fun initProductEntryList(): List<ProductEntry> {
            val api = Api()
            val data = api.getTrending().getAsJsonArray("data")
            val mangas: ArrayList<ProductEntry> = ArrayList()
            data.forEach {
                mangas.add(parseProductEntry(it))
            }
            return mangas
        }

        fun getProductEntry(id: String): ProductEntry {
            val api = Api()
            val data = api.getAnime(id).getAsJsonObject("data")
            return parseProductEntry(data)
        }
    }
}