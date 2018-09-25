package com.happyProject.admin.utlis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TrunTime {

	public static Date setTime(Date time , String format){
		if(time==null){
			return null;
		}
		try {
			SimpleDateFormat bjSdf = new SimpleDateFormat(format);     // 北京
			bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			String f = bjSdf.format(time);
			Date date = new SimpleDateFormat(format).parse(f);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
