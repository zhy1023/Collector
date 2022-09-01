package data.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;
import com.zy.common.application.BaseApp;

import java.util.Collections;
import java.util.Set;

/**
 * Author:zhy
 * Date:2022/3/8
 * Description:BaseMMKV
 */
public abstract class BaseMMKV {
    private MMKV mmkv;

    private MMKV multiProcessMMKV;

    //mmapID
    private String mmkvId;

    //多进程mmapID
    private String multiProcessId;

    /**
     * 单进程
     *
     * @param mmkvId
     */
    public BaseMMKV(String mmkvId) {
        this.mmkvId = mmkvId;
        mmkv = MMKV.mmkvWithID(mmkvId);
        //TODO SharePrefences迁移到MMKV
        Context context = BaseApp.context;
        SharedPreferences sp = context.getSharedPreferences(mmkvId, Context.MODE_PRIVATE);
        if (sp == null || sp.getAll().isEmpty()) {
            return;
        }
        mmkv.importFromSharedPreferences(sp);
        sp.edit().clear().apply();
    }

    /**
     * 多进程访问
     * SINGLE_PROCESS_MODE = 1;
     * MULTI_PROCESS_MODE = 2;
     * CONTEXT_MODE_MULTI_PROCESS = 4;
     *
     * @param multiProcessId
     */
    public BaseMMKV(String multiProcessId, int type) {
        this.multiProcessId = multiProcessId;
        multiProcessMMKV = MMKV.mmkvWithID(mmkvId, type);
    }


    /**
     * 单进程访问
     *
     * @return
     */
    public MMKV getMMKV() {
        if (mmkv == null) {
            mmkv = MMKV.mmkvWithID(mmkvId);
        }
        return mmkv;
    }

    /**
     * 多进程访问
     *
     * @return
     */
    public MMKV getMultiProcessMMKV() {
        if (multiProcessMMKV == null) {
            multiProcessMMKV = MMKV.mmkvWithID(mmkvId, MMKV.MULTI_PROCESS_MODE);
        }
        return multiProcessMMKV;
    }


    /**
     * 保存数据 根据拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        put(key, object, getMMKV());
    }

    public void put(String key, Object object, MMKV mmkv) {
        if (object instanceof String) {
            mmkv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mmkv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mmkv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mmkv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mmkv.encode(key, (Long) object);
        } else if (object instanceof Double) {
            mmkv.encode(key, (Double) object);
        } else if (object instanceof byte[]) {
            mmkv.encode(key, (byte[]) object);
        } else {
            mmkv.encode(key, object.toString());
        }
    }

    public void putSet(String key, Set<String> sets) {
        putSet(key, sets, getMMKV());
    }

    public void putSet(String key, Set<String> sets, MMKV mmkv) {
        mmkv.encode(key, sets);
    }

    /**
     * 存储Obj
     *
     * @param key
     * @param obj
     */
    public void putParcelable(String key, Parcelable obj) {
        getMMKV().encode(key, obj);
    }

    public void putParcelable(String key, Parcelable obj, MMKV mmkv) {
        mmkv.encode(key, obj);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public Integer getInt(String key) {
        return getInt(key, getMMKV());
    }

    public Integer getInt(String key, Integer defaultValue) {
        return getInt(key, defaultValue, getMMKV());
    }

    public Double getDouble(String key) {
        return getDouble(key, getMMKV());
    }

    public Long getLong(String key) {
        return getLong(key, getMMKV());
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, getMMKV());
    }

    public Float getFloat(String key) {
        return getFloat(key, getMMKV());
    }

    public byte[] getBytes(String key) {
        return getBytes(key, getMMKV());
    }

    public String getString(String key) {
        return getString(key, getMMKV());
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, getMMKV());
    }

    public Parcelable getParcelable(String key) {
        return getParcelable(key, getMMKV());
    }

    public Integer getInt(String key, MMKV mmkv) {
        return getInt(key, -1, mmkv);
    }

    public Double getDouble(String key, MMKV mmkv) {
        return getDouble(key, -1.0d, mmkv);
    }

    public Long getLong(String key, MMKV mmkv) {
        return getLong(key, -1L, mmkv);
    }

    public Boolean getBoolean(String key, MMKV mmkv) {
        return getBoolean(key, false, mmkv);
    }

    public Float getFloat(String key, MMKV mmkv) {
        return getFloat(key, -1.0f, mmkv);
    }

    public byte[] getBytes(String key, MMKV mmkv) {
        return getBytes(key, null, mmkv);
    }

    public String getString(String key, MMKV mmkv) {
        return getString(key, null, mmkv);
    }

    public Set<String> getStringSet(String key, MMKV mmkv) {
        return getStringSet(key, Collections.emptySet(), mmkv);
    }

    public Parcelable getParcelable(String key, MMKV mmkv) {
        return getParcelable(key, null, mmkv);
    }

    public Integer getInt(String key, int defaultValue) {
        return getInt(key, defaultValue, getMMKV());
    }

    public Integer getInt(String key, int defaultValue, MMKV mmkv) {
        return mmkv.decodeInt(key, defaultValue);
    }

    public Double getDouble(String key, Double defaultValue) {
        return getDouble(key, defaultValue, getMMKV());
    }

    public Double getDouble(String key, Double defaultValue, MMKV mmkv) {
        return mmkv.decodeDouble(key, defaultValue);
    }

    public Long getLong(String key, Long defaultValue) {
        return getLong(key, defaultValue, getMMKV());
    }

    public Long getLong(String key, Long defaultValue, MMKV mmkv) {
        return mmkv.decodeLong(key, defaultValue);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(key, defaultValue, getMMKV());
    }

    public Boolean getBoolean(String key, boolean defaultValue, MMKV mmkv) {
        return mmkv.decodeBool(key, defaultValue);
    }

    public Float getFloat(String key, Float defaultValue) {
        return getFloat(key, defaultValue, getMMKV());
    }

    public Float getFloat(String key, Float defaultValue, MMKV mmkv) {
        return mmkv.decodeFloat(key, defaultValue);
    }

    public byte[] getBytes(String key, byte[] defaultValue) {
        return getBytes(key, defaultValue, getMMKV());
    }

    public byte[] getBytes(String key, byte[] defaultValue, MMKV mmkv) {
        return mmkv.decodeBytes(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return getString(key, defaultValue, getMMKV());
    }

    public String getString(String key, String defaultValue, MMKV mmkv) {
        return mmkv.decodeString(key, defaultValue);
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return getStringSet(key, defaultValue, getMMKV());
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue, MMKV mmkv) {
        return mmkv.decodeStringSet(key, defaultValue);
    }

    public <T extends Parcelable> Parcelable getParcelable(String key, Class<T> defaultValue) {
        return getParcelable(key, defaultValue, getMMKV());
    }

    public <T extends Parcelable> Parcelable getParcelable(String key, Class<T> defaultValue, MMKV mmkv) {
        return mmkv.decodeParcelable(key, defaultValue);
    }

    /**
     * 移除某个key对
     *
     * @param key
     */
    public void removeKey(String key) {
        removeKey(getMMKV(), key);
    }

    /**
     * 移除多个key
     *
     * @param keys
     */
    public void removeKeys(String... keys) {
        removeKeys(getMMKV(), keys);
    }

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public boolean containKey(String key) {
        return containsKey(getMMKV(), key);
    }

    /**
     * 清除所有key
     */
    public void clearAll() {
        clearAll(getMMKV());
    }

    /**
     * 移除某个key对
     *
     * @param key
     * @param mmkv
     */
    public void removeKey(MMKV mmkv, String key) {
        mmkv.removeValueForKey(key);
    }

    /**
     * 移除多个key
     *
     * @param mmkv
     * @param keys
     */
    public void removeKeys(MMKV mmkv, String... keys) {
        mmkv.removeValuesForKeys(keys);
    }

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public boolean containsKey(MMKV mmkv, String key) {
        return mmkv.contains(key);
    }

    /**
     * 清除所有key
     *
     * @param mmkv
     */
    public void clearAll(MMKV mmkv) {
        mmkv.clearAll();
    }

}
