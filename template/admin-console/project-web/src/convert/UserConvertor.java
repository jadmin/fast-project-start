/*
 * @(#)UserConvertor.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.convert;

import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ${packagePrefix}.component.authenticator.AuthenticationInfo;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.support.ldap.dto.LdapUserDTO;
import ${packagePrefix}.view.AdminUserView;
import ${packagePrefix}.view.ProfileDetailVO;

/**
 * UserConvertor
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: UserConvertor.java ${currentTime} Exp $
 */
@Mapper
public interface UserConvertor {

	UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

	@Mappings({ 
		@Mapping(target = "userId", source = "id"), 
		@Mapping(target = "username", source = "username"),
		@Mapping(target = "name", source = "name"), 
		@Mapping(target = "email", source = "email"),
		@Mapping(target = "mobile", source = "mobile"), 
		@Mapping(target = "status", source = "status") 
	})
	AdminUserView from(LoginUserDO loginUser);

	@Mappings({ 
		@Mapping(source = "id", target = "userId"), 
	})
	AuthenticationInfo convertToAuthenticationInfo(LoginUserDO loginUser);

	@Mappings({ 
		@Mapping(source = "userId", target = "id"), 
	})
	LoginUserDO convertToLoginUserDO(AuthenticationInfo authenticationInfo);
	
	LoginUserDO convertToLoginUserDO(LdapUserDTO ldapUserDTO);

	default AdminUserView build(LoginUserDO loginUser) {
		AdminUserView view = from(loginUser);
		if (null != loginUser.getAttributesMap()) {
			view.setLoginIp(Objects.toString(loginUser.getAttributesMap().get(LoginUserDO.ATTR_LOGIN_IP), null));
			view.setLoginUA(Objects.toString(loginUser.getAttributesMap().get(LoginUserDO.ATTR_LOGIN_UA), null));
		}
		return view;
	}
	
	ProfileDetailVO convertToProfileDetailVO(LoginUserDO loginUser);
}
