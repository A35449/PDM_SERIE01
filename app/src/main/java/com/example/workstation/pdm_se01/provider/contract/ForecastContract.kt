package com.example.workstation.pdm_se01.provider.contract

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by workstation on 16/12/2016.
 */

object ForecastContract : AWAContract(){
    val RESOURCE = "forecast"

    init{
        CONTENT_URI = Uri.withAppendedPath(
                super.CONTENT_URI,
                RESOURCE)
    }

    /* Projections */
    val SELECT_ALL = arrayOf(BaseColumns._ID, DATA)

    val DEFAULT_SORT_ORDER = BaseColumns._ID + " ASC"

    /* Uri resource builders */
    override fun getAll(): Uri {
        return CONTENT_URI
    }

    override fun getById(country: String, city: String): Uri {
        return Uri.withAppendedPath(CONTENT_URI,country + "_" + city)
    }
}