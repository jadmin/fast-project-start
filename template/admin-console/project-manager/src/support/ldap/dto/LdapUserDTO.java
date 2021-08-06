package ${packagePrefix}.support.ldap.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class LdapUserDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 名称
     */
    private String name;
}
