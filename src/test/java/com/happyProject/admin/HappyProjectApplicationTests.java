package com.happyProject.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.happyProject.admin.utlis.Md5Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HappyProjectApplicationTests {

	@Test
	public void contextLoads() {
		String md5String = Md5Util.getMd5String("qwm12.as");
		System.out.println(md5String);
	}

}
