/*
 * @(#)PageViewController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.admin.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.github.javaclub.sword.core.BizObjects;
import ${packagePrefix}.common.enums.ResourceType;
import ${packagePrefix}.dataobject.MenuModuleDO;
import ${packagePrefix}.dataobject.ResourceDO;
import ${packagePrefix}.query.MenuModuleQuery;
import ${packagePrefix}.query.ResourceQuery;
import ${packagePrefix}.service.utils.BizUtils;
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
public class PageViewController extends AdminControllerBase {
	
	/**
     * 默认首页
     */
	@GetMapping(value = {"", "/", "/index"})
    public String index(HttpServletRequest request, Model model) {
        return "index";
    }

    /**
     * 用户登录页
     */
    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {
        model.addAttribute("crsfToken", crsfToken());
        model.addAttribute("tokenKey", BizUtils.generateToken());
        return "login";
    }


}
