/*
 * @(#)OkHttpUtil.java	2017年9月13日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaclub.sword.core.Strings;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttpUtil
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: OkHttpUtil.java 2017年9月13日 14:05:09 Exp $
 */
public class OkHttpUtil {

	static final Logger log = LoggerFactory.getLogger(OkHttpUtil.class);
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	static OkHttpClient okHttpClient;
	static {
		okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS) // 连接超时(单位:秒)
				.writeTimeout(20, TimeUnit.SECONDS) // 写入超时(单位:秒)
				.readTimeout(20, TimeUnit.SECONDS) // 读取超时(单位:秒)
				.pingInterval(20, TimeUnit.SECONDS) // websocket轮训间隔(单位:秒)
				.build();
	}

	public static String doGet(String url, Map<String, String> headerMap) {

		String echo = "";
		Request.Builder builder = new Request.Builder().url(url);
		if (null != headerMap && headerMap.size() > 0) {
			builder.headers(Headers.of(headerMap));
		}
		Request request = builder.build();
		Call call = okHttpClient.newCall(request);
		try {
			Response response = call.execute();
			echo = response.body().string();
		} catch (Exception e) {
			log.error("", e);
		}

		return echo;
	}

	public static String doPost(String url, String json, Map<String, String> headerMap) {
		RequestBody body = RequestBody.create(JSON, json);
		Request.Builder builder = new Request.Builder().url(url);
		if (null != headerMap && headerMap.size() > 0) {
			builder.headers(Headers.of(headerMap));
		}
		Request request = builder.post(body).build();
		String echo = "";
		try {
			Response response = okHttpClient.newCall(request).execute();
			echo = response.body().string();
		} catch (Exception e) {
			log.error("", e);
		}
		
		return echo;
	}
	
	public static String doPost(String url, String json, String bodyContentType) {
		String echo = "";
		try {
			RequestBody body = RequestBody.create(MediaType.parse(bodyContentType), json);
			Request.Builder builder = new Request.Builder().url(url);
			Request request = builder.post(body).build();
			Response response = okHttpClient.newCall(request).execute();
			echo = response.body().string();
		} catch (Exception e) {
			log.error("", e);
		}
		
		return echo;
	}
	
	public static void main(String[] args) {
		String url = Strings.format("http://portal.zjlianhua.com/oto/cgi/token?appid={}&secret={}", "jingxuan", "NLzGA9skQAdYxsns393LjKkq1JvP");
		String echo = doGet(url, null);
		System.out.println(echo);
	}
}
