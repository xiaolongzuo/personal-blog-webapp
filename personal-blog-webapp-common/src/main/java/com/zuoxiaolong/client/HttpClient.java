package com.zuoxiaolong.client;

import com.zuoxiaolong.util.HttpUtil;
import com.zuoxiaolong.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

    private HttpClient() {}

    private static final String URL = "http://127.0.0.1:8888";

    private static Map<String, Object> map(String[] keys, Object... values) {
        if (keys == null || values == null) {
            return null;
        }
        if (keys.length != values.length) {
            throw new IllegalArgumentException();
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    public static void get(String uri, Object param) {
        HttpUtil.sendHttpJsonRequest(URL + uri, "GET", param);
    }

    public static void post(String uri, Object param) {
        HttpUtil.sendHttpJsonRequest(URL + uri, "POST", param);
    }

    public static void get(String uri, Map<String, Object> param) {
        HttpUtil.sendHttpRequest(URL + uri, "GET", param);
    }

    public static void post(String uri, Map<String, Object> param) {
        HttpUtil.sendHttpRequest(URL + uri, "POST", param);
    }

    public static void get(String uri, String[] keys, Object... values) {
        HttpUtil.sendHttpRequest(URL + uri, "GET", map(keys, values));
    }

    public static void post(String uri, String[] keys, Object... values) {
        HttpUtil.sendHttpRequest(URL + uri, "POST", map(keys, values));
    }

    public static <T> T get(Class<T> clazz, String uri, Object param) {
        String response = HttpUtil.sendHttpJsonRequest(URL + uri, "GET", param);
        try {
            return JsonUtils.fromJson(response, clazz);
        } catch (Throwable e) {
            log.warn("json parse error, {}, {}, {}", uri, param, response);
            return null;
        }
    }

    public static <T> T post(Class<T> clazz, String uri, Object param) {
        String response = HttpUtil.sendHttpJsonRequest(URL + uri, "POST", param);
        try {
            return JsonUtils.fromJson(response, clazz);
        } catch (Throwable e) {
            log.warn("json parse error, {}, {}, {}", uri, param, response);
            return null;
        }
    }

    public static <T> T get(Class<T> clazz, String uri, Map<String, Object> param) {
        String response = HttpUtil.sendHttpRequest(URL + uri, "GET", param);
        try {
            return JsonUtils.fromJson(response, clazz);
        } catch (Throwable e) {
            log.warn("json parse error, {}, {}, {}", uri, param, response);
            return null;
        }
    }

    public static <T> T post(Class<T> clazz, String uri, Map<String, Object> param) {
        String response = HttpUtil.sendHttpRequest(URL + uri, "POST", param);
        try {
            return JsonUtils.fromJson(response, clazz);
        } catch (Throwable e) {
            log.warn("json parse error, {}, {}, {}", uri, param, response);
            return null;
        }
    }

    public static <T> T get(Class<T> clazz, String uri, String[] keys, Object... values) {
        Map<String, Object> param = map(keys, values);
        String response = HttpUtil.sendHttpRequest(URL + uri, "GET", param);
        try {
            return JsonUtils.fromJson(response, clazz);
        } catch (Throwable e) {
            log.warn("json parse error, {}, {}, {}", uri, param, response);
            return null;
        }
    }

    public static <T> T post(Class<T> clazz, String uri, String[] keys, Object... values) {
        Map<String, Object> param = map(keys, values);
        String response = HttpUtil.sendHttpRequest(URL + uri, "POST", param);
        try {
            return JsonUtils.fromJson(response, clazz);
        } catch (Throwable e) {
            log.warn("json parse error, {}, {}, {}", uri, param, response);
            return null;
        }
    }

}
