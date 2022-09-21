package com.zy.common.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


/**
 * @Author Liudeli
 * @Describe：日志打印相关工具类---[此类已不在使用，暂不删除]
 */
public class ELog {

    private static final String TAG = "Collect";

    public static final int SHOW_VERBOSE = 0;
    public static final int SHOW_DEBUG = 1;
    public static final int SHOW_INFO = 2;
    public static final int SHOW_WARN = 3;
    public static final int SHOW_ERROR = 4;

    public static int SHOW = SHOW_VERBOSE;

    public static void e(String tag, Object... obj) {
        Log.e(TAG, append(tag, obj));
        // com.orhanobut.logger.Logger.e(tag, obj);
    }

    public static void w(String tag, Object... obj) {
        Log.w(TAG, append(tag, obj));
        // com.orhanobut.logger.Logger.w(tag, obj);
    }

    public static void i(String tag, Object... obj) {
        Log.i(TAG, append(tag, obj));
        // com.orhanobut.logger.Logger.i(TAG, append(tag, obj));
    }

    public static void d(String tag, Object... obj) {
        Log.d(TAG, append(tag, obj));
        // com.orhanobut.logger.Logger.d(tag, obj);
    }

    public static void v(String tag, Object... obj) {
        Log.v(TAG, append(tag, obj));
        // com.orhanobut.logger.Logger.v(TAG, append(tag, obj));
    }

    public static final void showToastLong(Context context, String text) {
        toast(context, text, Toast.LENGTH_SHORT);
    }

    public static final void showToastShort(Context context, String text) {
        toast(context, text, Toast.LENGTH_SHORT);
    }

    public static final void showToast(Context context, String text, int duration) {
        toast(context, text, duration);
    }

    public static final void showToastLong(Context context, int res) {
        toast(context, res, Toast.LENGTH_LONG);
    }

    public static final void showToastShort(Context context, int res) {
        toast(context, res, Toast.LENGTH_SHORT);
    }

    public static final void showToast(Context context, int res, int duration) {
        toast(context, res, duration);
    }

    private static void toast(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    private static void toast(Context context, int res, int duration) {
        Toast.makeText(context, res, duration).show();
    }

    private static final String CHAR_RIGHT = "| ";
    private static final String CHAR_NULL = "null";

    private static String append(String tag, Object... obj) {
        int len = obj.length;
        if (len <= 0) {
            return tag;
        }

        StringBuilder sb = new StringBuilder(len + 2);
        sb.append(tag).append(CHAR_RIGHT);
        Object o;
        for (int i = 0; i < len; i++) {
            o = obj[i];
            if (null == o) {
                o = CHAR_NULL;
            }
            sb.append(o);
        }

        return sb.toString();
    }

}
