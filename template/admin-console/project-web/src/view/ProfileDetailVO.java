package ${packagePrefix}.view;

import lombok.Data;

import java.io.Serializable;


@Data
public class ProfileDetailVO implements Serializable {
	
    private static final long serialVersionUID = -2027555309937344920L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮件
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 是否开启谷歌身份认证
     */
    private Boolean openSecondVerify;

    /**
     * 二次认证类型
     */
    private Integer secondVerifyType;

    /**
     * 二次认证密钥
     */
    private String authKey;

    /**
     * 谷歌二次身份认证账号二维码(可登陆)
     */
    private String qrcodeUrl;
    
}
