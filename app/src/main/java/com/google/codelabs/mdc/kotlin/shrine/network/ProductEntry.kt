package com.google.codelabs.mdc.kotlin.shrine.network

import android.content.Context
import com.google.codelabs.mdc.kotlin.shrine.service.Api
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import java.util.*

/**
 * A product entry in the list of products.
 */
class ProductEntry(
        val id: String, val title_en: String, val title_jp: String, val synopsis: String, val status: String, val ratingRank : String,val subtype: String, val nb_episode: String, val start_date: String, val end_date: String, val url: String, val coverImage: String) {

    companion object {

        fun parseProductEntry(json: JsonElement): ProductEntry {
            val id = json.asJsonObject.get("id").asString
            val attributes = json.asJsonObject.getAsJsonObject("attributes")
            val titles = attributes.asJsonObject.getAsJsonObject("titles")
            val title_en = if (titles.get("en") is JsonNull) {titles.get("en_jp").asString} else attributes.get("canonicalTitle").asString
            val title_jp  = titles.get("ja_jp").asString
            val synopsis = attributes.get("synopsis").asString
            val status = if (attributes.get("status") is JsonNull) "0" else attributes.get("status").asString
            val ratingRank = if (attributes.get("ratingRank") is JsonNull) "0" else attributes.get("ratingRank").asString
            val subtype = if (attributes.get("subtype") is JsonNull) "0" else attributes.get("subtype").asString
            val nbEpisode = if (attributes.get("episodeCount") is JsonNull) "0" else attributes.get("episodeCount").asString
            val startDate = attributes.get("startDate").asString
            val endDate = if (attributes.get("endDate") is JsonNull) "?" else attributes.get("endDate").asString
            val posterImage = attributes.asJsonObject.getAsJsonObject("posterImage").get("large").asString
            val coverImage = attributes.getAsJsonObject("coverImage").get("original").asString
            return ProductEntry(
                    id = id, title_en = title_en, title_jp = title_jp, synopsis = synopsis, status = status,
                    ratingRank = ratingRank,subtype = subtype, nb_episode = nbEpisode, start_date = startDate,
                    end_date = endDate, url = posterImage, coverImage = coverImage
            )
        }

        /**
         * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
         */
        fun initProductEntryList(context: Context?): List<ProductEntry> {
            val api = Api()
            val data = api.getTrending(context).getAsJsonArray("data")
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