package ${packagePrefix}.common.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum ResourceType {
    
    PAGE(1, "页面"),
    ACTION(2, "ACTION"),
    OTHER(3, "其他"),

    ;
    
	
    private final Integer value;
    private final String desc;

    ResourceType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static ResourceType of(Integer value) {
        for (ResourceType loginTypeEnum : values()) {
            if (Objects.equals(loginTypeEnum.getValue(), value)) {
                return loginTypeEnum;
            }
        }
        return null;
    }
}
