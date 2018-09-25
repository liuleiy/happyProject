package com.happyProject.admin.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.happyProject.admin.model.Onlink;
import com.happyProject.admin.service.impl.OnlinkServiceImpl;
import com.happyProject.admin.utlis.SendTheRequest;

@Component
public class ScheduledTasks {
	@Resource
	private OnlinkServiceImpl onlinkServiceImpl;
	
	//@Scheduled(cron = "0/5 * * * * ? ") // 每5秒执行一次
	@Scheduled(fixedDelay = 1000*60*60*1) // 两个两个小时,*60*2
	public void testCron() {
		if(onlinkServiceImpl!=null) {
			Integer onlineNumber = SendTheRequest.getOnlineNumber(9);
			Date ctime = new Date();
			SimpleDateFormat s = new SimpleDateFormat("dd'日'HH'时'");
			s.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			String time = s.format(ctime);
			Onlink t = new Onlink(null, time, onlineNumber, ctime);
			onlinkServiceImpl.AddAnaUpdate(t);//add(t);
		}else {
			System.out.println("null");
		}
	}
}
