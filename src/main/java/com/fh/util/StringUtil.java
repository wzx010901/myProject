package com.fh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * 
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr) {
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0) {
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

			i++;
		}
		return returnStr;
	}

	/**
	 * 获取字符串编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/**
	 * 替换下划线并让下一个字母大写
	 * 
	 * @param srcStr
	 * @param org
	 * @param ob
	 * @return
	 */
	public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
		String newString = "";
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString = newString + srcStr.substring(0, first) + ob;
				srcStr = srcStr.substring(first + org.length(), srcStr.length());
				srcStr = firstCharacterToUpper(srcStr);
			}
		}
		newString = newString + srcStr;
		return newString;
	}

	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	/**
	 * 首字母小写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToLower(String srcStr) {
		return srcStr.substring(0, 1).toLowerCase() + srcStr.substring(1);
	}

	/**
	 * 产生序列号
	 * 
	 * @return
	 */
	public synchronized static String getSeqString() {

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String seq = DateUtil.dateToString(new Date(), "yyMMddHHmmssSSS");
		String idStr = seq.toString();
		return idStr;
	}

	/**
	 * 两个数组合并
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String[] concat(String[] a, String[] b) {
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/**
	 * 随机生成六位数验证码
	 * 
	 * @return
	 */
	public static int getRandomNum() {
		Random r = new Random();
		return r.nextInt(900000) + 100000;// (Math.random()*(999999-100000)+100000)
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmptyStr(String str) {
		if (str != null && str.length() > 0) {
			return true;
		} else {
			return false;
		}
		// return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 字符串是否为空（会排除字符串左右空格）
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmptyStr(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
		// return s==null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 判断字符串对象是否为null或是否为空（会排除字符串左右空格）
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj.toString().trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串是否为空（不排除字符串左右空格）
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isEmpty(String source) {
		if (source == null || source.length() == 0)
			return true;
		return false;
	}

	/**
	 * 是否是数值，不带小数和负数
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否为大于零的数字
	 * 
	 * @param str_intNumber
	 * @return
	 */
	public final static boolean IsIntNumber(String str_intNumber) {

		// 使用正则表达式判断是否匹配[1-9]+\\d*
		Pattern pattern = Pattern.compile("[1-9]+\\d*");
		Matcher isNum = pattern.matcher(str_intNumber);
		if (!isNum.matches()) {
			return false;
		}
		return true;
		// IsMatch(str_intNumber, @");
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmptyStr(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码是否符合规则
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean isTelphone(String str) {
		String check = "^(13|14|15|17|18)\\d{9}$";
		// String check = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		return isValidateByRegx(str, check);
	}

	/**
	 * 自定义正则判断字符串
	 * 
	 * @param str
	 * @param regx
	 * @return
	 */
	public final static boolean isValidateByRegx(String str, String regx) {
		Pattern pattern = Pattern.compile(regx);
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	/**
	 * 验证身份证是否符合规则
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean isCard(String str) {
		String check = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
		return isValidateByRegx(str, check);
	}

	public static boolean isZipcode(String zipcode) {
		// TODO Auto-generated method stub
		String check = "^[1-9]\\d{5}$";
		return isValidateByRegx(zipcode, check);
	}

	/**
	 * 是否是数值，带小数，带负数
	 * 
	 * @param str
	 * @return
	 */
	public final static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^-{0,1}\\d+(\\.\\d+)?$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是数值，带小数，不带负数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumberNoNegative(String money) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("^{0,1}\\d+(\\.\\d+)?$");
		Matcher isNum = pattern.matcher(money);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 是否有效日期
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static boolean isValidDate(String str, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);
		try {
			df.parse(str);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
