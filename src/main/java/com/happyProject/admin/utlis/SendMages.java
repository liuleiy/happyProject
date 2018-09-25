package com.happyProject.admin.utlis;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.chuanglan.sms.request.SmsBalanceRequest;
import com.chuanglan.sms.request.SmsSendRequest;
import com.chuanglan.sms.response.SmsBalanceResponse;
import com.chuanglan.sms.response.SmsSendResponse;
import com.chuanglan.sms.util.ChuangLanSmsUtil;

public class SendMages {
	public static final String charset = "utf-8";


	// 用户平台API账号(非登录账号,示例:N1234567)

	public static String account = "CN6274467";

	// 用户平台API密码(非登录密码)

	public static String pswd = "w3PR9WJGf6ce4f";

	public static SmsBalanceResponse getMoney(String url) {
		SmsBalanceRequest smsBalanceRequest = new SmsBalanceRequest(account,
				pswd);

		String requestJson = JSON.toJSONString(smsBalanceRequest);

		System.out.println("before request string is: " + requestJson);

		String response = ChuangLanSmsUtil.sendSmsByPost(url,
				requestJson);

		System.out.println("response after request result is : " + response);

		SmsBalanceResponse smsVarableResponse = JSON.parseObject(response,
				SmsBalanceResponse.class);

		System.out.println("response  toString is : " + smsVarableResponse);
		return smsVarableResponse;
	}

	public static String sendMessage(String url, String msg, String phone,
			String report) {
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd,
				msg, phone, report);

		String requestJson = JSON.toJSONString(smsSingleRequest);

		/*
		 * System.out.println("before request string is(之前请求字符串): "
		 * 
		 * + requestJson);
		 */

		String response = ChuangLanSmsUtil.sendSmsByPost(url, requestJson);

		System.out.println("response after request result is(请求后的响应结果为) :"

		+ response);

		SmsSendResponse smsSingleResponse = JSON.parseObject(response,
				SmsSendResponse.class);
		JSONObject fromObject = JSONObject.fromObject(smsSingleResponse);
		String string = fromObject.getString("code");
		if (string.equals("0")) {
			return "提交成功";
		} else if (string.equals("101")) {
			return "无此用户";
		} else if (string.equals("102")) {
			return "密码错误";
		} else if (string.equals("103")) {
			return "提交过快（提交速度超过流速限制）";
		} else if (string.equals("104")) {
			return "系统忙（因平台侧原因，暂时无法处理提交的短信）";
		} else if (string.equals("105")) {
			return "敏感短信（短信内容包含敏感词）";
		} else if (string.equals("106")) {
			return "消息长度错（>536或<=0）";
		} else if (string.equals("107")) {
			return "包含错误的手机号码";
		} else if (string.equals("108")) {
			return "手机号码个数错（群发>1000或<=0）";
		} else if (string.equals("109")) {
			return "无发送额度（该用户可用短信数已使用完）";
		} else if (string.equals("110")) {
			return "不在发送时间内";
		} else if (string.equals("113")) {
			return "扩展码格式错（非数字或者长度不对）";
		} else if (string.equals("114")) {
			return "可用参数组个数错误（小于最小设定值或者大于1000）;变量参数组大于20个";
		} else if (string.equals("116")) {
			return "签名不合法或未带签名（用户必须带签名的前提下）";
		} else if (string.equals("117")) {
			return "IP地址认证错,请求调用的IP地址不是系统登记的IP地址";
		} else if (string.equals("118")) {
			return "用户没有相应的发送权限（账号被禁止发送）";
		} else if (string.equals("119")) {
			return "用户已过期";
		} else if (string.equals("120")) {
			return "违反防盗用策略(日发送限制)";
		} else if (string.equals("123")) {
			return "发送类型错误";
		} else if (string.equals("124")) {
			return "白模板匹配错误";
		} else if (string.equals("125")) {
			return "匹配驳回模板，提交失败";
		} else if (string.equals("127")) {
			return "定时发送时间格式错误";
		} else if (string.equals("128")) {
			return "内容编码失败";
		} else if (string.equals("129")) {
			return "JSON格式错误";
		} else if (string.equals("130")) {
			return "请求参数错误（缺少必填参数）";
		}
		return "未知错误";

		/*
		 * System.out.println("response  toString is(响应toString) :"
		 * 
		 * + smsSingleResponse);
		 */
	}
	/*
	 * public static void main(String[] args) { //
	 * 请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
	 * 
	 * String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json"
	 * ;
	 * 
	 * // 短信内容
	 * 
	 * String msg = "【253云通讯】你好,你的验证码是123456";
	 * 
	 * // 手机号码
	 * 
	 * String phone = "17707270361";
	 * 
	 * // 状态报告
	 * 
	 * String report = "true";
	 * 
	 * SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg,
	 * phone, report);
	 * 
	 * String requestJson = JSON.toJSONString(smsSingleRequest);
	 * 
	 * System.out.println("before request string is(之前请求字符串): "
	 * 
	 * + requestJson);
	 * 
	 * String response = ChuangLanSmsUtil.sendSmsByPost(
	 * smsSingleRequestServerUrl, requestJson);
	 * 
	 * System.out.println("response after request result is(请求后的响应结果为) :"
	 * 
	 * + response);
	 * 
	 * SmsSendResponse smsSingleResponse = JSON.parseObject(response,
	 * SmsSendResponse.class);
	 * 
	 * System.out.println("response  toString is(响应toString) :"
	 * 
	 * + smsSingleResponse);
	 * 
	 * }
	 */
}
