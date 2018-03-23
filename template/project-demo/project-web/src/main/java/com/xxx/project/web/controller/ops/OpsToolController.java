/*
 * @(#)OpsToolController.java	2017年6月9日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.web.controller.ops;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.github.javaclub.sword.web.HttpResult;
import #{packagePrefix}#.web.controller.BaseController;

/**
 * OpsToolController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: OpsToolController.java 2017年6月9日 15:54:01 Exp $
 */
@Controller
@RequestMapping("/ops/tool")
public class OpsToolController extends BaseController {
	
	static final Logger log = LoggerFactory.getLogger(OpsToolController.class);
	

	@RequestMapping(value = "/getSomeConfig")
	@ResponseBody
	public HttpResult getSomeConfig() {
		
		Map<String, String> configAsProps = kaConfigFactory.getConfigAsPropertyMap("test.players");
		Map<String, Integer> map = (Map<String, Integer>) kaConfigFactory.getConfigAsBean("batch.update.limit", Map.class);
		
		Integer i = map.get("meiyijia");
		
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("integer", i);
		resultMap.put("configAsProps", configAsProps);
		resultMap.put("map", map);
		
		return HttpResult.success(resultMap);
	}
	
	
}
