package com.adam.douban.girls.engine;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
/**
 * Capsulate volley
 * @description
 * @author AdamJY
 * 2014下午3:46:07
 */
public class MyVolley {

	private static MyVolley instance;
	
	public static MyVolley getInstance(Context context) {
		return instance;
	}
	
	private static final String TAG = "MyVolley";  
	  
    private static RequestQueue mRequestQueue;  
    private static ImageLoader mImageLoader;  
    private final static int RATE = 8; // Default rate of cache over total cache of app
    
    private MyVolley(Context context) {  
        mRequestQueue = Volley.newRequestQueue(context);  
  
        ActivityManager manager = (ActivityManager) context  
                .getSystemService(Context.ACTIVITY_SERVICE);  
        int maxSize = manager.getMemoryClass() / RATE;
        System.out.println("maxSIze=" + maxSize + " MB");
        // Create image loader, and delivery lru hashmap to it.
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(  
                1024 * 1024 * maxSize));  
  
        Log.i(TAG, "MyVolley is instantiated successfully!");  
    }
    
    public static void init(Context context) {  
        if (instance == null) {  
            instance = new MyVolley(context);  
        }  
    }  
  
    /** 
     * Get request queue for net request
     *  
     * @return 
     */  
    private static RequestQueue getRequestQueue() {  
        throwIfNotInit();  
        return mRequestQueue;  
    }  
  
    public static ImageLoader getImageLoader() {  
        throwIfNotInit();  
        return mImageLoader;  
    }  
  
    public static void addRequest(Request<?> request) {  
        getRequestQueue().add(request);  
    }  
  
    public static void getImage(String requestUrl, ImageView imageView) {  
        getImage(requestUrl, imageView, 0, 0);  
    }  
  
    public static void getImage(String requestUrl, ImageView imageView,  
            int defaultImageResId, int errorImageResId) {  
        getImage(requestUrl, imageView, defaultImageResId, errorImageResId, 0,  
                0);  
    }  
  
    public static void getImage(String requestUrl, ImageView imageView,  
            int defaultImageResId, int errorImageResId, int maxWidth,  
            int maxHeight) {  
        imageView.setTag(requestUrl);  
        getImageLoader().get(requestUrl, ImageListenerFactory.getImageListener(  
                imageView, defaultImageResId, errorImageResId), maxWidth,  
                maxHeight);  
    }  
  
    /** 
     * Check whether initialization is finished
     */  
    private static void throwIfNotInit() {  
        if (instance == null) {
            throw new IllegalStateException("MyVolley has not been instantiated");  
        }  
    }
}
