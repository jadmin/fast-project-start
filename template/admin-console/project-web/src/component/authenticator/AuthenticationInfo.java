package ${packagePrefix}.component.authenticator;

import java.io.Serializable;

import ${packagePrefix}.view.AdminUserView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthenticationInfo extends AdminUserView implements Serializable {
	
    private static final long serialVersionUID = 606833639848463792L;

    /**
     * 是否开启谷歌身份认证
     */
    private Boolean openSecondVerify;

    /**
     * 二次认证类型
     */
    private Integer secondVerifyType;
}
