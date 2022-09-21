package com.zy.common.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Liudeli
 * @Describe：字符串处理类
 */
public final class StringUtil {

  private static final String ALLCHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String CHINA_PHONE_REG =
      "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
  /**
   * 编译后的正则表达式缓存
   */
  private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();
  private static final char CN_CHAR_START = '\u4e00';
  private static final char CN_CHAR_END = '\u9fa5';
  private static final int SECOND = 2;
  private static final int FF = 0xff;

  private StringUtil() {
    throw new UnsupportedOperationException("StringUtils cannot be instantiated");
  }

  /**
   * 字符串是否为空
   *
   * @param str 需要判断的字符串
   * @return true :空
   */
  public static boolean isEmpty(String str) {
    return null == str || TextUtils.isEmpty(str);
  }

  /**
   * 字符串是否非空
   *
   * @param str 需要判断的字符串
   * @return true:非空
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  /**
   * 字符串是否相同
   *
   * @param str 需要比较的字符串
   * @param equalStr 被比较的字符串
   * @return true:相等
   */
  public static boolean isEqual(String str, String equalStr) {
    if (StringUtil.isEmpty(str)) {
      return false;
    }
    return str.equals(equalStr);
  }

  /**
   * 字符串从左向右插入字符
   *
   * @param index 要插入的位置
   * @param oldString 旧字符串
   * @param insertString 要插入的字符串
   * @return 最终生成的字符串
   */
  public static String insertChar(int index, String oldString, String insertString) {
    StringBuffer buffer = new StringBuffer(oldString);
    for (int i = index; i < buffer.length(); i = i + index + 1) {
      buffer.insert(i, insertString);
    }
    return buffer.toString();
  }

  /**
   * 翻转字符串
   *
   * @param str 需要翻转的字符串
   * @return 翻转后的字符串
   */
  public static String reverseString(String str) {
    return new StringBuffer(str).reverse().toString();
  }

  /**
   * 返回一个定长的随机字符串(只包含大小写字母、数字)
   *
   * @param length 随机字符串长度
   * @return 随机字符串
   */
  public static String generateString(int length) {
    StringBuffer sb = new StringBuffer();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
    }
    return sb.toString();
  }

  /**
   * 判断是否是手机号
   *
   * @param phone 手机号
   * @return true 是手机号
   */
  public static boolean isChinaPhone(String phone) {
    if (isEmpty(phone)) {
      return false;
    }
    Pattern pattern = compileRegex(CHINA_PHONE_REG);
    Matcher matcher = pattern.matcher(phone);
    return matcher.matches();
  }

  /**
   * 检测String是否全是中文
   *
   * @param name 需要操作的字符串
   * @return true 是全中文
   */

  public static boolean checkNameChese(String name) {
    boolean res = true;
    char[] cTemp = name.toCharArray();
    for (int i = 0; i < name.length(); i++) {
      if (!isChinese(cTemp[i])) {
        res = false;
        break;
      }
    }
    return res;
  }

  /**
   * 判定输入汉字
   *
   * @param c 需要判断的字符
   * @return true 是汉字
   */

  public static boolean isChinese(char c) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;

  }


  /**
   * 编译一个正则表达式，并且进行缓存,如果缓存已存在则使用缓存
   *
   * @param regex 表达式
   * @return 编译后的Pattern
   */
  public static Pattern compileRegex(String regex) {
    Pattern pattern = PATTERN_CACHE.get(regex);
    if (pattern == null) {
      pattern = Pattern.compile(regex);
      PATTERN_CACHE.put(regex, pattern);
    }
    return pattern;
  }

  /**
   * 将字符串的第一位转为小写
   *
   * @param str 需要转换的字符串
   * @return 转换后的字符串
   */
  public static String toLowerCaseFirstOne(String str) {
    if (Character.isLowerCase(str.charAt(0))) {
      return str;
    } else {
      char[] chars = str.toCharArray();
      chars[0] = Character.toLowerCase(chars[0]);
      return new String(chars);
    }
  }

  /**
   * 将字符串的第一位转为大写
   *
   * @param str 需要转换的字符串
   * @return 转换后的字符串
   */
  public static String toUpperCaseFirstOne(String str) {
    if (Character.isUpperCase(str.charAt(0))) {
      return str;
    } else {
      char[] chars = str.toCharArray();
      chars[0] = Character.toUpperCase(chars[0]);
      return new String(chars);
    }
  }

  /**
   * 下划线命名转为驼峰命名
   *
   * @param str 下划线命名格式
   * @return 驼峰命名格式
   */
  public static String underScoreCase2CamelCase(String str) {
    if (!str.contains("_")) {
      return str;
    }
    StringBuilder sb = new StringBuilder();
    char[] chars = str.toCharArray();
    boolean hitUnderScore = false;
    sb.append(chars[0]);
    for (int i = 1; i < chars.length; i++) {
      char c = chars[i];
      if (c == '_') {
        hitUnderScore = true;
      } else {
        if (hitUnderScore) {
          sb.append(Character.toUpperCase(c));
          hitUnderScore = false;
        } else {
          sb.append(c);
        }
      }
    }
    return sb.toString();
  }

  /**
   * 驼峰命名法转为下划线命名
   *
   * @param str 驼峰命名格式
   * @return 下划线命名格式
   */
  public static String camelCase2UnderScoreCase(String str) {
    StringBuilder sb = new StringBuilder();
    char[] chars = str.toCharArray();
    for (char c : chars) {
      if (Character.isUpperCase(c)) {
        sb.append("_").append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 将异常栈信息转为字符串
   *
   * @param e 字符串
   * @return 异常栈
   */
  public static String throwable2String(Throwable e) {
    StringWriter writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));
    return writer.toString();
  }

  /**
   * 字符串连接，将参数列表拼接为一个字符串
   *
   * @param more 追加
   * @return 返回拼接后的字符串
   */
  public static String concat(Object... more) {
    return concatSpiltWith("", more);
  }

  /**
   * 字符串连接，将参数列表拼接为一个字符串
   *
   * @param split 拼接的字符
   * @param more 拼接的参数个数
   * @return 回拼接后的字符串
   */
  @NonNull
  public static String concatSpiltWith(String split, Object... more) {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < more.length; i++) {
      if (i != 0) {
        buf.append(split);
      }
      buf.append(more[i]);
    }
    return buf.toString();
  }

  /**
   * 将字符串转移为ASCII码
   *
   * @param str 字符串
   * @return 字符串ASCII码
   */
  public static String toASCII(String str) {
    StringBuffer strBuf = new StringBuffer();
    byte[] bGBK = str.getBytes();
    for (int i = 0; i < bGBK.length; i++) {
      strBuf.append(Integer.toHexString(bGBK[i] & FF));
    }
    return strBuf.toString();
  }

  /**
   * 将字符串转移为Unicode码
   *
   * @param str 字符串
   * @return 返回Unicode 的字符串
   */
  public static String toUnicode(String str) {
    StringBuffer strBuf = new StringBuffer();
    char[] chars = str.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      strBuf.append("\\u").append(Integer.toHexString(chars[i]));
    }
    return strBuf.toString();
  }

  /**
   * 将字符串转移为Unicode码
   *
   * @param chars 字符数组
   * @return 转移为Unicode码 字符串
   */
  public static String toUnicodeString(char[] chars) {
    StringBuffer strBuf = new StringBuffer();
    for (int i = 0; i < chars.length; i++) {
      strBuf.append("\\u").append(Integer.toHexString(chars[i]));
    }
    return strBuf.toString();
  }


  /**
   * 是否包含中文字符
   *
   * @param str 要判断的字符串
   * @return 是否包含中文字符
   */
  public static boolean containsChineseChar(String str) {
    char[] chars = str.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] >= CN_CHAR_START && chars[i] <= CN_CHAR_END) {
        return true;
      }
    }
    return false;
  }

  /**
   * 对象是否为无效值
   *
   * @param obj 要判断的对象
   * @return 是否为有效值（不为null 和 "" 字符串）
   */
  public static boolean isNullOrEmpty(Object obj) {
    return obj == null || "".equals(obj.toString());
  }

  /**
   * 参数是否是有效数字 （整数或者小数）
   *
   * @param obj 参数（对象将被调用string()转为字符串类型）
   * @return 是否是数字
   */
  public static boolean isNumber(Object obj) {
    if (obj instanceof Number) {
      return true;
    }
    return isInt(obj) || isDouble(obj);
  }

  /**
   * 匹配到第一个字符串
   *
   * @param patternStr 正则表达式
   * @param text 字符串
   * @return 返回字符串
   */
  public static String matcherFirst(String patternStr, String text) {
    Pattern pattern = compileRegex(patternStr);
    Matcher matcher = pattern.matcher(text);
    String group = null;
    if (matcher.find()) {
      group = matcher.group();
    }
    return group;
  }

  /**
   * 参数是否是有效整数
   *
   * @param obj 参数（对象将被调用string()转为字符串类型）
   * @return 是否是整数
   */
  public static boolean isInt(Object obj) {
    if (isNullOrEmpty(obj)) {
      return false;
    }
    if (obj instanceof Integer) {
      return true;
    }
    return obj.toString().matches("[-+]?\\d+");
  }

  /**
   * 字符串参数是否是double
   *
   * @param obj 参数（对象将被调用string()转为字符串类型）
   * @return 是否是double
   */
  public static boolean isDouble(Object obj) {

    if (isNullOrEmpty(obj)) {
      return false;
    }

    if (obj instanceof Double || obj instanceof Float) {
      return true;
    }
    return compileRegex("[-+]?\\d+\\.\\d+").matcher(obj.toString()).matches();
  }

  /**
   * 判断一个对象是否为boolean类型,包括字符串中的true和false
   *
   * @param obj 要判断的对象
   * @return 是否是一个boolean类型
   */
  public static boolean isBoolean(Object obj) {
    if (obj instanceof Boolean) {
      return true;
    }
    String strVal = String.valueOf(obj);
    return "true".equalsIgnoreCase(strVal) || "false".equalsIgnoreCase(strVal);
  }

  /**
   * 对象是否为true
   *
   * @param obj 判断的对象
   * @return true 是
   */
  public static boolean isTrue(Object obj) {
    return "true".equals(String.valueOf(obj));
  }

  /**
   * 判断一个数组里是否包含指定对象
   *
   * @param arr 对象数组
   * @param obj 要判断的对象
   * @return 是否包含
   */
  public static boolean contains(Object obj, Object... arr) {
    if (arr == null || obj == null || arr.length == 0) {
      return false;
    }
    return Arrays.asList(arr).containsAll(Arrays.asList(obj));
  }

  /**
   * 将对象转为int值,如果对象无法进行转换,则使用默认值
   *
   * @param object 要转换的对象
   * @param defaultValue 默认值
   * @return 转换后的值
   */
  public static int toInt(Object object, int defaultValue) {
    int returnValue = defaultValue;
    if (object instanceof Number) {
      returnValue = ((Number) object).intValue();
    }
    if (isInt(object)) {
      returnValue = Integer.parseInt(object.toString());
    }
    if (isDouble(object)) {
      returnValue = (int) Double.parseDouble(object.toString());
    }
    return returnValue;
  }

  /**
   * 将对象转为int值,如果对象不能转为,将返回0
   *
   * @param object 要转换的对象
   * @return 转换后的值
   */
  public static int toInt(Object object) {
    return toInt(object, 0);
  }

  /**
   * 将对象转为long类型,如果对象无法转换,将返回默认值
   *
   * @param object 要转换的对象
   * @param defaultValue 默认值
   * @return 转换后的值
   */
  public static long toLong(Object object, long defaultValue) {
    long returnValue = defaultValue;
    if (object instanceof Number) {
      returnValue = ((Number) object).longValue();
    }
    if (isInt(object)) {
      returnValue = Long.parseLong(object.toString());
    }
    if (isDouble(object)) {
      returnValue = (long) Double.parseDouble(object.toString());
    }
    return returnValue;
  }

  /**
   * 将对象转为 long值,如果无法转换,则转为0
   *
   * @param object 要转换的对象
   * @return 转换后的值
   */
  public static long toLong(Object object) {
    return toLong(object, 0);
  }

  /**
   * 将对象转为Double,如果对象无法转换,将使用默认值
   *
   * @param object 要转换的对象
   * @param defaultValue 默认值
   * @return 转换后的值
   */
  public static double toDouble(Object object, double defaultValue) {
    double returnValue = defaultValue;
    if (object instanceof Number) {
      returnValue = ((Number) object).doubleValue();
    }
    if (isNumber(object)) {
      returnValue = Double.parseDouble(object.toString());
    }
    if (null == object) {
      returnValue = defaultValue;
    }
    return returnValue;
  }

  /**
   * 将对象转为Double,如果对象无法转换,将使用默认值0
   *
   * @param object 要转换的对象
   * @return 转换后的值
   */
  public static double toDouble(Object object) {
    return toDouble(object, 0);
  }


  /**
   * 分隔字符串,根据正则表达式分隔字符串,只分隔首个,剩下的的不进行分隔,
   * 如: 1,2,3,4 将分隔为 ['1','2,3,4']
   *
   * @param str 要分隔的字符串
   * @param regex 分隔表达式
   * @return 分隔后的数组
   */
  public static String[] splitFirst(String str, String regex) {
    return str.split(regex, SECOND);
  }

  /**
   * 将对象转为字符串,如果对象为null,则返回null,而不是"null"
   *
   * @param object 要转换的对象
   * @return 转换后的对象
   */
  public static String toString(Object object) {
    return toString(object, null);
  }

  /**
   * 将对象转为字符串,如果对象为null,则使用默认值
   *
   * @param object 要转换的对象
   * @param defaultValue 默认值
   * @return 转换后的字符串
   */
  public static String toString(Object object, String defaultValue) {
    if (object == null) {
      return defaultValue;
    }
    return String.valueOf(object);
  }

  /**
   * 将对象转为String后进行分割，如果为对象为空或者空字符,则返回null
   *
   * @param object 要分隔的对象
   * @param regex 分隔规则
   * @return 分隔后的对象
   */
  public static String[] toStringAndSplit(Object object, String regex) {
    if (isNullOrEmpty(object)) {
      return null;
    }
    return String.valueOf(object).split(regex);
  }

  private static final float MESSY_PERCENT = 0.4f;

  /**
   * 是否为乱码
   *
   * @param strName 需要判断的字符串
   * @return true 是乱码
   */
  public static boolean isMessyCode(String strName) {
    Pattern p = compileRegex("\\s*|\t*|\r*|\n*");
    Matcher m = p.matcher(strName);
    String after = m.replaceAll("");
    String temp = after.replaceAll("\\p{P}", "");
    char[] ch = temp.trim().toCharArray();
    float chLength = 0;
    float count = 0;
    for (char c : ch) {
      if (!Character.isLetterOrDigit(c)) {
        if (!isChinese(c)) {
          count = count + 1;
        }
        chLength++;
      }
    }
    float result = count / chLength;
    return result >= MESSY_PERCENT;
  }

  // ----------------------
}
