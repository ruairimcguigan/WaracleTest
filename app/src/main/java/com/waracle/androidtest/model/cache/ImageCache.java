package com.waracle.androidtest.model.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Image Lru cache
 */

public class ImageCache {
    private static ImageCache instance;
    private LruCache<String, Bitmap> imageCache;

    private ImageCache(){
        imageCache = new LruCache<>(getCacheMemory());
    }

    public static ImageCache getInstance(){
        if (instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    public LruCache<String, Bitmap> getCache(){
        return  imageCache;
    }

    private int getCacheMemory() {
       final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        return maxMemory / 8;
    }
}
