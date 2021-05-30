package com.google.codelabs.mdc.kotlin.shrine.database

class AnimeModel(
        val id: String, val title_en: String, val title_jp: String,
        val synopsis: String, val status: String, val ratingRank : String,
        val subtype: String, val nb_episode: String, val start_date: String,
        val end_date: String, val url: String, val coverImage: String
)
