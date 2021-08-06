package ${packagePrefix}.service.params;

import java.io.Serializable;

import ${packagePrefix}.common.enums.SecondVerifyType;

import lombok.Data;

@Data
public class LoginUserUpdateParam implements Serializable {
	
    private static final long serialVersionUID = -1449691444497864941L;
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 二次认证类型
     *
     * @see SecondVerifyType
     */
    private Integer secondVerifyType;

    /**
     * 二次认证的密钥
     */
    private String authKey;

    /**
     * 是否开启二次认证
     */
    private Boolean openSecondVerify;
    
}
