package ${packagePrefix}.common.enums;

import lombok.Getter;

import java.util.Objects;


@Getter
public enum LoginTypeEnum {
    
    USERNAME_PASSWORD(1, "用户名密码登陆"),
    LDAP(2, "LDAP登陆"),

    ;
    
	
    private final Integer value;
    private final String desc;

    LoginTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static LoginTypeEnum of(Integer value) {
        for (LoginTypeEnum loginTypeEnum : values()) {
            if (Objects.equals(loginTypeEnum.getValue(), value)) {
                return loginTypeEnum;
            }
        }
        return null;
    }
}
