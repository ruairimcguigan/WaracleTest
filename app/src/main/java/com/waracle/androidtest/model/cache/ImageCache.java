package com.waracle.androidtest.model.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.waracle.androidtest.model.Cake;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Image Lru cache
 */

public class ImageCache {
    private static ImageCache instance;
    private LruCache<String, Bitmap> imageCache;
    private static final String TAG = ImageCache.class.getSimpleName();

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

    public void cacheImages(List<Cake> cakes) throws IOException {
        if (cakes != null) {
            for (Cake cake : cakes) {
                String imageUrl = cake.getImage();
                InputStream inputStream = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                getInstance().getCache().put(cake.getTitle(), bitmap);
                cake.setBitmap(getInstance().getCache().get(cake.getTitle()));
                inputStream.close();
            }
            Log.i(TAG, "doInBackground: Contents of cache: " + getInstance().getCache().size());
        }
    }
}
