/*
 * @(#)ZooPlayerController.java	2017年6月9日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.web.controller.ops;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.github.javaclub.sword.config.ConfigFactory;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.web.HttpResult;
import com.shangou.tmq.client.producer.SendResult;
import #{packagePrefix}#.mq.TmqPublisher;
import #{packagePrefix}#.web.controller.BaseController;

/**
 * ZooPlayerController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ZooPlayerController.java 2017年6月9日 15:54:01 Exp $
 */
@Controller
@RequestMapping("/test")
public class ZooPlayerController extends BaseController {
	
	@Resource
	private ConfigFactory kaConfigFactory;
	
	@Value("#{APP_PROP['kamember.msgcenter.appId']}")
	private int msgAppId;
	@Value("#{APP_PROP['kamember.msgcenter.appKey']}")
	private String msgAppKey;
	
	@Autowired
	private TmqPublisher tmqPublisher;

	@RequestMapping(value = "/getConfig")
	@ResponseBody
	public HttpResult getConfig() {
		
		Map<String, String> configAsProps = kaConfigFactory.getConfigAsPropertyMap("test.players");
		Map<String, Integer> map = (Map<String, Integer>) kaConfigFactory.getConfigAsBean("batch.update.limit", Map.class);
		
		Integer i = map.get("meiyijia");
		
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("integer", i);
		resultMap.put("configAsProps", configAsProps);
		resultMap.put("map", map);
		
		return HttpResult.success(resultMap);
	}
	
	@RequestMapping(value = "/sendTmq")
	@ResponseBody
	public HttpResult doSendTmq() {
		String topic = "kamember";
		String subtopic = "test";
		
		Long timestamp = System.currentTimeMillis();
		String msgBody = Strings.createMapJson("id", timestamp)
								.entry("name", Strings.fixed(20))
								.getJsonObject().toJSONString();
		
		SendResult r = tmqPublisher.sendStringMessage(topic, subtopic, msgBody);
		
		Map map = com.github.javaclub.sword.core.Maps.generateMap("send_result", r, "id_key", timestamp);
		
		return HttpResult.success(map);
	}
	
}
