package com.android.jvolley.utils;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

/**
 * @author shihuajian
 * @depict 图片加载类
 * @since 4/14/16 9:40 AM
 */
public class JImageLoader {

    private Context context;

    private RequestQueue requestQueue;

    public JImageLoader(Context context) {
        requestQueue = RequestQueueSington.getInstance(context);
    }

    public void get(ImageView view, String url, int defaultImageResId, int errorImageResId) {
        ImageLoader imageLoader = new ImageLoader(requestQueue, new JImageCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(view, defaultImageResId, errorImageResId);
        imageLoader.get(url, listener);
    }
}
