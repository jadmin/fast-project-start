/*
 * @(#)LdapApi.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.support.ldap.api;

import com.github.javaclub.sword.domain.ResultDO;
import ${packagePrefix}.support.ldap.dto.LdapUserDTO;

/**
 * LdapApi
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: LdapApi.java ${currentTime} Exp $
 */
public interface LdapApi {

	/**
     * LDAP登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否登陆成功
     */
    ResultDO<Boolean> login(String username, String password);
     

    /**
     * 获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息
     */
    ResultDO<LdapUserDTO> getUserInfo(String username);
}
