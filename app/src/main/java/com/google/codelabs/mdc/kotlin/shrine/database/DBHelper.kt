package com.google.codelabs.mdc.kotlin.shrine.database


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertAnime(user: AnimeModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_ID, user.id)
        values.put(DBContract.UserEntry.COLUMN_TITLE_EN, user.title_en)
        values.put(DBContract.UserEntry.COLUMN_TITLE_JP, user.title_jp)
        values.put(DBContract.UserEntry.COLUMN_SYNOPSIS, user.synopsis)
        values.put(DBContract.UserEntry.COLUMN_STATUS, user.status)
        values.put(DBContract.UserEntry.COLUMN_RATE, user.ratingRank)
        values.put(DBContract.UserEntry.COLUMN_SUBTYPE, user.subtype)
        values.put(DBContract.UserEntry.COLUMN_NB_EPISODE, user.nb_episode)
        values.put(DBContract.UserEntry.COLUMN_START_DATE, user.start_date)
        values.put(DBContract.UserEntry.COLUMN_END_DATE, user.end_date)
        values.put(DBContract.UserEntry.COLUMN_POSTER, user.url)
        values.put(DBContract.UserEntry.COLUMN_COVER, user.coverImage)
        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteAnime(id: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(id)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readAnime(id: String): ArrayList<AnimeModel> {
        val anime = ArrayList<AnimeModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_ID + "='" + id + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var title_en: String
        var title_jp: String
        var synopsis: String
        var status: String
        var ratingRank : String
        var subtype: String
        var nb_episode: String
        var start_date: String
        var end_date: String
        var url: String
        var coverImage: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                title_en = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TITLE_EN))
                title_jp = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TITLE_JP))
                synopsis = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SYNOPSIS))
                status = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_STATUS))
                ratingRank = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_RATE))
                subtype = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SUBTYPE))
                nb_episode = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NB_EPISODE))
                start_date = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_START_DATE))
                end_date = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_END_DATE))
                url = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_POSTER))
                coverImage = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_COVER))

                anime.add(AnimeModel(id, title_en, title_jp, synopsis, status, ratingRank, subtype, nb_episode, start_date, end_date, url, coverImage))
                cursor.moveToNext()
            }
        }
        return anime
    }

    fun readAllAnime(): ArrayList<AnimeModel> {
        val anime = ArrayList<AnimeModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var title_en: String
        var title_jp: String
        var synopsis: String
        var status: String
        var ratingRank : String
        var subtype: String
        var nb_episode: String
        var start_date: String
        var end_date: String
        var url: String
        var coverImage: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ID))
                title_en = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TITLE_EN))
                title_jp = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_TITLE_JP))
                synopsis = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SYNOPSIS))
                status = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_STATUS))
                ratingRank = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_RATE))
                subtype = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SUBTYPE))
                nb_episode = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NB_EPISODE))
                start_date = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_START_DATE))
                end_date = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_END_DATE))
                url = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_POSTER))
                coverImage = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_COVER))

                anime.add(AnimeModel(id, title_en, title_jp, synopsis, status, ratingRank, subtype, nb_episode, start_date, end_date, url, coverImage))
                cursor.moveToNext()
            }
        }
        return anime
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Animes.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                        DBContract.UserEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DBContract.UserEntry.COLUMN_TITLE_EN + " TEXT," +
                        DBContract.UserEntry.COLUMN_TITLE_JP + " TEXT," +
                        DBContract.UserEntry.COLUMN_SYNOPSIS + " TEXT," +
                        DBContract.UserEntry.COLUMN_STATUS + " TEXT," +
                        DBContract.UserEntry.COLUMN_RATE + " TEXT," +
                        DBContract.UserEntry.COLUMN_SUBTYPE + " TEXT," +
                        DBContract.UserEntry.COLUMN_NB_EPISODE + " TEXT," +
                        DBContract.UserEntry.COLUMN_START_DATE + " TEXT," +
                        DBContract.UserEntry.COLUMN_END_DATE + " TEXT," +
                        DBContract.UserEntry.COLUMN_POSTER + " TEXT," +
                        DBContract.UserEntry.COLUMN_COVER + " TEXT)"
        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }

}