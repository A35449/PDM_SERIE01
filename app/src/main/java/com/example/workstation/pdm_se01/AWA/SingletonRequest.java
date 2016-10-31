package com.example.workstation.pdm_se01.AWA;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by workstation on 29/10/2016.
 */

public class SingletonRequest {

    private static SingletonRequest srInstance;
    private RequestQueue srRequest;
    private static Context srContext;
    private ImageLoader srImageLoader;
    private SingletonRequest(Context context){
        srContext = context;
        srRequest = initializeRequestQueue();

        srImageLoader = new ImageLoader(srRequest, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized SingletonRequest getInstance(Context context){
        if(srInstance == null) {
            srInstance = new SingletonRequest(context);
        }
        return srInstance;
    }

    public RequestQueue initializeRequestQueue(){
        if(srRequest == null){
            srRequest = Volley.newRequestQueue(srContext.getApplicationContext());
        }
        return srRequest;
    }

    public ImageLoader getImageLoader() {
        return srImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req){
        initializeRequestQueue().add(req);
    }
}
