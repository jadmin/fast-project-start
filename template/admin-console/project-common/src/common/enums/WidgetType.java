package ${packagePrefix}.common.enums;

import lombok.Getter;

import java.util.Objects;


@Getter
public enum WidgetType {
    
    DEV_TOOL(1, "基础开发工具"),
    BIZ_TOOL(2, "业务工具"),

    ;
	
    private final Integer value;
    private final String desc;

    WidgetType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static WidgetType of(Integer value) {
        for (WidgetType loginTypeEnum : values()) {
            if (Objects.equals(loginTypeEnum.getValue(), value)) {
                return loginTypeEnum;
            }
        }
        return null;
    }
}
