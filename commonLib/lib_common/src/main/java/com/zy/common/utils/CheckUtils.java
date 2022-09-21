package com.zy.common.utils;

import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * Author:zhy
 * Date:2022/3/8
 */
public final class CheckUtils {

    private CheckUtils() {
        throw new UnsupportedOperationException("Utils can not be instantiated!");
    }

    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(@Nullable Collection<?> col) {
        return col == null || col.isEmpty();
    }

    public static boolean isEmpty(@Nullable Map<?, ?> col) {
        return col == null || col.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static @NonNull
    <T> T checkNotNull(T reference, String message) {
        if (reference == null) {
            throw new NullPointerException(message);
        }
        return reference;
    }

    /**
     * 主线程异常警告
     */
    public static void checkMainThread() {
        if (!isMainThread()) {
            throw new IllegalStateException("mainThread is required.");
        }
    }

    /**
     * 检测是否是主线程
     */
    public static boolean isMainThread(){
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
