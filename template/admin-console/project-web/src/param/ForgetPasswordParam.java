package ${packagePrefix}.param;

import lombok.Data;

import java.io.Serializable;


@Data
public class ForgetPasswordParam implements Serializable {
	
    private static final long serialVersionUID = -4691436682236201228L;
    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String confirmPassword;

    /**
     * 随机验证码
     */
    private String randVerificationCode;

    /**
     * 用户名
     */
    private String username;
}
