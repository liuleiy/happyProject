package com.happyProject.admin.utlis;

public class DataUtils {
	private static Integer onlinkFlag = 1;
	public static Integer getOnlinkFlag() {
		return onlinkFlag;
	}
	public static void setOnlinkFlag(Integer onlinkFlag) {
		DataUtils.onlinkFlag = onlinkFlag;
	}
}
