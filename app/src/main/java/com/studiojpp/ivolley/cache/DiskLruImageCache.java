package com.studiojpp.ivolley.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class DiskLruImageCache implements ImageLoader.ImageCache {

    private final LruCache<String, Bitmap> cache;
    private final Context mContext;

    public DiskLruImageCache(Context context) {
        mContext = context;
        cache = new LruCache<String, Bitmap>(20);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}
