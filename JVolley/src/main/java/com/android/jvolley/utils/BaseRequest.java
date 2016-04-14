package com.android.jvolley.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author shihuajian
 * @since 16-4-12 下午3:22
 * @depict 请求基类
 */
public class BaseRequest<T> {

    private static final String TAG = BaseRequest.class.getSimpleName();

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    protected static RequestQueue mRequestQueue;

    private Context mContext;

    protected BaseRequest(Context context) {
        this.mContext = context;
        mRequestQueue = RequestQueueSington.getInstance(mContext);
    }

    /**
     * 请求加入到Volley Request请求队列中
     *
     * @param request
     */
    protected void addRequest(Request request) {
        mRequestQueue.add(request);
    }

    /**
     * 根据请求地址和请求参数进行拼接成新的请求地址
     *
     * @param url    请求服务器地址
     * @param params 请求参数
     * @return 拼接过后的地址
     */
    protected String createGetUrlWithParams(String url, Map<String, String> params) {
        if (params != null) {
            StringBuffer stringBuffer = new StringBuffer(url);
            if (!url.contains("?")) {
                stringBuffer.append('?');
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey().toString();
                String value = null;
                if (entry.getValue() == null) {
                    value = "";
                } else {
                    value = entry.getValue().toString();
                }
                stringBuffer.append(key);
                stringBuffer.append("=");
                try {
                    LogUtils.d(TAG, "get获取数据的key:" + key);
                    LogUtils.d(TAG, "get获取数据的value:" + value);
                    value = URLEncoder.encode(value, DEFAULT_PARAMS_ENCODING);
                    LogUtils.d(TAG, "get编码后value:" + value);
                    stringBuffer.append(value);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                stringBuffer.append('&');
            }
            //删除最后一个'&'
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            url = stringBuffer.toString();
        }
        LogUtils.d(TAG, "get请求地址url:" + url);
        return url;
    }
}
