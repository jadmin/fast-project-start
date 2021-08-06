/*
 * @(#)LoginTokenModel.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.model;

import java.io.Serializable;
import java.util.Objects;

import com.github.javaclub.sword.algorithm.crypt.MD5;

import lombok.Data;

/**
 * LoginTokenModel
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: LoginTokenModel.java ${currentTime} Exp $
 */
@Data
public class LoginTokenModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	static final String SALT = "$Gh68E#@11WqeKo";

	/**
	 * databox.user 主键ID 
	 */
	private Long wxUserId;
	
	/**
	 * 登录时的时间戳 
	 */
	private Long timestamp;
	
	/**
	 * 签名 
	 */
	private String md5Sign;
	
	public LoginTokenModel() {}
	
	public LoginTokenModel(Long wxUserId) {
		this.wxUserId = wxUserId;
		this.timestamp = System.currentTimeMillis();
	}
	
	public String sortedParams() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("wxUserId:").append(Objects.toString(wxUserId, "null")).append(";");
		sbf.append("timestamp:").append(Objects.toString(timestamp, "null")).append(";");
		return sbf.toString();
	}
	
	/**
	 * 签名校验
	 */
	public boolean validate() {
		String sortedParams = this.sortedParams();
		String value = MD5.getMd5(sortedParams + SALT);
		return Objects.equals(value, md5Sign);
	}
	
	/**
	 * 签名生成
	 */
	public String generateSign() {
		String sortedParams = this.sortedParams();
		return MD5.getMd5(sortedParams + SALT);
	}
	
	public static void main(String[] args) {
		
	}
}
