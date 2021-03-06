package com.example.workstation.pdm_se01.provider.contract

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by workstation on 08/12/2016.
 */
abstract class AWAContract : BaseColumns {

    companion object{
        val AUTHORITY = "com.example.workstation.pdm_se01"
    }
    var CONTENT_URI = Uri.parse("content://" + AUTHORITY)

    abstract fun getAll(): Uri
    abstract fun getById(country: String, city: String): Uri
    abstract fun getData() : String
    abstract fun getFav() : String
    abstract fun getCountry() : String
    abstract fun getCity() : String
}
