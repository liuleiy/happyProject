package com.happyProject.admin.utlis;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 返回md5的32位大写字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String getMd5String(byte[] data) {
		byte[] digest = getMd5ByteArray(data);
		// 把密文转换成十六进制的字符串形式
		int j = digest.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = digest[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}

	/**
	 * 返回md5的32位大写字符串
	 * 
	 * @param data
	 * @return
	 */
	public static String getMd5String(String str) {
		return getMd5String(str.getBytes());
	}

	public static byte[] getMd5ByteArray(byte[] data) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return md5.digest(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
//		System.out.println(getMd5String("cccccc").toLowerCase());
		System.out.println(getMd5String("123456"));
	}
}
