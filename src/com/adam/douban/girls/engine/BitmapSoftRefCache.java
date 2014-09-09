package com.adam.douban.girls.engine;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;
/**
 * Bitmap cache used soft reference
 * @description
 * @author AdamJY
 * 2014下午3:43:33
 */
public class BitmapSoftRefCache implements ImageCache {

	private static final String TAG = "BitmapSoftRefCache";  
    
    private LinkedHashMap<String, SoftReference<Bitmap>> map;  
    public BitmapSoftRefCache() {  
        map = new LinkedHashMap<String, SoftReference<Bitmap>>();  
    }  
  
    @Override  
    public Bitmap getBitmap(String url) {  
        Bitmap bitmap = null;  
        SoftReference<Bitmap> softRef = map.get(url);  
        if(softRef != null){  
            bitmap = softRef.get();  
            if(bitmap == null){  
                map.remove(url); //从map中移除  
                Log.w(TAG, url+"对象已经被GC回收");  
            }else{  
                Log.i(TAG, "命中"+url);  
            }  
        }  
        return bitmap;  
    }  
  
    @Override  
    public void putBitmap(String url, Bitmap bitmap) {  
        SoftReference<Bitmap> softRef = new SoftReference<Bitmap>(bitmap);  
        map.put(url, softRef);  
    } 

}
