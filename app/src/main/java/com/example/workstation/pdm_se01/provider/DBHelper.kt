package com.example.workstation.pdm_se01.provider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by workstation on 01/12/2016.
 */

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DbSchema.DB_NAME, null, DbSchema.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        createDb(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        deleteDb(db)
        createDb(db)
    }

    private fun createDb(db: SQLiteDatabase?) {

        db!!.execSQL(DbSchema.Weather.DDL_CREATE_TABLE)
        db.execSQL(DbSchema.Forecast.DDL_CREATE_TABLE)
/*      val cmd = "INSERT INTO " + DbSchema.Weather.TBL_NAME + " VALUES (" +
                1 + "," + "'Data_query1'" +
                ");"
        db.execSQL(cmd)*/
    }

    private fun deleteDb(db: SQLiteDatabase?) {
        db!!.execSQL(DbSchema.Weather.DDL_DROP_TABLE)
        db.execSQL(DbSchema.Forecast.DDL_DROP_TABLE)
    }
}