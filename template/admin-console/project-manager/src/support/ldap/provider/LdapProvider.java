/*
 * @(#)LdapProvider.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.support.ldap.provider;

import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;

import com.github.javaclub.sword.domain.ResultDO;
import com.google.common.base.Strings;
import ${packagePrefix}.support.ldap.api.LdapApi;
import ${packagePrefix}.support.ldap.dto.LdapUserDTO;
import ${packagePrefix}.support.ldap.enums.LdapErrorEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * LdapProvider
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: LdapProvider.java ${currentTime} Exp $
 */
@Slf4j
public class LdapProvider implements LdapApi {

	private final LdapProperties ldapProperties;
    private final LdapTemplate ldapTemplate;

    public LdapProvider(LdapTemplate ldapTemplate,
                        LdapProperties ldapProperties) {
        this.ldapProperties = ldapProperties;
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public ResultDO<Boolean> login(String username, String password) {
        try {
            AndFilter filter = new AndFilter();
            filter.and(new EqualsFilter("objectClass", ldapProperties.getBaseEnvironment().get("userObjectClass")))
                    .and(new EqualsFilter(ldapProperties.getBaseEnvironment().get("userId"), username));
            return ResultDO.success(ldapTemplate.authenticate(ldapProperties.getBaseEnvironment().get("userBaseDN"),
                    filter.toString(), password));
        } catch (Exception e) {
            log.error("Failed to login of ldap by username: {}, password: {}, cause: {}", username, password, e);
            return ResultDO.failure(LdapErrorEnum.LDAP_LOGIN_FAIL.getMsg(), LdapErrorEnum.LDAP_LOGIN_FAIL.getCode());
        }
    }

    @Override
    public ResultDO<LdapUserDTO> getUserInfo(String username) {
        try {
            LdapQuery query = LdapQueryBuilder.query().base(ldapProperties.getBaseEnvironment().get("userBaseDN"))
                    .where(ldapProperties.getBaseEnvironment().get("userId")).is(username)
                    .and("objectClass").is(ldapProperties.getBaseEnvironment().get("userObjectClass"));
            LdapUserDTO ldapUserDTO = ldapTemplate.searchForObject(query, new AbstractContextMapper<LdapUserDTO>() {
                @Override
                protected LdapUserDTO doMapFromContext(DirContextOperations ctx) {
                    LdapUserDTO ldapUserDTO = new LdapUserDTO();
                    String mail = (String) ctx.getObjectAttribute("mail");
                    String displayName = (String) ctx.getObjectAttribute("displayname");
                    String mobile = (String) ctx.getObjectAttribute("mobile");
                    ldapUserDTO.setEmail(mail);
                    ldapUserDTO.setMobile(mobile);
                    if (Strings.isNullOrEmpty(displayName)) {
                        ldapUserDTO.setName(username);
                    } else {
                        ldapUserDTO.setName(displayName);
                    }
                    ldapUserDTO.setUsername(username);
                    return ldapUserDTO;
                }
            });
            return ResultDO.success(ldapUserDTO);
        } catch (Exception e) {
            log.error("Failed to find ldap user info by username: {}, cause: {}", username, e);
            return ResultDO.failure(LdapErrorEnum.LDAP_USER_FIND_FAIL.getMsg(), LdapErrorEnum.LDAP_USER_FIND_FAIL.getCode());
        }
    }

}
