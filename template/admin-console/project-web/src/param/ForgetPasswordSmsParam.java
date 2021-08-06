package ${packagePrefix}.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForgetPasswordSmsParam implements Serializable {
	
    private static final long serialVersionUID = -4691436682236201228L;
    /**
     * 用户名
     */
    private String username;
}
