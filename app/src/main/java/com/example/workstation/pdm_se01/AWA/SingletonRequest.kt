package com.example.workstation.pdm_se01.AWA

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

/**
 * Created by workstation on 29/10/2016.
 */

class SingletonRequest private constructor(context: Context) {
    private var srRequest: RequestQueue? = null
    val imageLoader: ImageLoader

    init {
        srContext = context
        srRequest = initializeRequestQueue()

        imageLoader = ImageLoader(srRequest, object : ImageLoader.ImageCache {
            private val cache = LruCache<String, Bitmap>(20)

            override fun getBitmap(url: String): Bitmap {
                return cache.get(url)
            }

            override fun putBitmap(url: String, bitmap: Bitmap) {
                cache.put(url, bitmap)
            }
        })
    }

    fun initializeRequestQueue(): RequestQueue {
        if (srRequest == null) {
            srRequest = Volley.newRequestQueue(srContext?.applicationContext)
        }
        return srRequest!!
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        initializeRequestQueue().add(req)
    }

    companion object {

        private var srInstance: SingletonRequest? = null
        private var srContext: Context? = null

        @Synchronized fun getInstance(context: Context): SingletonRequest {
            if (srInstance == null) {
                srInstance = SingletonRequest(context)
            }
            return srInstance!!
        }
    }
}
