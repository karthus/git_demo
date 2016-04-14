package com.android.jvolley.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author shihuajian
 * @since 16-4-11 下午4:50
 * @depict RequestQueue 单例模式
 */
public class RequestQueueSington {

    private static RequestQueue mInstance;

    public static RequestQueue getInstance(Context context) {
        synchronized (RequestQueueSington.class) {
            if (mInstance == null) {
                mInstance = Volley.newRequestQueue(context);
            }
        }

        return mInstance;
    }
}
