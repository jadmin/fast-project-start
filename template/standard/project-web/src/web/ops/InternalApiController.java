/*
 * @(#)InternalApiController.java	${date}
 *
 * Copyright (c) ${year} - 2021. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.ops;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.algorithm.crypt.MD5;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.HttpController;
import com.google.common.collect.Lists;
import ${packagePrefix}.manager.impl.AppIdKeyManager;

import lombok.extern.slf4j.Slf4j;

/**
 * InternalApiController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: InternalApiController.java ${currentTime} Exp $
 */
@Slf4j
@RestController
@RequestMapping("/ops")
public class InternalApiController extends HttpController {
	
	@Autowired
	private AppIdKeyManager appIdKeyManager;

	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public String sendSms(
        @RequestParam(value = "appId", required = false) Integer appId,
        @RequestParam(value = "mobiles", required = false) String mobiles,
        @RequestParam(value = "content", required = false) String content,
        @RequestParam(value = "sign", required = false) String sign,  //数字签名
        @RequestBody String postBody) {
        try {
            String str = this.sortParam(postBody);
            String appKey = appIdKeyManager.getAppKey(appId);
            log.info("str={}, appKey={}", str, appKey);
            String checkSign = MD5.getMd5(str + appKey);
            if (Strings.equals(checkSign, sign)) {
            	List<String> mobileList = Lists.newArrayList(Strings.splitAndTrim(mobiles, ","));
                // TODO 调用发短信服务
                return String.valueOf(true);
            } else {
                log.error("sendSms invalid sign=" + sign);
            }
        } catch (Exception e) {
            log.error("sendSms", e);
        }
        return "false";
    }

    String sortParam(String postBody) {
        Map<String, String> params = WebUtil.splitUrlQuery(postBody);
        List<String> list = Lists.newArrayList();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if ("sign".equals(param.getKey())) {
                continue;
            }
            list.add(param.getKey() + "=" + param.getValue());
        }
        return Strings.join('&', list.iterator());
    }
}
