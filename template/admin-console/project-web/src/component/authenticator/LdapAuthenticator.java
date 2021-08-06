package ${packagePrefix}.component.authenticator;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.domain.ResultDO;
import ${packagePrefix}.common.enums.AdminUserStatus;
import ${packagePrefix}.common.exception.BusinessException;
import ${packagePrefix}.convert.UserConvertor;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.service.LoginUserService;
import ${packagePrefix}.service.utils.BizUtils;
import ${packagePrefix}.support.ldap.api.LdapApi;
import ${packagePrefix}.support.ldap.dto.LdapUserDTO;

@Component
public class LdapAuthenticator {
	
	@Autowired
    private LdapApi ldapApi;
    
    @Autowired
    private LoginUserService loginUserService;

    public AuthenticationInfo authenticate(AuthenticatorContext authenticatorContext) {
        // 验证LDAP用户名密码
        ResultDO<Boolean> resp = ldapApi.login(authenticatorContext.getUsername(), authenticatorContext.getPassword());
        if (!resp.isSuccess()) {
            throw new BusinessException(String.valueOf(resp.getCode()), resp.getMessage());
        }
        if (Objects.equals(resp.getEntry(), Boolean.FALSE)) {
            return null;
        }
        // 根据用户名查询是否注册
        LoginUserDO loginUserDO = loginUserService.getByUsername(authenticatorContext.getUsername());
        if (null == loginUserDO) {
            // 静默注册
        	ResultDO<LdapUserDTO> userInfoResp = ldapApi.getUserInfo(authenticatorContext.getUsername());
            if (!userInfoResp.isSuccess()) {
                throw new BusinessException(String.valueOf(userInfoResp.getCode()), userInfoResp.getMessage());
            }
            loginUserDO = UserConvertor.INSTANCE.convertToLoginUserDO(userInfoResp.getEntry());
            loginUserDO.setPassword(BizUtils.generatePasswordMD5(authenticatorContext.getPassword()));
            loginUserDO.setStatus(AdminUserStatus.NO_ACTIVATED.getValue());
            loginUserService.create(loginUserDO);
            loginUserDO.setId(loginUserDO.getId());
        }
        return UserConvertor.INSTANCE.convertToAuthenticationInfo(loginUserDO);
    }
}
