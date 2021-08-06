package ${packagePrefix}.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.javaclub.sword.core.Strings;

public class Utils {
	
	private static String localIp = null;

	// 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static String getRandomString(int length) {
		StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

	/**
	 * 取当前机器IP
	 */
	public static String getLocalIp() throws Exception {
		if (localIp != null) {
			return localIp;
		}
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					// netip = ip.getHostAddress();
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localIp = ip.getHostAddress();
					return localIp;
				}
			}
		}
		return null;
	}
	
	public static boolean hasNumeric(String content) {
		if(isBlank(content)) {
			return false;
		}
		//正则表达式，用于匹配非数字串，+号用于匹配出多个非数字串
		String regEx="[^0-9]+"; 
		Pattern pattern = Pattern.compile(regEx);
		//用定义好的正则表达式拆分字符串，把字符串中的数字留出来
		String[] cs = pattern.split(content);
		if(null == cs || 0 >= cs.length) {
			return false;
		}
		for (String s : cs) {
			if(isNotBlank(s)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean[] newBooleanArray(int len, boolean initVal) {
		boolean[] array = new boolean[len];
		for (int i = 0; i < array.length; i++) {
			array[i] = initVal;
		}
		return array;
	}
	
	public static boolean isOk(boolean[] array, boolean targetVal) {
		for (int i = 0; i < array.length; i++) {
			if(array[i] != targetVal) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
	
	public static int strLength(String str) { 
		if(null == str || 0 >= str.length()) {
			return 0;
		}
		return str.length();
	}
	
	public static boolean isBlank(String str) {
		int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
	}
	
	public static String join(String sep, String ... strArr) {
		if(null == strArr || 0 >= strArr.length) {
			return "";
		}
		StringBuffer sbf = new StringBuffer();
		for (String str : strArr) {
			if(isBlank(str)) {
				continue;
			}
			sbf.append(str.trim()).append(sep);
		}
		sbf.trimToSize();
		return sbf.toString();
	}
	
	public static boolean matchUrl(String urlPattern, String url) {
		if (isBlank(urlPattern) || isBlank(url)) {
			return false;
		}
		if (Objects.equals(urlPattern, url)) {
			return true;
		}
		if (urlPattern.contains("*")) {
			Pattern pattern = Pattern.compile(urlPattern);
			Matcher matcher = pattern.matcher(url);
			if (matcher.find()) {
                return true;
            }
		}
		return false;
	}
	
	public static String lastChar(String str) {
		if(null == str || 0 >= str.length()) {
			return "";
		}
		int index = str.length();
		return String.valueOf(str.charAt(index - 1));
	}
	
	public static String escapeHTML(String text) {
		return Strings.escapeHTML(text);
	}
	
	/**
	 * 5S/20S/3H/6M/10D => 5秒/20秒/3小时/6分钟/10天
	 */
	public static boolean isPeriodFormat(String format) {
		if(isBlank(format)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\d+[S|M|H|D]$");
		return pattern.matcher(format.toUpperCase()).matches();
	}
	
	public static String datetimeString(Date date) {
		 return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	public static String getSystemProperty(String property) {
		try {
			return System.getProperty(property);
		} catch (SecurityException ex) {
			// we are not allowed to look at this property
			System.err.println("Caught a SecurityException reading the system property '"
							+ property
							+ "'; the SystemUtils property value will default to null.");
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getLocalIp());
	}
}
