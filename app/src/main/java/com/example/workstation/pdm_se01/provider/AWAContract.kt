package com.example.workstation.pdm_se01.provider

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by workstation on 08/12/2016.
 */
object AWAContract {

    public val AUTHORITY = "com.example.workstation.pdm_se01"

    val CONTENT_URI = Uri.parse("content://" + AUTHORITY)

    val MEDIA_BASE_SUBTYPE = "/vnd.weather."

    object Weather : BaseColumns {

        val RESOURCE = "weather"

        val CONTENT_URI = Uri.withAppendedPath(
                AWAContract.CONTENT_URI,
                RESOURCE)

/*        val CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +
                        MEDIA_BASE_SUBTYPE + RESOURCE

        val CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +
                        MEDIA_BASE_SUBTYPE + RESOURCE*/

        /* Columns */
        val DATA = "data"
        val COUNTRY = "country"
        val CITY = "city"
        val FAV = "fav"

        /* Projections */
        val SELECT_ALL = arrayOf(BaseColumns._ID, DATA)

        val DEFAULT_SORT_ORDER = BaseColumns._ID + " ASC"

        /* Uri resource builders */
        fun getWeatherList(): Uri {
            return CONTENT_URI
        }

        fun getWeatherByLocation(country: String, city: String):Uri{
            return Uri.withAppendedPath(CONTENT_URI,country + "_" + city)
        }

    }
}
