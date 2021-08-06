/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)LoginUserDO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dataobject;

import java.util.Objects;

import com.github.javaclub.sword.core.Numbers;
import com.github.javaclub.sword.domain.BaseDO;

/**
 * LoginUserDO
 *
 * @version $Id: LoginUserDO.java ${currentTime} Exp $
 */
public class LoginUserDO extends BaseDO {

	private static final long serialVersionUID = 1597496453265L;
	
	public static final int SUPER_ADMIN = 9;
	public static final int NORMAL_USER = 0;
	
	public static final String ATTR_OPEN_SECOND_VERIFY = "openSecondVerify";
	public static final String ATTR_SECOND_VERIFY_TYPE = "secondVerifyType";
	
	public static final String ATTR_LOGIN_IP = "loginIp";
	public static final String ATTR_LOGIN_UA = "loginUA";
	
	public static final String ATTR_USER_ROLES = "userRoles";
	
	private Long id;

	private String username;

	private String password;
	
	private String mobile;
	
	private String email;
	
	private String name;
	
	private Integer status;
	
	private String authKey;


	public LoginUserDO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	
	/**
	 * 后台用户登录是否开启了二次验证
	 */
	public boolean isOpenSecondVerify() {
		if(null != getAttributesMap()) {
			String val  = Objects.toString(getAttributesMap().get(ATTR_OPEN_SECOND_VERIFY), "");
			return Objects.equals("true", val);
		}
		return false;
	}
	
	public Integer getSecondVerifyType() {
		if(null != getAttributesMap()) {
			String val  = Objects.toString(getAttributesMap().get(ATTR_SECOND_VERIFY_TYPE), "");
			return Numbers.parseInt(val);
		}
		return null;
	}
	
}

