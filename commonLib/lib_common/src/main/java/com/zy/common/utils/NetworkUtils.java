package com.zy.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author Liudeli
 * @Describe：网络类型，网络状态，网络制式，等相关工具类
 */
public final class NetworkUtils {
    private NetworkUtils() {
        throw new AssertionError();
    }

    /**
     * 未找到合适匹配网络类型
     */
    public static final int TYPE_NO = 0;

    /**
     * 中国移动CMNET网络
     * 中国移动GPRS接入方式之一, 主要为PC、笔记本电脑、PDA设立
     */
    public static final int TYPE_MOBILE_CMNET = 1;

    /**
     * 中国移动CMWAP网络
     * 中国移动GPRS接入方式之一,主要为手机WAP上网而设立
     */
    public static final int TYPE_MOBILE_CMWAP = 2;

    /**
     * 中国联通UNIWAP网络
     * 中国联通划分GPRS接入方式之一, 主要为手机WAP上网而设立
     */
    public static final int TYPE_MOBILE_UNIWAP = 3;

    /**
     * 中国联通UNINET网络
     * 中国联通划分GPRS接入方式之一, 主要为PC、笔记本电脑、PDA设立
     */
    public static final int TYPE_MOBILE_UNINET = 6;

    /**
     * 中国联通3GWAP网络
     */
    public static final int TYPE_MOBILE_3GWAP = 4;

    // 中国联通3HNET网络
    public static final int TYPE_MOBLIE_3GNET = 5;

    /**
     * 中国电信CTWAP网络
     */
    public static final int TYPE_MOBILE_CTWAP = 7;

    /**
     * 中国电信CTNET网络
     */
    public static final int TYPE_MOBILE_CTNET = 8;

    /**
     * WIFI网络
     */
    public static final int TYPE_WIFI = 10;

    /**
     * 网络类型 - 无连接
     */
    public static final int NETWORK_TYPE_NO_CONNECTION = -100000;

    /**
     * 连接模式WIFI网
     */
    public static final String NETWORK_TYPE_WIFI = "WIFI";
    /**
     * 连接模式快速网
     */
    public static final String NETWORK_TYPE_FAST = "FAST";
    /**
     * 连接模式慢速网
     */
    public static final String NETWORK_TYPE_SLOW = "SLOW";
    /**
     * 连接模式wap网
     */
    public static final String NETWORK_TYPE_WAP = "WAP";
    /**
     * 连接模式unknow
     */
    public static final String NETWORK_TYPE_UNKNOWN = "UNKNOWN";
    /**
     * 无连接
     */
    public static final String NETWORK_TYPE_DISCONNECT = "DISCONNECT";

    /**
     * 获取当前手机连接的网络类型
     *
     * @param context 上下文
     * @return int 网络类型
     */
    public static int getNetworkState(Context context) {
        int returnValue = TYPE_NO;
        // 获取ConnectivityManager对象
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获得当前网络信息
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            // 获取网络类型
            int currentNetWork = networkInfo.getType();
            // 手机网络类型
            if (currentNetWork == ConnectivityManager.TYPE_MOBILE) {
                if (networkInfo.getExtraInfo() != null) {
                    if (networkInfo.getExtraInfo().equals("cmnet")) {
                        returnValue = TYPE_MOBILE_CMNET;
                    }
                    if (networkInfo.getExtraInfo().equals("cmwap")) {
                        returnValue = TYPE_MOBILE_CMWAP;
                    }
                    if (networkInfo.getExtraInfo().equals("uniwap")) {
                        returnValue = TYPE_MOBILE_UNIWAP;
                    }
                    if (networkInfo.getExtraInfo().equals("3gwap")) {
                        returnValue = TYPE_MOBILE_3GWAP;
                    }
                    if (networkInfo.getExtraInfo().equals("3gnet")) {
                        returnValue = TYPE_MOBLIE_3GNET;
                    }
                    if (networkInfo.getExtraInfo().equals("uninet")) {
                        returnValue = TYPE_MOBILE_UNINET;
                    }
                    if (networkInfo.getExtraInfo().equals("ctwap")) {
                        returnValue = TYPE_MOBILE_CTWAP;
                    }
                    if (networkInfo.getExtraInfo().equals("ctnet")) {
                        returnValue = TYPE_MOBILE_CTNET;
                    }
                }
                // WIFI网络类型
            } else if (currentNetWork == ConnectivityManager.TYPE_WIFI) {
                returnValue = TYPE_WIFI;
            }
        }
        return returnValue;
    }

    /**
     * 判断网络是否连接
     *
     * @param context 上下文
     * @return boolean 网络连接状态
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            // 获取连接对象
            if (null != info && info.isConnected()) {
                return info.getState() == State.CONNECTED;
            }
        }
        return false;
    }


    /**
     * 打开网络设置界面
     *
     * @param activity Activity
     */
    public static void openNetSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm =
                new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 是否是用手机网络连接
     *
     * @param context 上下文
     * @return 结果
     */
    public static boolean isFlowConnect(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == cm || null == cm.getActiveNetworkInfo()) {
            return false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 是否是wifi连接
     *
     * @param context app的context
     * @return true:是wifi连接
     */
    public static boolean isWifiConnect(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == cm || null == cm.getActiveNetworkInfo()) {
            return false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取网络连接类型
     *
     * @param context context
     * @return NetworkType
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == cm || null == cm.getActiveNetworkInfo()) {
            return TYPE_NO;
        }
        return cm.getActiveNetworkInfo().getType();
    }


    /**
     * 获取网络连接类型名称
     *
     * @param context context
     * @return NetworkTypeName
     */
    public static String getNetworkTypeName(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        String type = NETWORK_TYPE_DISCONNECT;
        if (cm == null || info == null) {
            return type;
        }
        if (info.isConnected()) {
            String typeName = info.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
                type = NETWORK_TYPE_WIFI;
            } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                if (StringUtil.isEmpty(proxyHost)) {
                    type = isFastMobileNetwork(context) ? NETWORK_TYPE_FAST : NETWORK_TYPE_SLOW;
                } else {
                    type = NETWORK_TYPE_WAP;
                }
            } else {
                type = NETWORK_TYPE_UNKNOWN;
            }
        }
        return type;
    }


    /**
     * Whether is fast mobile network
     *
     * @param context context
     * @return FastMobileNetwork
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            default:
                return false;
        }
    }


    /**
     * 获取当前网络的状态
     *
     * @param context 上下文
     * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。
     * 当前没有网络连接时返回null
     */
    public static State getCurrentNetworkState(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (null == info) {
            return null;
        }
        return info.getState();
    }


    /**
     * 获取当前网络的类型
     *
     * @param context 上下文
     * @return 当前网络的类型。具体类型可参照ConnectivityManager中的TYPE_BLUETOOTH、TYPE_MOBILE、TYPE_WIFI等字段。
     * 当前没有网络连接时返回NetworkUtils.NETWORK_TYPE_NO_CONNECTION
     */
    public static int getCurrentNetworkType(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null ? info.getType() : NETWORK_TYPE_NO_CONNECTION;
    }


    /**
     * 获取当前网络的具体类型
     *
     * @param context 上下文
     * @return 当前网络的具体类型。具体类型可参照TelephonyManager中的NETWORK_TYPE_1xRTT、NETWORK_TYPE_CDMA等字段。
     * 当前没有网络连接时返回NetworkUtils.NETWORK_TYPE_NO_CONNECTION
     */
    public static int getCurrentNetworkSubtype(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null ? info.getSubtype() : NETWORK_TYPE_NO_CONNECTION;
    }


    /**
     * 判断当前网络是否已经连接
     *
     * @param context 上下文
     * @return 当前网络是否已经连接。false：尚未连接
     */
    public static boolean isConnectedByState(Context context) {
        return getCurrentNetworkState(context) == State.CONNECTED;
    }


    /**
     * 判断当前网络是否正在连接
     *
     * @param context 上下文
     * @return 当前网络是否正在连接
     */
    public static boolean isConnectingByState(Context context) {
        return getCurrentNetworkState(context) == State.CONNECTING;
    }


    /**
     * 判断当前网络是否已经断开
     *
     * @param context 上下文
     * @return 当前网络是否已经断开
     */
    public static boolean isDisconnectedByState(Context context) {
        return getCurrentNetworkState(context) == State.DISCONNECTED;
    }


    /**
     * 判断当前网络是否正在断开
     *
     * @param context 上下文
     * @return 当前网络是否正在断开
     */
    public static boolean isDisconnectingByState(Context context) {
        return getCurrentNetworkState(context) == State.DISCONNECTING;
    }


    /**
     * 判断当前网络是否已经暂停
     *
     * @param context 上下文
     * @return 当前网络是否已经暂停
     */
    public static boolean isSuspendedByState(Context context) {
        return getCurrentNetworkState(context) == State.SUSPENDED;
    }


    /**
     * 判断当前网络是否处于未知状态中
     *
     * @param context 上下文
     * @return 当前网络是否处于未知状态中
     */
    public static boolean isUnknownByState(Context context) {
        return getCurrentNetworkState(context) == State.UNKNOWN;
    }


    /**
     * 判断当前网络的类型是否是蓝牙
     *
     * @param context 上下文
     * @return 当前网络的类型是否是蓝牙。false：当前没有网络连接或者网络类型不是蓝牙
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static boolean isBluetoothByType(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return false;
        } else {
            return getCurrentNetworkType(context) == ConnectivityManager.TYPE_BLUETOOTH;
        }
    }


    /**
     * 判断当前网络的类型是否是虚拟网络
     *
     * @param context 上下文
     * @return 当前网络的类型是否是虚拟网络。false：当前没有网络连接或者网络类型不是虚拟网络
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean isDummyByType(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return false;
        } else {
            return getCurrentNetworkType(context) == ConnectivityManager.TYPE_DUMMY;
        }
    }


    /**
     * 判断当前网络的类型是否是ETHERNET
     *
     * @param context 上下文
     * @return 当前网络的类型是否是ETHERNET。false：当前没有网络连接或者网络类型不是ETHERNET
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static boolean isEthernetByType(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return false;
        } else {
            return getCurrentNetworkType(context) == ConnectivityManager.TYPE_ETHERNET;
        }
    }


    /**
     * 判断当前网络的类型是否是移动网络
     *
     * @param context 上下文
     * @return 当前网络的类型是否是移动网络。false：当前没有网络连接或者网络类型不是移动网络
     */
    public static boolean isMobileByType(Context context) {
        return getCurrentNetworkType(context) == ConnectivityManager.TYPE_MOBILE;
    }


    /**
     * 判断当前网络的类型是否是MobileDun
     *
     * @param context 上下文
     * @return 当前网络的类型是否是MobileDun。false：当前没有网络连接或者网络类型不是MobileDun
     */
    public static boolean isMobileDunByType(Context context) {
        return getCurrentNetworkType(context) == ConnectivityManager.TYPE_MOBILE_DUN;
    }


    /**
     * 判断当前网络的类型是否是MobileHipri
     *
     * @param context 上下文
     * @return 当前网络的类型是否是MobileHipri。false：当前没有网络连接或者网络类型不是MobileHipri
     */
    public static boolean isMobileHipriByType(Context context) {
        return getCurrentNetworkType(context) == ConnectivityManager.TYPE_MOBILE_HIPRI;
    }


    /**
     * 判断当前网络的类型是否是MobileMms
     *
     * @param context 上下文
     * @return 当前网络的类型是否是MobileMms。false：当前没有网络连接或者网络类型不是MobileMms
     */
    public static boolean isMobileMmsByType(Context context) {
        return getCurrentNetworkType(context) == ConnectivityManager.TYPE_MOBILE_MMS;
    }


    /**
     * 判断当前网络的类型是否是MobileSupl
     *
     * @param context 上下文
     * @return 当前网络的类型是否是MobileSupl。false：当前没有网络连接或者网络类型不是MobileSupl
     */
    public static boolean isMobileSuplByType(Context context) {
        return getCurrentNetworkType(context) == ConnectivityManager.TYPE_MOBILE_SUPL;
    }

    /**
     * 判断当前网络的类型是否是Wimax
     *
     * @param context 上下文
     * @return 当前网络的类型是否是Wimax。false：当前没有网络连接或者网络类型不是Wimax
     */
    public static boolean isWimaxByType(Context context) {
        return getCurrentNetworkType(context) == ConnectivityManager.TYPE_WIMAX;
    }


    /**
     * 判断当前网络的具体类型是否是1XRTT
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是1XRTT。false：当前没有网络连接或者具体类型不是1XRTT
     */
    public static boolean is1XRTTBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_1xRTT;
    }


    /**
     * 判断当前网络的具体类型是否是CDMA（Either IS95A or IS95B）
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是CDMA。false：当前没有网络连接或者具体类型不是CDMA
     */
    public static boolean isCDMABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_CDMA;
    }


    /**
     * 判断当前网络的具体类型是否是EDGE
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是EDGE。false：当前没有网络连接或者具体类型不是EDGE
     */
    public static boolean isEDGEBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_EDGE;
    }


    /**
     * 判断当前网络的具体类型是否是EHRPD
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是EHRPD。false：当前没有网络连接或者具体类型不是EHRPD
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static boolean isEHRPDBySubtype(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return false;
        } else {
            return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_EHRPD;
        }
    }


    /**
     * 判断当前网络的具体类型是否是EVDO_0
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是EVDO_0。false：当前没有网络连接或者具体类型不是EVDO_0
     */
    public static boolean isEVDO0BySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_EVDO_0;
    }


    /**
     * 判断当前网络的具体类型是否是EVDO_A
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是EVDO_A。false：当前没有网络连接或者具体类型不是EVDO_A
     */
    public static boolean isEVDOABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_EVDO_A;
    }


    /**
     * 判断当前网络的具体类型是否是EDGE
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是EVDO_B。false：当前没有网络连接或者具体类型不是EVDO_B
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isEVDOBBySubtype(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        } else {
            return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_EVDO_B;
        }
    }


    /**
     * 判断当前网络的具体类型是否是GPRS
     *
     * @param context app的context
     * @return false：当前网络的具体类型是否是GPRS。false：当前没有网络连接或者具体类型不是GPRS
     */
    public static boolean isGPRSBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_GPRS;
    }


    /**
     * 判断当前网络的具体类型是否是HSDPA
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是HSDPA。false：当前没有网络连接或者具体类型不是HSDPA
     */
    public static boolean isHSDPABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_HSDPA;
    }


    /**
     * 判断当前网络的具体类型是否是HSPA
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是HSPA。false：当前没有网络连接或者具体类型不是HSPA
     */
    public static boolean isHSPABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_HSPA;
    }


    /**
     * 判断当前网络的具体类型是否是HSPAP
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是HSPAP。false：当前没有网络连接或者具体类型不是HSPAP
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static boolean isHSPAPBySubtype(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return false;
        } else {
            return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_HSPAP;
        }
    }


    /**
     * 判断当前网络的具体类型是否是HSUPA
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是HSUPA。false：当前没有网络连接或者具体类型不是HSUPA
     */
    public static boolean isHSUPABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_HSUPA;
    }


    /**
     * 判断当前网络的具体类型是否是IDEN
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是IDEN。false：当前没有网络连接或者具体类型不是IDEN
     */
    public static boolean isIDENBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_IDEN;
    }


    /**
     * 判断当前网络的具体类型是否是LTE
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是LTE。false：当前没有网络连接或者具体类型不是LTE
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static boolean isLTEBySubtype(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return false;
        } else {
            return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_LTE;
        }
    }


    /**
     * 判断当前网络的具体类型是否是UMTS
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是UMTS。false：当前没有网络连接或者具体类型不是UMTS
     */
    public static boolean isUMTSBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_UMTS;
    }


    /**
     * 判断当前网络的具体类型是否是UNKNOWN
     *
     * @param context 上下文
     * @return false：当前网络的具体类型是否是UNKNOWN。false：当前没有网络连接或者具体类型不是UNKNOWN
     */
    public static boolean isUNKNOWNBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == TelephonyManager.NETWORK_TYPE_UNKNOWN;
    }


    /**
     * 判断当前网络是否是中国移动2G网络
     *
     * @param context 上下文
     * @return false：不是中国移动2G网络或者当前没有网络连接
     */
    public static boolean isChinaMobile2G(Context context) {
        return isEDGEBySubtype(context);
    }


    /**
     * 判断当前网络是否是中国联通2G网络
     *
     * @param context 上下文
     * @return false：不是中国联通2G网络或者当前没有网络连接
     */
    public static boolean isChinaUnicom2G(Context context) {
        return isGPRSBySubtype(context);
    }


    /**
     * 判断当前网络是否是中国联通3G网络
     *
     * @param context 上下文
     * @return false：不是中国联通3G网络或者当前没有网络连接
     */
    public static boolean isChinaUnicom3G(Context context) {
        return isHSDPABySubtype(context) || isUMTSBySubtype(context);
    }


    /**
     * 判断当前网络是否是中国电信2G网络
     *
     * @param context 上下文
     * @return false：不是中国电信2G网络或者当前没有网络连接
     */
    public static boolean isChinaTelecom2G(Context context) {
        return isCDMABySubtype(context);
    }


    /**
     * 判断当前网络是否是中国电信3G网络
     *
     * @param context 上下文
     * @return false：不是中国电信3G网络或者当前没有网络连接
     */
    public static boolean isChinaTelecom3G(Context context) {
        return isEVDO0BySubtype(context) || isEVDOABySubtype(context) || isEVDOBBySubtype(context);
    }


    /**
     * 获取Wifi的状态，需要ACCESS_WIFI_STATE权限
     *
     * @param context 上下文
     * @return 取值为WifiManager中的WIFI_STATE_ENABLED、WIFI_STATE_ENABLING、WIFI_STATE_DISABLED、
     * WIFI_STATE_DISABLING、WIFI_STATE_UNKNOWN之一
     * @throws Exception 没有找到wifi设备
     */
    public static int getWifiState(Context context) throws Exception {
        WifiManager wifiManager = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE));
        if (wifiManager != null) {
            return wifiManager.getWifiState();
        } else {
            throw new Exception("wifi device not found!");
        }
    }

    /**
     * 判断Wifi是否打开，需要ACCESS_WIFI_STATE权限
     *
     * @param context 上下文
     * @return true：打开；false：关闭
     * @throws Exception
     */
    public static boolean isWifiOpen(Context context) throws Exception {
        int wifiState = getWifiState(context);
        return wifiState == WifiManager.WIFI_STATE_ENABLED
                || wifiState == WifiManager.WIFI_STATE_ENABLING ? true : false;
    }


    /**
     * 设置Wifi，需要CHANGE_WIFI_STATE权限
     *
     * @param context 上下文
     * @param enable  wifi状态
     * @return 设置是否成功
     * @throws Exception 是否授予WiFi权限
     */
    public static boolean setWifi(Context context, boolean enable) throws Exception {
        // 如果当前wifi的状态和要设置的状态不一样
        if (isWifiOpen(context) != enable) {
            ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).setWifiEnabled(enable);
        }
        return true;
    }

    /**
     * 判断移动网络是否打开，需要ACCESS_NETWORK_STATE权限
     *
     * @param context 上下文
     * @return true：打开；false：关闭
     */
    public static boolean isMobileNetworkOpen(Context context) {
        return (((ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE)).getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE)).isConnected();
    }

    /**
     * 获取本机IP地址
     *
     * @return null：没有网络连接
     */
    public static String getIpAddress() {
        try {
            NetworkInterface nerworkInterface;
            InetAddress inetAddress;
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements(); ) {
                nerworkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = nerworkInterface.getInetAddresses(); enumIpAddr
                        .hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
            return null;
        } catch (SocketException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 设置数据流量状态
     *
     * @param context app的context
     * @param enabled 是否可用
     */
    public static void setDataEnabled(Context context, boolean enabled) {
        ConnectivityManager conMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> conMgrClass = null;
        Field iConMgrField = null;
        Object iConMgr = null;
        Class<?> iConMgrClass = null;
        Method setMobileDataEnabledMethod = null;
        try {
            conMgrClass = Class.forName(conMgr.getClass().getName());
            iConMgrField = conMgrClass.getDeclaredField("mService");
            iConMgrField.setAccessible(true);
            iConMgr = iConMgrField.get(conMgr);
            iConMgrClass = Class.forName(iConMgr.getClass().getName());
            setMobileDataEnabledMethod =
                    iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(iConMgr, enabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取wifi列表
     *
     * @param context app的context
     * @return wifi列表
     */
    public static List<ScanResult> getWifiScanResults(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.startScan() ? wifiManager.getScanResults() : null;
    }

    /**
     * 过滤扫描结果
     *
     * @param context app的context
     * @param bssid   过滤的字符串
     * @return 过滤后的wifi列表
     */
    public static ScanResult getScanResultsByBSSID(Context context, String bssid) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        ScanResult scanResult = null;
        boolean f = wifiManager.startScan();
        if (!f) {
            getScanResultsByBSSID(context, bssid);
        }
        List<ScanResult> list = wifiManager.getScanResults();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                scanResult = list.get(i);
                if (scanResult.BSSID.equals(bssid)) {
                    break;
                }
            }
        }
        return scanResult;
    }

    /**
     * 获取wifi连接信息
     *
     * @param context app的context
     * @return wifi信息
     */
    public static WifiInfo getWifiConnectionInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo();
    }

    /**
     * 获得Proxy地址
     *
     * @param context 上下文
     * @return Proxy地址
     */
    public static String getProxy(Context context) {
        String proxy = null;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isAvailable()) {
                String stringExtraInfo = networkinfo.getExtraInfo();
                if (stringExtraInfo != null
                        && ("cmwap".equals(stringExtraInfo) || "uniwap".equals(stringExtraInfo))) {
                    proxy = "10.0.0.172:80";
                } else if (stringExtraInfo != null && "ctwap".equals(stringExtraInfo)) {
                    proxy = "10.0.0.200:80";
                }
            }
        }

        return proxy;
    }


    // -------------

    /**
     * 判断当前是否有网络连接
     * @param context
     * @return	有网络返回true；无网络返回false
     */
    @SuppressWarnings("null")
    public static boolean isNetWorkEnable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null || networkInfo.isConnected()) {
            if (networkInfo.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前网络是否为wifi
     * @param context
     * @return	如果为wifi返回true；否则返回false
     */
    @SuppressWarnings("static-access")
    public static boolean isWiFiConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo.getType() == manager.TYPE_WIFI ? true : false;
    }

    /**
     * 判断MOBILE网络是否可用
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isMobileDataEnable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;
        isMobileDataEnable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return isMobileDataEnable;
    }

    /**
     * 判断wifi 是否可用
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isWifiDataEnable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable = false;
        isWifiDataEnable = manager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;
    }

    /**
     * 跳转到网络设置页面
     * @param activity
     */
    public static void GoSetting(Activity activity){
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cn);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
