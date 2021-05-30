package com.google.codelabs.mdc.kotlin.shrine.database


import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "animes"
            val COLUMN_ID = "id"
            val COLUMN_TITLE_EN = "title_en"
            val COLUMN_TITLE_JP = "title_jp"
            val COLUMN_SYNOPSIS = "synopsis"
            val COLUMN_STATUS = "status"
            val COLUMN_RATE = "ratingRank"
            val COLUMN_SUBTYPE = "subtype"
            val COLUMN_NB_EPISODE = "nbEpisode"
            val COLUMN_START_DATE = "startDate"
            val COLUMN_END_DATE = "endDate"
            val COLUMN_POSTER= "posterImage"
            val COLUMN_COVER = "coverImage"
        }
    }
}
