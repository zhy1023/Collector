package com.zy.common.utils;

import android.util.Base64;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author Liudeli
 * @Describe：RSA加解密工具类
 */
public class RSAUtils {

    private static final String TAG = "RSAUtils";

    private final static String UTF_8 = "UTF-8";
    // 10位公共偏移量
    private final static String KEY_SUFFIX = "14293300FF";
    private static byte[] iv;

    static {
        try {
            iv = "0123456789ABCDEF".getBytes(UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    static String AES_CBC_PKCS5Padding = "AES/CBC/PKCS5Padding";
    static String RSA_ECB_PKCS1Padding = "RSA/ECB/PKCS1Padding";
    static String MD5 = "MD5";
    static int MD5_LEN = 32;

    private static String[] TYPES = {AES_CBC_PKCS5Padding,
            RSA_ECB_PKCS1Padding};
    private static Integer[] MODES = {Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE};

    public static Key prvKey;
    public static Key pubKey;
    private static X509Certificate pubCert;
    // static String privateFile = RSAUtils.class.getClassLoader().getResource("").getPath() + "pkcs8_private_key.der";
    static String privateFile;
    // 公钥文件路径
    // static String publicFile = RSAUtils.class.getClassLoader().getResource("").getPath() + "rsacert.der";
    public static InputStream publicFis;
    public static String encode = UTF_8;// 保持平台兼容统一使用utf-8


    private static String encryptMD5(String data) throws Exception {
        return doEncryptMD5(data, MD5);
    }

    public static SecretKey getKeyAES(String strKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(strKey.getBytes(encode), "AES");
        return key;
    }

    public static HashMap<String, String> genMap(String acc)
            throws RuntimeException {
        System.out.println("accept data:" + acc);
        HashMap<String, String> tmpMap = new HashMap<>();
        if (acc == null || acc.length() < 26) {
            throw new RuntimeException("非法数据");
        }
        // 第一个|在第24位(从0开始算)
        if (acc.indexOf("|") == MD5_LEN) {
            String md5 = acc.substring(0, MD5_LEN);
            acc = acc.substring(MD5_LEN + 1);
            tmpMap.put("md5", md5);
            // 第二个|在第8位及以后(从0开始算)
            int tmpInt = acc.indexOf("|");
            if (acc.length() > 9 && tmpInt > 7 && tmpInt % 2 == 0) {
                String data = acc.substring(0, tmpInt);
                acc = acc.substring(tmpInt + 1);
                tmpMap.put("data", data);
                // 第二个|后数据长度都在16以上
                tmpInt = acc.length();
                if (tmpInt > 15) {
                    tmpMap.put("key", acc);
                } else {
                    throw new RuntimeException("非法key数据");
                }
            } else {
                throw new RuntimeException("非法data数据");
            }
        } else {
            throw new RuntimeException("非法md5数据");
        }
        return tmpMap;
    }

    /**
     * //默认加密 MD5 AES_CBC_PKCS5Padding RSA_ECB_PKCS1Padding（私钥加密）
     *
     * @param dataToEncypt 数据
     * @param pwd          对称密钥
     * @return Map
     * @throws Exception
     */
    public static HashMap<String, String> defaultEncrypt(byte[] dataToEncypt,
                                                         String pwd) throws Exception {
        return encrypt(dataToEncypt, pwd, true);
    }

    /**
     * //默认加密 MD5 AES_CBC_PKCS5Padding RSA_ECB_PKCS1Padding（私钥加密）
     *
     * @param dataToEncypt 数据
     * @param pwd          对称密钥
     * @param isPrvEncrypt 是否使用私钥加密
     * @return Map
     * @throws Exception
     */
    public static HashMap<String, String> encrypt(byte[] dataToEncypt,
                                                  String pwd, boolean isPrvEncrypt) throws Exception {
        if (pwd == null || pwd.getBytes(encode).length != 6) {
            throw new RuntimeException("非法密钥");
        }
        Key key = prvKey;
        if (!isPrvEncrypt) {
            key = pubKey;
        }

        // md5 key+data
        //      byte[] md5Byte = encryptMD5(pwd + new String(dataToEncypt, encode));
        //      String md5Base64 = Base64Utils.encode(md5Byte);
        String md5Base64 = doEncryptMD5(pwd + new String(dataToEncypt, encode), MD5);
        byte[] encryptData = doCrypt(AES_CBC_PKCS5Padding, Cipher.ENCRYPT_MODE,
                new IvParameterSpec(iv), getKeyAES(pwd + KEY_SUFFIX),
                dataToEncypt);
        //      String dataBase64 = Base64Utils.encode(encryptData);
        String dataBase64 = doBase64Encode(encryptData);
        byte[] encryptKey = doCrypt(RSA_ECB_PKCS1Padding, Cipher.ENCRYPT_MODE,
                null, key, pwd.getBytes(encode));
        //      String keyBase64 = Base64Utils.encode(encryptKey);
        String keyBase64 = doBase64Encode(encryptKey);

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("data", dataBase64);
        data.put("key", keyBase64);
        data.put("md5", md5Base64);
        data.put("send", md5Base64 + "|" + dataBase64 + "|" + keyBase64);
        return data;
    }

    /**
     * //默认解密 MD5 AES_CBC_PKCS5Padding RSA_ECB_PKCS1Padding（公钥解密）
     *
     * @paramdataToEncypt 数据
     * @parampwd          对称密钥
     * @return Map
     * @throws Exception
     */
    public static String defaultDecrypt(HashMap<String, String> data)
            throws Exception {
        return decrypt(data, true);
    }

    /**
     * //默认解密 MD5 AES_CBC_PKCS5Padding RSA_ECB_PKCS1Padding（公钥解密）
     *
     * @paramdataToEncypt 数据
     * @parampwd          对称密钥
     * @param isPubDecrypt 是否使用公钥解密
     * @return Map
     * @throws Exception
     */
    public static String decrypt(HashMap<String, String> data,
                                 boolean isPubDecrypt) throws Exception {
        Key key = pubKey;
        if (!isPubDecrypt) {
            key = prvKey;
        }

        String dataBase64 = data.get("data");
        String keyBase64 = data.get("key");
        String md5Base64Src = data.get("md5");

        //      byte[] encryptedKey = Base64Utils.decode(keyBase64);
        byte[] encryptedKey = doBase64Decode(keyBase64);
        byte[] decryptedKey = doCrypt(RSA_ECB_PKCS1Padding,
                Cipher.DECRYPT_MODE, null, key, encryptedKey);
        String pwd = new String(decryptedKey, encode);
        if (pwd.getBytes(encode).length != 6) {
            throw new RuntimeException("伪造密钥");
        }
        //      byte[] encryptedData = Base64Utils.decode(dataBase64);
        byte[] encryptedData = doBase64Decode(dataBase64);
        byte[] decryptedData = doCrypt(AES_CBC_PKCS5Padding,
                Cipher.DECRYPT_MODE, new IvParameterSpec(iv), getKeyAES(pwd
                        + KEY_SUFFIX), encryptedData);
        String textDecrypt = new String(decryptedData, encode);
        // md5 key+data
        //      byte[] md5Byte = encryptMD5(pwd + textDecrypt);
        //      String md5Base64 = Base64Utils.encode(md5Byte);
        String md5Base64 = doEncryptMD5(pwd + textDecrypt, MD5);
        if (!md5Base64.equals(md5Base64Src)) {
            throw new RuntimeException("伪造数据");
        }
        return textDecrypt;
    }

    public static Key getPubKey() throws CertificateException, IOException {
        // 读取公钥
        CertificateFactory certificatefactory = CertificateFactory
                .getInstance("X.509");
        InputStream bais = publicFis;
        Certificate cert = certificatefactory.generateCertificate(bais);
        bais.close();
        pubCert = (X509Certificate) cert;
        PublicKey puk = cert.getPublicKey();
        return puk;
    }

    public static Key getPrvKey() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        FileInputStream in = new FileInputStream(privateFile);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] tmpbuf = new byte[1024];
        int count = 0;
        while ((count = in.read(tmpbuf)) != -1) {
            bout.write(tmpbuf, 0, count);
        }
        in.close();
        // 读取私钥
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                bout.toByteArray());
        PrivateKey prk = keyFactory.generatePrivate(privateKeySpec);
        return prk;
    }

    public static String doEncryptMD5(String data, String type)
            throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes(StandardCharsets.UTF_8));
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (byte value : b) {
            i = value;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        if (MD5_LEN == 32) {
            // 32位加密
            return buf.toString().toUpperCase();
        } else {
            // 16位的加密
            return buf.toString().substring(8, 24);
        }
    }

    public static String getPassword() {
        String pwd = (new Random().nextInt(1000000) + 1000000 + "")
                .substring(1);
        return pwd;
    }

    // pkcs8_der.key文件为私钥 只能保存在服务端
    // public_key.der为公钥文件，保存在客户端
    public static void main(String[] args) {
        try {
            prvKey = getPrvKey();
            pubKey = getPubKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = "{\"TxnCode\":\"hce1003\",\"TxnStatus\":\"true\",\"ReplyCode\":\"000000\",\"ReplyMsg\":\"交易成功\",\"Data\":\"[{\\\"accountNo\\\":\\\"623091019******3297\\\",\\\"accountType\\\":\\\"1\\\",\\\"certNo\\\":\\\"***\\\",\\\"certType\\\":\\\"101\\\",\\\"customerName\\\":\\\"***\\\",\\\"mobileNo\\\":\\\"***\\\"},{\\\"accountNo\\\":\\\"***\\\",\\\"accountType\\\":\\\"1\\\",\\\"certNo\\\":\\\"***\\\",\\\"certType\\\":\\\"101\\\",\\\"customerName\\\":\\\"***\\\",\\\"mobileNo\\\":\\\"***\\\"}]\"}";
        // String text = "c";
        //      String text = "提起黄飞鸿，我们隐隐约约地知道他是一个真实的历史人物，但恐怕很少有人能在脑海中勾勒出他本人真实的面貌，上了岁数的人大概还依稀记得关德兴那张冷峻硬朗的面庞；三四十岁左右的人想到的应该是风度翩翩的李连杰，关之琳扮演娇美的十三姨，如影随形不离左右；再年轻一点的观众或许更倾心于赵文卓甚至是彭于晏的扮相。“黄飞鸿”这个名字，经过文学和影视作品的塑造，早已由一个平凡的历史人物变成了整个华人世界的偶像，侠之大者、一代宗师的化身。那么，真实历史中的黄飞鸿，究竟是怎样一个人？为什么在他死后，会由一介平凡的岭南武师，变为全体华人心目中的英雄？";
        String pwd = getPassword();
        //       String pwd="663810";
        // 加密
        HashMap<String, String> data;
        try {
            data = defaultEncrypt(text.getBytes(encode), pwd);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String accept = data.get("send");
        // String accept =
        // "mQ6uBpadJfNFxFHez3zWwQ==|2xZJpm3xO5gKaID9pYYqfTQ8e64GHo4esj7E51DlyCZM32DJAPUN3RKqLFkfR5rUCA+SsSD8vQOH+iS/Uh7YlCZhatuTOgNqJi6TfLbp4yZx4iTqFRda5jBVD1vNgsUf8jZdoJLY6rg2OhvcOjL+/lGeijdVv5f0RkykAtfKUHWeO5jWk+jUALQqx/ugO46Npna6MoeelUDzIbmdHL2NmZRmDzPvqpkQmUz9Pk8P19R/kWZuVfxVuOzPuaic69Roq1zbNyrKhGfqITQRwSsVOGMQi3qGYfY3Sv/hrbIuwZ4=|eAoqLUr9bC+sfACRZN6UFnm9g8R8h/jtq1k50m5aOB6AhxtaJWN/PiE2iaHBwF8a5z1gqdQdt0HERLFZm6tzvE6N2+RwF/XylK4oxfhLeCGOW85ahpnwlEpVGD86oCq8JRp4fglhaFkt9MAwmfpWGnT6GIlB9OiXFzNkIgrUdIk=";
        HashMap<String, String> acceptMap = genMap(accept);
        // 解密
        try {
            defaultDecrypt(acceptMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type   算法/加密方式/填充方式 如：AES/CBC/PKCS5Padding
     * @param mode   加密/或者解密 Cipher.DECRYPT_MODE/Cipher.ENCRYPT_MODE
     * @param zeroIv 初始化向量 如：new IvParameterSpec(iv)
     * @param key    密钥
     * @param data   需要加密的数据
     * @return byte[]
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] doCrypt(String type, int mode, IvParameterSpec zeroIv,
                                 Key key, byte[] data) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        if (!checkCrypt(type, mode, zeroIv, key, data)) {
            throw new RuntimeException("参数非法");
        }

        if (!pubCertValid()) {
            throw new RuntimeException("证书失效");
        }

        Cipher cipher = Cipher.getInstance(type);
        if (type.contains("CBC") && zeroIv != null) {
            cipher.init(mode, key, zeroIv);
        } else {
            cipher.init(mode, key);
        }
        return cipher.doFinal(data);
    }

    private static boolean checkCrypt(String type, int mode,
                                      IvParameterSpec zeroIv, Key key, byte[] data)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        if (type == null || !isValid(TYPES, type)) {
            return false;
        }
        if (!isValid(MODES, mode)) {
            return false;
        }
        if (key == null) {
            return false;
        }
        if (type.contains("RSA") && zeroIv != null) {
            return false;
        }
        if (data == null || data.length == 0) {
            return false;
        }
        return true;
    }

    private static boolean isValid(Object[] ts, Object t) {
        if (ts instanceof String[] && t instanceof String) {
            String[] strs = (String[]) ts;
            String tmp = (String) t;
            for (String str : strs) {
                if (tmp.equals(str)) {
                    return true;
                }
            }
        }
        if (ts instanceof Integer[] && t instanceof Integer) {
            Integer[] ints = (Integer[]) ts;
            Integer intT = (Integer) t;
            for (Integer str : ints) {
                if (intT == str) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean pubCertValid() {
        if (pubCert == null) {
            return false;
        }
        try {
            pubCert.checkValidity(new Date());
        } catch (Exception e) {
            System.err.println("证书失效" + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static String doBase64Encode(byte[] abc) throws UnsupportedEncodingException {
        String abce = null;
        abce = new String(Base64.encode(abc, 0)); // 刘德利修改过
        // Base64.getEncoder().encodeToString(abc);
        return abce;
    }

    public static byte[] doBase64Decode(String abc) throws UnsupportedEncodingException {
        byte[] abce = null;
        abce = Base64.decode(abc,0); // 刘德利修改过
        return abce;
    }


    public static void mainTest() {
        String result = "test123456789";
        try {
            JSONObject jsonObject = new JSONObject(result);
            String data = jsonObject.getString("data");
            String resultCode = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            LogUtil.i(TAG, "resultCode=====>" + resultCode);
            if ("200".equals(resultCode)) {
                HashMap<String, String> acceptMap = RSAUtils.genMap(data);
                String decryptText = defaultDecrypt(acceptMap);
                LogUtil.i(TAG, "解密明文=====>" + decryptText);
                //  progressLoginRequest(decryptText);
            } else {
                // 其他情况直接显示后台内容
                // LoginActivity.this.showDialog(BaseActivity.MODAL_DIALOG, msg);
                LogUtil.d(TAG, "msg:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
