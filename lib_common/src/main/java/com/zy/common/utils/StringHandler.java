package com.zy.common.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Liudeli
 * @Describe：字符串处理类
 */
public class StringHandler {
    
    private static final String TAG = "StringHandler";
	
	/**
	 * 根据分隔符截取字符串
	 * @param str	字符串
	 * @param separator	分隔符
	 * @return	截取的字符串数组
	 */
	public static String[] split(String str, String separator) {
		if (str == null || str.equals("") || separator == null) {
			return null;
		}
		int index;
		ArrayList<String> list = new ArrayList<String>();
		while((index = str.indexOf(separator)) != -1) {
			list.add(str.substring(0, index));
			str = str.substring(index + separator.length());
		}
		list.add(str);
		
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * 使用StringBuffer追加字符串
	 * @param str 字符串数组
	 * @return 完整字符串
	 */
	public static String append(String...str) {
		StringBuffer sb = new StringBuffer();
		int len = str.length;
		for (int i = 0; i < len; i++) {
			if (null != str[i]) {
				sb.append(str[i]);
			}
		}
		return sb.toString();
	}
	
	public static final String MSG_REPLACE_STR = "%s";
	
	public static String replace(String src, String...str) {
		if (str == null) {
			return src;
		}
		int count = countStr(src, MSG_REPLACE_STR);
		if (count != str.length) {
		    ELog.w(TAG, "str len error.");
			return null;
		}
		for (int i = 0; i < str.length; i++) {
			src = src.replaceFirst(MSG_REPLACE_STR, str[i]);
		}
		count = 0;
		return src;
	}
	
	/**
	 * 计算src中包含str的个数
	 * 可以优化 --> indexOf(a, b)
	 * @return
	 */
	public static int countStr(String src, String str) {
		int count = 0;
		if (src.indexOf(str) == -1) {
			return 0;
		} else if (src.indexOf(str) != -1) {
			count += countStr(src.substring(src.indexOf(str) + str.length()), str);
			return count + 1;
		}
		return 0;
	}
	
	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	/**
	 * 将二进制数据转换为文件
	 * @param data
	 * @param fileName
	 */
	public static boolean data2file(byte[] data, String fileName) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			out.write(data);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			ELog.e(TAG, e.toString());
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out = null;
			return false;
		} catch (IOException e) {
//			e.printStackTrace();
			ELog.e(TAG, e.toString());
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out = null;
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					ELog.e(TAG, e.toString());
					return false;
				}
				out = null;
			}
		}
		return true;
	}

}
