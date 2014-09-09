package com.adam.douban.girls.engine;

import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
/**
 * Image listener factory for ImageLoader in volley.
 * @description
 * @author AdamJY
 * 2014下午3:45:25
 */
public class ImageListenerFactory {
	protected static final String TAG = "ImageListenerFactory";

	public static ImageListener getImageListener(final ImageView view,  
            final int defaultImageResId, final int errorImageResId) {  
        return new ImageListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
                if (errorImageResId != 0) {  
                    view.setImageResource(errorImageResId);  
                }  
            }  
  
            @Override  
            public void onResponse(ImageContainer response, boolean isImmediate) {  
                if (response.getBitmap() != null) {  
                      
                    if(view.getTag().toString() == response.getRequestUrl()){  
                        view.setImageBitmap(response.getBitmap());  
                    }else{  
                        Log.i(TAG, "ImageView get wrong bitmap");  
                    }  
                } else if (defaultImageResId != 0) {  
                    view.setImageResource(defaultImageResId);  
                }  
            }  
        };  
    } 
}
