package ${packagePrefix}.component.authenticator;

import lombok.Data;

import java.io.Serializable;


@Data
public class AuthenticatorContext implements Serializable {
	
    private static final long serialVersionUID = 8242851254804846095L;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
