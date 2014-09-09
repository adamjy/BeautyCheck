package com.adam.douban.girls.engine;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;
/**
 * Bitmap cache implements least recently used algorithm.
 * @description
 * @author AdamJY
 * 2014下午3:42:20
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements
		ImageCache {

	private static final String TAG = "BitmapLruCache";  
	  
    private BitmapSoftRefCache softRefCache;  
  
    public BitmapLruCache(int maxSize) {  
        super(maxSize);  
        softRefCache = new BitmapSoftRefCache();  
    }  
  
    @Override  
    protected int sizeOf(String key, Bitmap value) {  
        return value.getRowBytes() * value.getHeight();  
    }  
  
    @Override  
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {  
        if (evicted) {  
            Log.i(TAG, "Lru cache map is fulled, remove:" + key);  
            // Add removed bitmp to soft map cache
            softRefCache.putBitmap(key, oldValue);  
        }  
    }  
  
    /** 
     * Get bitmap from cache
     */  
    @Override  
    public Bitmap getBitmap(String url) {  
        Bitmap bitmap = get(url);  
        // Get bitmap from soft reference map
        if (bitmap == null) {  
            bitmap = softRefCache.getBitmap(url);  
        } else {  
            Log.i(TAG, "Get bitmap from lruache：" + url);  
        }  
        return bitmap;  
    }  
  
    /** 
     * Add cache 
     */  
    @Override  
    public void putBitmap(String url, Bitmap bitmap) {  
        put(url, bitmap);  
    }

}
