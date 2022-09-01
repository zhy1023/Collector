package com.zy.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Liudeli
 * @Describe：所有与UI相关操纵的工具类
 */
public class UIUtils {

    private static final String TAG = UIUtils.class.getSimpleName();

    /**
     * 通过ID获取颜色值
     * @param colorId
     * @return
     */
    public static int getColor(Context mContext, int colorId) {
        return mContext.getResources().getColor(colorId);
    }

    /**
     * 通过ID获取View
     * @param layoutId
     * @return
     */
    public static View getXmlVIew(Context mContext, int layoutId) {
        return View.inflate(mContext, layoutId, null);
    }

    /**
     * 通过ID获取 View Item 布局的View
     * @param mContext
     * @param layoutId
     * @return
     */
    public static View getItemView(Context mContext, int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    /**
     * dp转换px
     * @param dp
     * @return
     */
    public static int dp2px(Context mContext, int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }

    /**
     * px转换dp
     * @param px
     * @return
     */
    public static int px2dp(Context mContext, int px) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (px/density+0.5);
    }

    /**
     * 通过arrayId获取string.xml里面指定的arrayId字符串数组
     * @param arrayId
     * @return
     */
    public static String[] getStringArray(Context mContext, int arrayId) {
        return mContext.getResources().getStringArray(arrayId);
    }

    /**
     * 用于跳转Activity
     * @param cls
     */
    public static void startActivity(Activity activity, Class<?> cls) {
        activity.startActivity(new Intent(activity, cls));
    }

    /**
     * 用于ForResult方式跳转Activity
     * @param activity
     * @param cls
     * @param requestCode
     */
    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode) {
        activity.startActivityForResult(new Intent(activity, cls), requestCode);
    }

    /**
     * 待参数的方式跳转Activity
     * @param activity
     * @param cls
     * @param params
     * @param <T>
     */
    public static <T extends String> void startActivityForIntentParam(Activity activity, Class<?> cls, Map<String, T> params) {
        Intent intent  = new Intent(activity, cls);
        for (Map.Entry<String, T> entry : params.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        activity.startActivity(intent);
    }

    /**
     * 获取其他Activity传过来的参数，转成Map集合
     * @param activity
     * @param params
     * @return
     */
    public static Map receiveForIntentParam(Activity activity, String ... params) {
        Intent intent = activity.getIntent();
        Map<String, String> mMap = new HashMap<>();
        for (int i = 0; i<params.length; i++) {
            mMap.put(params[i], intent.getStringExtra(params[i]));
        }
        return mMap.size()==0?null:mMap;
    }

    /**
     * 设置EditText的hint字体大小
     * @param editText
     * @param dpSize
     * @param textString
     */
    public static void setEditTextHint(EditText editText, int dpSize,String textString) {
        SpannableString ss =  new SpannableString(textString);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(dpSize, true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_USER);
        editText.setHint(new SpannedString(ss));
    }

    /**
     * 设置EditText的hint颜色与字体大小
     * @param editText
     * @param color
     * @param dpSize
     * @param textString
     */
    public static void setEditTextHint(EditText editText, int color, int dpSize,String textString) {
        SpannableString ss =  new SpannableString(textString);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(dpSize, true);
        editText.setHintTextColor(color);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_USER);
        editText.setHint(new SpannedString(ss));
    }

    /**
     * 设置EditText的hint颜色与字体大小
     * @param editText
     * @param color
     * @param dpSize
     * @param textString
     * @param isDip
     */
    public static void setEditTextHint(EditText editText, int color, int dpSize,String textString, boolean isDip) {
        SpannableString ss =  new SpannableString(textString);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(dpSize, isDip);
        editText.setHintTextColor(color);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    /**
     * Java代码设置控件大小，这个方法没有什么卵用
     * @param view  控件
     * @param width 宽度，单位：像素
     * @param height 高度，单位：像素
     */
    public static void setViewSize(View view,int width,int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

}
