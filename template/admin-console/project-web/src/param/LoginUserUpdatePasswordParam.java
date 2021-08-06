package ${packagePrefix}.param;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class LoginUserUpdatePasswordParam implements Serializable {
	
    private static final long serialVersionUID = -1449691444497864941L;
    
    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
    
}
