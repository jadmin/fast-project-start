package ${packagePrefix}.param.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class ForgetPasswordSmsDTO implements Serializable {
	
    private static final long serialVersionUID = 4649014487062670228L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;


    /**
     * 随机验证码
     */
    private String randVerificationCode;
}
