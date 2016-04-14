package com.android.jvolley.utils;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shihuajian
 * @since 16-4-11 下午4:54
 * @depict Gson请求类
 */
public class GsonRequest<T> extends Request<T> {

    public static final String KEY_SESSION_ID = "session_id";

    public static final String KEY_VERIFICATION = "verification";

    public static final String KEY_COOKIE = "Cookie";

    /** 请求返回的结果监听器 */
    private Response.Listener<T> resultListener = null;

    /** 数据的实体类 */
    private Class<T> mClass;

    /** 头部数据 */
    private static Map<String, String> mHeader = new HashMap<String, String>();

    /** Post的参数 */
    private Map<String, String> requestParams = new HashMap<String, String>();

    /** body数据 */
    private byte[] bodyData;

    /** 是否返回Cookie */
    private boolean hasCookie = false;

    /** 使用该正则表达式从reponse的头中提取cookie内容的子串 */
    private final Pattern mPattern = Pattern.compile("Set-Cookie\\d*");

    /** 获取到的所有Set-Cookie */
    private Map<String, String> mSetCookies = new HashMap<String, String>();

    /** Set-Cookie拼成的Cookie */
    private static String mCookies = "";

    /** 是否返回Cookie */
    private boolean isCallbackCookie = false;


    static {
        // 设置访问服务器时必须传递的参数，密钥等
        // 例如：
        // mHeader.put("APP-Key", "LBS-AAA");
        // mHeader.put("APP-Secret", "ad12msa234das232in");
    }

    /**
     * GsonRequest 构造函数，需要指定请求方式
     *
     * @param method 请求方式 POST or GET
     * @param url 链接
     * @param clazz 实体类
     * @param errorListener 错误反馈监听
     */
    public GsonRequest(int method, String url, Class<T> clazz, Response.ErrorListener errorListener) {
        super(method, url , errorListener);
        this.mClass = clazz;
        this.isCallbackCookie = false;
        this.bodyData = null;
    }

    /**
     * GsonRequest 构造函数，默认使用GET请求方式
     *
     * @param url 链接
     * @param clazz 实体类
     * @param errorListener 错误反馈监听
     */
    public GsonRequest(String url, Class<T> clazz, Response.ErrorListener errorListener) {
        this(Method.GET, url , clazz, errorListener);
    }

    /**
     * 数据解析
     * @param response Response from the network
     * @return 网络请求返回的数据
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            // 使用正则表达式获取Cookies
            StringBuffer cookie = new StringBuffer();
            for (String key : response.headers.keySet()) {
                Matcher matcher = mPattern.matcher(key);
                if (matcher.find()) {
                    String value = response.headers.get(key);
//                    // 过滤掉后面的时间
                    value = value.substring(0, value.indexOf(";"));
                    // 储存verification和session_id
                    if (value.contains(KEY_SESSION_ID)) {
                        mSetCookies.put(KEY_SESSION_ID, value);
                    } else if (value.contains(KEY_VERIFICATION)) {
                        mSetCookies.put(KEY_VERIFICATION, value);
                    }
                    // 拼成一条完整的Cookie
                    if (cookie.length() > 0) {
                        // 后面添加的value需要使用";"分隔
                        cookie.append(String.valueOf(";" + value));
                    } else {
                        // 第一条直接append
                        cookie.append(String.valueOf(value));
                    }
                }
            }
            mCookies = cookie.toString();

            String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T data = GsonUtils.fromJson(mClass, jsonStr);
            return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 数据分发
     *
     * @param response The parsed response returned by
     */
    @Override
    protected void deliverResponse(T response) {
        if (resultListener == null) {
            return;
        }

        // 判断是否需要返回Cookie
        if (isCallbackCookie) {
            resultListener.onResponse(response, getSetCookies(), getCookies());
        } else {
            resultListener.onResponse(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mHeader != null) {
            mHeader.put(KEY_COOKIE, mCookies);
            return mHeader;
        } else {
            return super.getHeaders();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        String data = String.valueOf(bodyData);
        if (TextUtils.isEmpty(data)) {
            return super.getBody();
        } else {
            return bodyData;
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (requestParams != null && requestParams.size() > 0) {
            return requestParams;
        } else {
            return super.getParams();
        }
    }

    public static void setHeader(Map<String, String> mHeader) {
        if (mHeader != null) {
            GsonRequest.mHeader.putAll(mHeader);
        }
    }

    public String getCookies() {
        return mCookies;
    }

    public void setCookies(String mCookies) {
        this.mCookies = mCookies;
    }

    public Map<String, String> getSetCookies() {
        return mSetCookies;
    }

    public void setResultListener(Response.Listener<T> listener) {
        this.resultListener = listener;
    }

    public byte[] getBodyData() {
        return bodyData;
    }

    public void setBodyData(byte[] bodyData) {
        this.bodyData = bodyData;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public boolean isCallbackCookie() {
        return isCallbackCookie;
    }

    public void setCallbackCookie(boolean callbackCookie) {
        isCallbackCookie = callbackCookie;
    }
}
