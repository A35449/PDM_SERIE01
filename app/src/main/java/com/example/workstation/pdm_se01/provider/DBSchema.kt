package com.example.workstation.pdm_se01.provider

import android.provider.BaseColumns

/**
 * Created by workstation on 01/12/2016.
 */


object DbSchema {

    val DB_NAME = "awa.db"
    val DB_VERSION = 1

    val COL_ID = BaseColumns._ID

    object Weather {
        val TBL_NAME = "weather"

        val COL_DATA = "data"

        val COL_CITY = "city"

        val COL_FAV =  "fav"

        val COL_COUNTRY = "country"

        val DDL_CREATE_TABLE =
            "CREATE TABLE " + TBL_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_DATA + " TEXT," +
                COL_CITY + " TEXT," +
                COL_COUNTRY + " TEXT" +
                COL_FAV + " BIT DEFAULT 0" +
            ")"

        val DDL_DROP_TABLE = "DROP TABLE IF EXISTS " + TBL_NAME
    }
}