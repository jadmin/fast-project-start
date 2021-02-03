package ${packagePrefix}.common.enums;

import lombok.Getter;

@Getter
public enum  EnvEnum {

    DEV("dev", "dev"),
    DAILY("daily", "daily"),
    GRAY("gray", "gray"),
    ONLINE("online", "online"),
    ;

    private String env;
    private String desc;

    EnvEnum(String env, String desc) {
        this.env = env;
        this.desc = desc;
    }

    
}
