package com.example.workstation.pdm_se01.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils

/**
 * Created by workstation on 01/12/2016.
 */

class AWAProvider : ContentProvider() {

    private val WEA_LIST = 1
    private val WEA_OBJ = 2
    private val URI_MATCHER: UriMatcher

    init{
        URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        URI_MATCHER.addURI(
                AWAContract.AUTHORITY,
                AWAContract.Weather.RESOURCE,
                WEA_LIST)
        URI_MATCHER.addURI(
                AWAContract.AUTHORITY,
                AWAContract.Weather.RESOURCE + "/#",
                WEA_OBJ)
    }

    private var dbHelper: DBHelper? = null

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context)
        return true
    }

    override fun getType(uri: Uri?): String {
/*        when (URI_MATCHER.match(uri)) {
            WEA -> return WeatherDB.Weather.CONTENT_TYPE
            CTY -> return WeatherDB.Weather.CONTENT_ITEM_TYPE
            else -> throw badUri(uri!!)
        }*/
        throw UnsupportedOperationException("getType is not implemented");
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val table: String
        when (URI_MATCHER.match(uri)) {
            WEA_LIST -> { table = DbSchema.Weather.TBL_NAME }
            WEA_OBJ -> { table = DbSchema.Weather.TBL_NAME}
            else -> throw badUri(uri)

        }

        val db = dbHelper!!.writableDatabase
        val newId = db.insert(table, null, values)

        context.contentResolver.notifyChange(uri, null)
        return ContentUris.withAppendedId(uri, newId)
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val table: String
        when (URI_MATCHER.match(uri)) {
            WEA_OBJ -> {
                table = DbSchema.Weather.TBL_NAME
                if (selection != null) {
                    throw IllegalArgumentException("selection not supported")
                }
            }
            else -> throw badUri(uri)
        }

        val db = dbHelper!!.writableDatabase
        val ndel = db.delete(table, null, null)

        context.contentResolver.notifyChange(uri, null)
        return ndel
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        var sortOrder = sortOrder
        val qbuilder = SQLiteQueryBuilder()
        when (URI_MATCHER.match(uri)) {
            WEA_LIST -> {
                qbuilder.tables = DbSchema.Weather.TBL_NAME
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = AWAContract.Weather.DEFAULT_SORT_ORDER
                }
            }
            WEA_OBJ -> {
                qbuilder.tables = DbSchema.Weather.TBL_NAME
                qbuilder.appendWhere(DbSchema.COL_ID + "=" + uri.lastPathSegment)
            }
            else -> badUri(uri)
        }

        val db = dbHelper!!.readableDatabase
        val cursor = qbuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        cursor.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    private fun badUri(uri: Uri): Exception {
        throw IllegalArgumentException("Unsupported URI: " + uri)
    }


}