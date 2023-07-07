package com.zy.common.utils;

import android.text.TextUtils;
import android.util.ArrayMap;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zy.common.constants.AppConstant;

import org.json.JSONException;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gson转换工具类
 * <p>
 * Author:zhy
 * Date:2022/3/8
 */
public final class JsonUtils {

    private static final Gson GSON = new Gson();

    private JsonUtils() {
        throw new UnsupportedOperationException("Utils CANNOT be instantiated!");
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return GSON.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        try {
            return GSON.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(Reader json, Class<T> classOfT) {
        try {
            return GSON.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(Reader json, Type typeOfT) {
        try {
            return GSON.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJson(Object src) {
        return GSON.toJson(src);
    }

    public static String safe2Json(Object src) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return GSON.toJson(src, typeOfSrc);
    }

    public static void toJson(Object src, Appendable writer) {
        GSON.toJson(src, writer);
    }

    public static void toJson(Object src, Type typeOfSrc, Appendable writer) {
        GSON.toJson(src, typeOfSrc, writer);
    }

    /**
     * @param jsonObject json 对象
     * @param key        键
     * @return 返回结果可能为空对象, 注意空指针判断
     */
    public static String parseJsonValue(JSONObject jsonObject, String key) {
        String value = "";
        if (null == jsonObject || TextUtils.isEmpty(key)) {
            return value;
        }
        try {
            value = jsonObject.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * map 2 json
     *
     * @return
     */
    public static String parseMap2JSON(ArrayMap<String, Object> params) {
        try {
            if (null != params && params.size() > 0) {
                org.json.JSONObject jsonObject = new org.json.JSONObject();
                for (int i = 0; i < params.size(); i++) {
                    jsonObject.put(params.keyAt(i), params.valueAt(i));
                }
                return String.valueOf(jsonObject);
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }


    /**
     * 获取json解析code
     *
     * @param response
     * @return
     */
    public static int parseResultCode(String response) {
        try {
            return new org.json.JSONObject(response).getInt(AppConstant.CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String parseResultString(String response, String key) {
        try {
            return parseJsonString(new org.json.JSONObject(response), key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseJsonString(org.json.JSONObject jsonObject, String key) {
        return parseJsonString(jsonObject, key, "");
    }

    private static String parseJsonString(org.json.JSONObject jsonObject, String key, String defaultValue) {
        try {
            return jsonObject.has(key) && !TextUtils.equals("null", jsonObject.getString(key)) ? jsonObject.getString(key) : defaultValue;
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public static boolean parseJsonBoolean(String response, String key) {
        try {
            return parseJsonBoolean(new org.json.JSONObject(response), key);
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean parseJsonBoolean(org.json.JSONObject jsonObject, String key) {
        try {
            return jsonObject.has(key) && jsonObject.getBoolean(key);
        } catch (JSONException e) {
            return false;
        }
    }


    public static int parseJsonInt(String response, String key) {
        try {
            return parseJsonInt(new org.json.JSONObject(response), key);
        } catch (JSONException e) {
            return -1;
        }
    }

    public static int parseJsonInt(org.json.JSONObject jsonObject, String key) {
        return parseJsonInt(jsonObject, key, -1);
    }

    private static int parseJsonInt(org.json.JSONObject jsonObject, String key, int defaultValue) {
        try {
            return jsonObject.has(key) ? jsonObject.getInt(key) : defaultValue;
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list;
        list = GSON.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(GSON.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list;
        list = GSON.fromJson(gsonString,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map;
        map = GSON.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

    // -------

    /**
     * 按章节点得到相应的内容
     *
     * @param jsonString json字符串
     * @param note       节点
     * @return 节点对应的内容
     */
    public static String getNoteJsonString(String jsonString, String note) {
        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("json字符串");
        }
        if (TextUtils.isEmpty(note)) {
            throw new RuntimeException("note标签不能为空");
        }
        JsonElement element = new JsonParser().parse(jsonString);
        if (element.isJsonNull()) {
            throw new RuntimeException("得到的jsonElement对象为空");
        }
        return element.getAsJsonObject().get(note).toString();
    }

    /**
     * 按照节点得到节点内容，然后传化为相对应的bean数组
     *
     * @param jsonString 原json字符串
     * @param note       节点标签
     * @param beanClazz  要转化成的bean class
     * @return 返回bean的数组
     */
    public static <T> List<T> parserJsonToArrayBeans(String jsonString, String note, Class<T> beanClazz) {
        String noteJsonString = getNoteJsonString(jsonString, note);
        return parserJsonToArrayBeans(noteJsonString, beanClazz);
    }

    /**
     * 按照节点得到节点内容，转化为一个数组
     *
     * @param jsonString json字符串
     * @param beanClazz  集合里存入的数据对象
     * @return 含有目标对象的集合
     */
    public static <T> List<T> parserJsonToArrayBeans(String jsonString, Class<T> beanClazz) {
        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("json字符串为空");
        }
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        if (jsonElement.isJsonNull()) {
            throw new RuntimeException("得到的jsonElement对象为空");
        }
        if (!jsonElement.isJsonArray()) {
            throw new RuntimeException("json字符不是一个数组对象集合");
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<T> beans = new ArrayList<T>();
        for (JsonElement jsonElement2 : jsonArray) {
            T bean = new Gson().fromJson(jsonElement2, beanClazz);
            beans.add(bean);
        }
        return beans;
    }

    /**
     * 把相对应节点的内容封装为对象
     *
     * @param jsonString json字符串
     * @param clazzBean  要封装成的目标对象
     * @return 目标对象
     */
    public static <T> T parserJsonToArrayBean(String jsonString, Class<T> clazzBean) {
        if (TextUtils.isEmpty(jsonString)) {
            throw new RuntimeException("json字符串为空");
        }
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        if (jsonElement.isJsonNull()) {
            throw new RuntimeException("json字符串为空");
        }
        if (!jsonElement.isJsonObject()) {
            throw new RuntimeException("json不是一个对象");
        }
        return new Gson().fromJson(jsonElement, clazzBean);
    }

    /**
     * 按照节点得到节点内容，转化为一个数组
     *
     * @param jsonString json字符串
     * @param note       json标签
     * @param clazzBean  集合里存入的数据对象
     * @return 含有目标对象的集合
     */
    public static <T> T parserJsonToArrayBean(String jsonString, String note, Class<T> clazzBean) {
        String noteJsonString = getNoteJsonString(jsonString, note);
        return parserJsonToArrayBean(noteJsonString, clazzBean);
    }

    /**
     * 把bean对象转化为json字符串
     *
     * @param obj bean对象
     * @return 返回的是json字符串
     */
    public static String toJsonString(Object obj) {
        if (obj != null) {
            return new Gson().toJson(obj);
        } else {
            throw new RuntimeException("对象不能为空");
        }
    }

}
