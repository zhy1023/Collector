package com.zy.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @Author Liudeli
 * @Describe：SharedPreferences工具类，包含常用的数值获取和存储
 */
public class PreferencesUtil {

    private PreferencesUtil(){}

    /**
     * 默认的SharePreference名称
     */
    private static final String SHARED_NAME = "SharedPreferences";

    /**
     * 查询某个key是否已经存在
     * @param context	应用程序上下文
     * @param key	key关键字
     * @return	包含返回true；反之返回false
     */
    public static boolean containsKey(Context context, String key){
        SharedPreferences sp = getSharedPreferences(context);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     * @param context 应用程序上下文
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getAll();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context	应用程序上下文
     * @param key		key关键字
     * @param defaultObject	默认值
     * @return	返回获取的String值
     */
    public static Object get(Context context, String key, Object defaultObject){
        SharedPreferences sp = getSharedPreferences(context);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 获取Set<String> 集合
     * @param context	应用程序上下文
     * @param key		key关键字
     * @param defValues	默认值
     * @return	返回Set<String>值
     */
    public static Set<String> getStringSet(Context context, String key,  Set<String> defValues){
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getStringSet(key, defValues);
    }

    /**
     * 保存Set<String>集合的值
     * @param context	应用程序上下文
     * @param key 		key关键字
     * @param value		对应值
     * @return 成功返回true，失败返回false
     */
    public static boolean putStringSet(Context context, String key, Set<String> value){
        return getEditor(context).putStringSet(key, value).commit();
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context	应用程序上下文
     * @param key 		key关键字
     * @param object	对应值
     * @return 成功返回true，失败返回false
     */
    public static void put(Context context, String key, Object object){
        if (object instanceof String) {
            getEditor(context).putString(key, (String) object);
        } else if (object instanceof Integer) {
            getEditor(context).putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            getEditor(context).putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            getEditor(context).putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            getEditor(context).putLong(key, (Long) object);
        } else {
            getEditor(context).putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(getEditor(context));
    }



    /**
     * 删除关键字key对应的值
     * @param context	应用程序上下文
     * @param key		关键字key
     */
    public static void removeKey(Context context, String key){
        getEditor(context).remove(key);
        SharedPreferencesCompat.apply(getEditor(context));
    }

    /**
     * 清除所有的关键字对应的值
     * @param context	应用程序上下文
     */
    public static void clearValues(Context context){
        getEditor(context).clear();
        SharedPreferencesCompat.apply(getEditor(context));
    }

    /**
     * 获取SharedPreferences对象
     * @param context	应用程序上下文
     * @return	返回SharedPreferences对象
     */
    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取Editor对象
     * @param context	应用程序上下文
     * @return	返回Editor对象
     */
    private static Editor getEditor(Context context){
        return getSharedPreferences(context).edit();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         * @param editor
         */
        public static void apply(Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }
}

