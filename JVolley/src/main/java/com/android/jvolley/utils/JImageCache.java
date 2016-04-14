package com.android.jvolley.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * @author shihuajian
 * @depict 图片缓存器，实现ImageLoder.ImageCache实现其中的方法，具体图片怎么样缓存让我们自己来实现，这样可以考虑到将来的扩展性
 * @since 4/14/16 9:47 AM
 */
public class JImageCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache = null;

    private static final int CACHE_MAX_SIZE = 8 * 1024 * 1024;  //默认缓存大小为8M

    public JImageCache() {
        if (mCache == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                mCache = new LruCache<String, Bitmap>(CACHE_MAX_SIZE) {
                    @Override
                    protected int sizeOf(String key, Bitmap value) {
                        return value.getRowBytes() * value.getHeight();
                    }
                };
            }
        }
    }

    @Override
    public Bitmap getBitmap(String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return mCache.get(url);
        }
        return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            mCache.put(url, bitmap);
        }
    }
}
