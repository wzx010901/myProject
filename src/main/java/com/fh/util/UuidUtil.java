package com.fh.util;

public class UuidUtil {

	public static String get32UUID() {
//		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		String uuid = StringUtil.getSeqString();
		return uuid;
	}
	public static void main(String[] args) {
		System.out.println(get32UUID());
	}
}

