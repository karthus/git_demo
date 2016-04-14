package com.android.jvolley.utils;

import com.android.volley.VolleyError;

import java.util.Map;

/**
 * @author shihuajian
 * @since 16-4-12 下午3:43
 * @depict 请求接口回调监听
 */
public interface CallBackListener<T> {

    void onSuccessResponse(T response);

    void onSuccessResponse(T response, Map<String, String> setCookies, String cookies);

    void onErrorResponse(VolleyError error);
}
