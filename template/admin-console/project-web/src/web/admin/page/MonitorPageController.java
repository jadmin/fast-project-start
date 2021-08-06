/*
 * @(#)PageViewController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.admin.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ${packagePrefix}.common.model.RedisInfo;
import ${packagePrefix}.web.admin.AdminControllerBase;

import springfox.documentation.annotations.ApiIgnore;

/**
 * PageViewController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: PageViewController.java ${currentTime} Exp $
 */
@Controller
@RequestMapping("/console")
@ApiIgnore
public class MonitorPageController extends AdminControllerBase {
	

    /**
     * 数据监控页
     */
    @GetMapping("/monitor/druid")
    public String druidPage(HttpServletRequest request, Model model) {
        return "/monitor/druid";
    }
    
    /**
     * redis监控页
     */
    @GetMapping("/monitor/redis")
    public String redisPage(HttpServletRequest request, Model model) throws Exception {
    	List<RedisInfo> itemList = redisCacheManager.getRedisInfo();
    	model.addAttribute("itemList", itemList);
        return "/monitor/redis";
    }


}
