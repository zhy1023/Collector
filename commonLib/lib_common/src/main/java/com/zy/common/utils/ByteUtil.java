package com.zy.common.utils;

/**
 * @Author Liudeli
 * @Describe：
 */
public class ByteUtil {

    private ByteUtil(){}

    /**
     * 把byte[] 转成 Stirng
     * @param bytes byte[]
     * @return
     */
    // @RequiresApi(api = Build.VERSION_CODES.O)
    public static String byteToString(byte[] bytes) {
        // return Base64.getEncoder().encodeToString(bytes);
        return new String(bytes);
    }

    /**
     * 把String 转成 byte[]
     * @param string
     * @return
     */
    // @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] StringToByte(String string) {
        // return Base64.getDecoder().decode(string);
        return string.getBytes();
    }
}
