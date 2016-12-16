package com.example.workstation.pdm_se01.network.api

import android.content.Context

import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.example.workstation.pdm_se01.network.SingletonRequest
import com.example.workstation.pdm_se01.provider.contract.AWAContract
import com.example.workstation.pdm_se01.utils.QueryRegist

import java.util.Locale

/**
 * Created by workstation on 31/10/2016.
 */

abstract class API(internal var context: Context) {

    protected val API_KEY = "3b23922cd82d67ca2db1f9d0e189dc9a"

    protected val BASE_URL = "http://api.openweathermap.org"

    protected val lang: String

    internal var queue: RequestQueue

    var contract : AWAContract ?= null

    companion object {
        private val loader: ImageLoader? = null
    }

    init {
        lang = Locale.getDefault().language
        queue = SingletonRequest.getInstance(context).initializeRequestQueue()
    }

    abstract fun get(reg: QueryRegist, sucessHandler: Response.Listener<String>, errHandler: Response.ErrorListener)

    fun getImage(icon: String, target: NetworkImageView) {
        val imgurl = BASE_URL + "/img/w/" + icon
        target.setImageUrl(imgurl, loader)
    }
}