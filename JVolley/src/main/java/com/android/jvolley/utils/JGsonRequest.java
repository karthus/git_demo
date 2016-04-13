package com.android.jvolley.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

/**
 * @author shihuajian
 * @since 16-4-12 下午3:33
 * @depict Gson请求类
 */
public class JGsonRequest<T> extends BaseRequest<T> {

    protected JGsonRequest(Context context) {
        super(context);
    }

    /**
     * 默认请求
     *
     * @param method 请求类型, GET还是POST
     * @param url 链接
     * @param params 请求参数
     * @param bodyData  body数据
     * @param cookies   请求时需要的Cookies, 这个Cookies是会默认put到header请求的
     * @param isCallbackCookies 请求结果是否返回Cookies
     * @param clazz 数据的实体类
     * @param callBackListener 请求结果反馈的监听方法
     */
    private void defaultRequest(int method, String url, Map<String, String> params, byte[] bodyData, Map<String, String> header,
                     String cookies, boolean isCallbackCookies, Class<T> clazz,
                     final CallBackListener<T> callBackListener) {
        GsonRequest<T> request = new GsonRequest<>(method, url, clazz, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBackListener.onErrorResponse(error);
            }
        });
        request.setResultListener(new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                // 默认或者isCallbackCookie设置false，则回调这个方法
                callBackListener.onSuccessResponse(response);
            }

            @Override
            public void onResponse(T response, Map<String, String> setCookies, String cookies) {
                // 如果isCallbackCookie设置true，则回调这个方法
                callBackListener.onSuccessResponse(response, setCookies, cookies);
            }
        });
        request.setHeader(header);
        request.setCallbackCookie(isCallbackCookies);
        request.setRequestParams(params);
        request.setBodyData(bodyData);
        request.setCookies(cookies);
        addRequest(request);
    }

    /**
     * GET 请求, Body为null, header为null, callbackCookie为true
     *
     * @param url 链接
     * @param params 请求参数
     * @param cookies   请求时需要的Cookies, 这个Cookies是会默认put到header请求的
     * @param clazz 数据的实体类
     * @param callBackListener 请求结果反馈的监听方法
     */
    public void get(String url, Map<String, String> params, String cookies, Class<T> clazz, final CallBackListener<T> callBackListener) {
        this.defaultRequest(Request.Method.GET, url, params, null, null, cookies, true, clazz, callBackListener);
    }

    /**
     * POST 请求, Body为null, header为null, callbackCookie为true
     *
     * @param url 链接
     * @param params 请求参数
     * @param cookies   请求时需要的Cookies, 这个Cookies是会默认put到header请求的
     * @param clazz 数据的实体类
     * @param callBackListener 请求结果反馈的监听方法
     */
    public void post(String url, Map<String, String> params, String cookies, Class<T> clazz, final CallBackListener<T> callBackListener) {
        this.defaultRequest(Request.Method.POST, url, params, null, null, cookies, true, clazz, callBackListener);
    }

    /**
     * POST 请求, Body为null, callbackCookie为true
     *
     * @param url 链接
     * @param params 请求参数
     * @param header header参数
     * @param cookies   请求时需要的Cookies, 这个Cookies是会默认put到header请求的
     * @param clazz 数据的实体类
     * @param callBackListener 请求结果反馈的监听方法
     */
    public void post(String url, Map<String, String> params, Map<String, String> header, String cookies, Class<T> clazz,
                    final CallBackListener<T> callBackListener) {
        this.defaultRequest(Request.Method.POST, url, params, null, header, cookies, true, clazz, callBackListener);
    }

    /**
     * POST 请求登录, Body为null, params为null, callbackCookie为true
     *
     * @param url 链接
     * @param cookies   请求时需要的Cookies, 这个Cookies是会默认put到header请求的
     * @param clazz 数据的实体类
     * @param callBackListener 请求结果反馈的监听方法
     */
    public void postLogin(String url, byte[] body, String cookies, Class<T> clazz, final CallBackListener<T> callBackListener) {
        this.defaultRequest(Request.Method.POST, url, null, body, null, cookies, true, clazz, callBackListener);
    }
}
