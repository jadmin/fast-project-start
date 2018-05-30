/*
 * @(#)PortalPageController.java	2018年4月21日
 *
 * Copyright (c) 2018. All Rights Reserved.
 *
 */

package com.sbt.misc.web.controller.view;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbt.misc.web.controller.BaseController;
import com.sbt.sso.AuthToken;
import com.sbt.sso.SSOConfig;
import com.sbt.sso.SSOHelper;
import com.sbt.sso.SessionUtil;
import com.sbt.sso.Token;
import com.sbt.sso.common.SSOProperties;
import com.sbt.sso.common.util.HttpUtil;
import com.sbt.sword.BizException;
import com.sbt.sword.core.Strings;

/**
 *  Portal页视图
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: PortalPageController.java 2018年4月21日 14:27:33 Exp $
 */
@Controller
public class PortalController extends BaseController {

	static final Logger log = LoggerFactory.getLogger(PortalController.class);
	
	/**
	 * 统一登录认证
	 */
	@RequestMapping(value = "/login.html")
    public String login(Model model) {
		
		try {
			String redirectURL = request.getParameter(SSOConfig.getInstance().getParamReturl());
			if(Strings.isBlank(redirectURL) && SessionUtil.isLogin()) {
				return redirectTo("/user/profile.html");
			}
			// TODO 初始化
			
		} catch (BizException e) {
			model.addAttribute("errorMsg", e.getMessage());
		} catch (Exception e) {
			model.addAttribute("errorMsg", "系统异常，请稍后再试！");
		}
		
        return "/login";
    }
	
	/**
	 * 跨域登录：回复子系统是否登录
	 */
	@ResponseBody
	@RequestMapping("/replylogin")
	public void replylogin() {
		StringBuffer replyData = new StringBuffer();
		replyData.append(request.getParameter("callback")).append("({\"msg\":\"");
		Token token = SSOHelper.getToken(request);
		if (token != null) {
			String askData = request.getParameter("askData");
			if (askData != null && !"".equals(askData)) {
				SSOProperties prop = SSOConfig.getSSOProperties();
				
				//下面开始验证票据，签名新的票据每一步都必须有。
				AuthToken at = SSOHelper.replyCiphertext(request, askData);
				if (at != null) {
					
					//1、业务系统公钥验证签名合法性（此处要支持多个跨域端，取 authToken 的 app 名找到对应系统公钥验证签名）
					at = at.verify(prop.get("sso.defined." + at.getApp() + "_public_key"));
					if (at != null) {
						
						//at.getUuid() 作为 key 设置 authToken 至分布式缓存中，然后 sso 系统二次验证
						//at.setData(data); 设置自定义信息，当然你也可以直接 at.setData(token.jsonToken()); 把当前 SSOToken 传过去。
						
						at.setUid(token.getUid());//设置绑定用户ID
						at.setTime(token.getTime());//设置登录时间
						
						//2、SSO 的私钥签名
						at.sign(prop.get("sso.defined.sso_private_key"));
						
						//3、生成回复密文票据
						replyData.append(at.encryptAuthToken());
					} else {
						//非法签名, 可以重定向至无权限界面，自己处理
						replyData.append("-2");
					}
				} else {
					//非法签名, 可以重定向至无权限界面，自己处理
					replyData.append("-2");
				}
			}
		} else {
			// 未登录
			replyData.append("-1");
		}
		try {
			replyData.append("\"})"); // 已经是json了
			HttpUtil.response(response, replyData.toString(), false, null, "UTF-8");
		} catch (IOException e) {
			log.error("跨域登录 => replylogin ", e);
		}
	}
	
	/**
	 * 测试freemarker页面
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
    public String freemarkerTemplate(String name, String msg, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("message", msg);
        log.warn("testPage, queryString={}, uri={}, url={}", 
        				request.getQueryString(), 
        				request.getRequestURI(), 
        				request.getRequestURL());
        return "/test";
    }
	
	
}
