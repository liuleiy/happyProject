package com.happyProject.admin.utlis;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.happyProject.admin.model.GameSend;
import com.happyProject.admin.model.LoginPrize;
import com.happyProject.admin.model.LuckySend;
import com.happyProject.admin.model.Notice;
import com.happyProject.admin.model.PayCurrency;
import com.happyProject.admin.model.ShopSend;
import com.happyProject.admin.model.TaskSend;

public class SendTheRequest {
	private static String url = "http://127.0.0.1/happy/webjson";

	// private static String url = "http://huandou8.top/happy/webjson";

	public static String getJsonData(JSONObject jsonParam, String urls) {
		StringBuffer sb = new StringBuffer();
		try {

			// 创建url资源
			URL url = new URL(urls);
			// 建立http连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置允许输出
			conn.setDoOutput(true);
			// 设置允许输入
			conn.setDoInput(true);
			// 设置不用缓存
			conn.setUseCaches(false);
			// 设置传递方式
			conn.setRequestMethod("POST");
			// 设置维持长连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置文件字符集:
			conn.setRequestProperty("Charset", "UTF-8");
			// 转换为字节数组
			byte[] data = (jsonParam.toString()).getBytes();
			// 设置文件长度
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			// 设置文件类型:
			conn.setRequestProperty("contentType", "application/json");
			// 开始连接请求
			conn.connect();
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// 写入请求的字符串
			out.write((jsonParam.toString()).getBytes());
			out.flush();
			out.close();

			System.out.println(conn.getResponseCode());

			// 请求返回的状态
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				System.out.println("连接成功");
				// 请求返回的数据
				InputStream in1 = conn.getInputStream();
				try {
					String readLine = new String();
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1, "UTF-8"));
					while ((readLine = responseReader.readLine()) != null) {
						sb.append(readLine).append("\n");
					}
					responseReader.close();
					// System.out.println(sb.toString());

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("error++");

			}

		} catch (Exception e) {

		}

		return sb.toString();

	}

	// 获得在线人数
	public static Integer getOnlineNumber(int code) {
		Integer jInt = 0;
		Integer wInt = 0;
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", code);

			// String url = "http://32co.cn/happy/webjson";
			String data = SendTheRequest.getJsonData(jsonParam, url);
			char fir = 0;
			if (!"".equals(data)) {
				fir = data.charAt(0);
			} else {
				System.out.println("获取数据为空!");
			}

			if (fir == '{') {
				JSONObject obj = new JSONObject(data);
				System.out.println("ogj:" + obj);
				if (obj.has("Result")) {
					String s = obj.getString("Result");

					JSONObject result = new JSONObject(s);
					if (result.has("1")) {
						String jq = result.getString("1");
						System.out.println("机器人:" + jq);
						if (!"".equals(jq)) {
							jInt = Integer.parseInt(jq);
						}
					} else if (result.has("2")) {
						String wj = result.getString("2");
						System.out.println("玩家:" + wj);
						if (!"".equals(wj)) {
							wInt = Integer.parseInt(wj);
						}
					}

				} else if (obj.has("ErrMsg")) {
					System.out.println("请求参数出错!");
				}
			} else {

				System.out.println("未取得数据!");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jInt + wInt;
	}

	// 修改商品
	public static void delShop(Integer code, Integer atype, Object key, ShopSend shopSend) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", code);// 请求
			jsonParam.put("Atype", atype);
			// String url = "http://huandou8.top/happy/webjson";

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(key, shopSend);
			// map.put("id2", shopSend);
			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());// net.sf.json.JSONObject.fromObject(map).toString()
			System.out.println("j:" + jsonParam);
			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 修改休息房间
	public static void delGame(Integer code, Integer atype, Object key, GameSend gameSend) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", code);// 请求
			jsonParam.put("Atype", atype);
			// String url = "http://huandou8.top/happy/webjson";

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(key, gameSend);

			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());// net.sf.json.JSONObject.fromObject(map).toString()
			System.out.println("j:" + jsonParam);
			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 修改任务
	public static void delTask(Integer code, Integer atype, Object key, TaskSend taskSend) {

		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", code);// 请求
			jsonParam.put("Atype", atype);
			// String url = "http://huandou8.top/happy/webjson";

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(key, taskSend);

			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());// net.sf.json.JSONObject.fromObject(map).toString()
			System.out.println("j:" + jsonParam);
			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// setAgent("113307");
		// setPartner("113410");
		// String format = new SimpleDateFormat(
		// "yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
		// 任务
		// delTask(10, 1, "2", new TaskSend("", 1, 1,
		// "", 1, 1l, 1l, true, 1,
		// 1, format));
		// 商品
		// delShop(5, 0, "", new ShopSend("", null, null, null, null, null, "",
		// "", 0, format, format));
		// crud(4, 0, "id1", new PayCurrency("104242", null, 10000, null, null,
		// null, null));

		// 公告
		// 创建时间

		/*
		 * Date time = new Date(); SimpleDateFormat s = new
		 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // 注意格式化的表达式 String format2 =
		 * s.format(time); // 过期时间 Calendar cal = Calendar.getInstance();
		 * cal.add(Calendar.YEAR, 1); Date date = cal.getTime(); String format1 = new
		 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'") .format(date);
		 */

		// 1,广播；2,公告
		/*
		 * Notice notice = new Notice("13", "104242", 3, 0, 0, 0, 1, 0, "我给你的消息你要不要续1",
		 * format1, format2);
		 */
		/*
		 * Notice notice = new Notice("21", "104242", 0, 0, 0, 0, 1, 0, "你成功兑换了1300金币",
		 * format1, format2);
		 */
		/*
		 * Notice notice = new Notice("15", "104242", 2, 0, 0, 0, 1, 0, "我给你的公告1",
		 * format1, format2);
		 */
		/*
		 * Notice notice = new Notice("16", "104242", 2, 0, 0, 0, 1, 0, "我给你的公告2",
		 * format1, format2);
		 */
		/*
		 * Notice notice = new Notice("17", "104242", 3, 0, 0, 0, 1, 0, "我给你的公告3",
		 * format1, format2);
		 */

		/*
		 * Notice notice = new Notice("33", "105764", 3, 0, 0, 0, 1, 0, "d我给你的公告3.2",
		 * format1, format2); SendTheRequest.add(2, 0, "", notice);
		 */

		// 发送金币
		// SendTheRequest.crud(4, 0, "id1", new PayCurrency("104242", null,
		// 2000, null, null, null, null));

		// 设置代理
		// SendTheRequest.setAgent("");
		// 设置成为合伙人
		// SendTheRequest.setPartner();
		//
		/*
		 * Date ctime = new Date(); SimpleDateFormat s = new SimpleDateFormat(
		 * "yyyy-MM-dd'T'HH:mm:ss'Z'"); // 注意格式化的表达式 String format2 = s.format(ctime);
		 * LoginPrize loginPrize = new LoginPrize("", 0, 0, 100, 50, format2);
		 * setLoginPrize("1",loginPrize);
		 */
	}

	// 修改，lucky
	public static void crudLucky(Integer code, Integer atype, Object key, LuckySend luckySend) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", code);// 请求
			jsonParam.put("Atype", atype);
			// String url = "http://huandou8.top/happy/webjson";

			/*
			 * Map<Object, Object> map = new HashMap<Object, Object>(); map.put(key,
			 * payCurrency);
			 */

			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(luckySend).toString());// net.sf.json.JSONObject.fromObject(map).toString()
			System.out.println("j:" + jsonParam);
			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void setLoginPrize(String key, LoginPrize loginPrize) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", 11);// 请求
			jsonParam.put("Atype", 0);

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(key, loginPrize);

			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());
			System.out.println("data" + jsonParam);
			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 设置区域比例
	public static void setRate(String userid, Integer rate) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", 12);// 请求
			jsonParam.put("Atype", 0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userid);
			map.put("rate", rate);
			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());
			// System.out.println(jsonParam);
			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setPartner() {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", 13);// 请求
			jsonParam.put("Atype", 0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", "104242");
			map.put("state", 1);
			map.put("level", 1);
			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());

			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setAgent(String id, String agent) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", 3);// 请求
			jsonParam.put("Atype", 0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", id);
			map.put("agent", agent);
			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());

			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setPartner(String id) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", 13);// 请求
			jsonParam.put("Atype", 0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", id);
			map.put("state", 1);
			map.put("level", 1);
			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());

			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 修改，同步变动货币数据(充值或后台操作等)
	public static void crud(Integer code, Integer atype, Object key, PayCurrency payCurrency) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", code);// 请求
			jsonParam.put("Atype", atype);
			// String url = "http://huandou8.top/happy/webjson";

			/*
			 * Map<Object, Object> map = new HashMap<Object, Object>(); map.put(key,
			 * payCurrency);
			 */

			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(payCurrency).toString());// net.sf.json.JSONObject.fromObject(map).toString()
			System.out.println("j:" + jsonParam);
			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 公告
	public static void add(Integer code, Integer atype, Object key, Notice notice) {
		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("Code", code);// 请求
			jsonParam.put("Atype", atype);// 0添加修改，1删除
			// String url = "http://huandou8.top/happy/webjson";

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(key, notice);

			jsonParam.put("Data", net.sf.json.JSONObject.fromObject(map).toString());// net.sf.json.JSONObject.fromObject(map).toString()
			System.out.println("j:" + jsonParam);
			String data = SendTheRequest.getJsonData(jsonParam, url);
			System.out.println("data2:" + data);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
