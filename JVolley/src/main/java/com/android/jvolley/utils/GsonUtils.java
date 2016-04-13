package com.android.jvolley.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

/**
 * Gson工具类
 *
 * @author shihuajian
 * @since 2016/2/26
 */
public class GsonUtils {

    /**
     * json转实体类
     *
     * @param clazz
     * @param jsonStr
     * @return
     * @throws JSONException
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(Class<T> clazz, String jsonStr) throws JSONException, JsonSyntaxException {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        Gson gson = new Gson();
        T t = gson.fromJson(jsonStr, clazz);
        return t;
    }

    /**
     * 实体类转Json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(obj);
        return jsonStr;
    }
}
