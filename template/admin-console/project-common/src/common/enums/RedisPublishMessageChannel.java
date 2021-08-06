package ${packagePrefix}.common.enums;

import lombok.Getter;

@Getter
public enum  RedisPublishMessageChannel {

	BLACK_WHITE_NAME("BLACK_WHITE_NAME", "黑白名单消息通道"),
    
    ;

    private String code;
    private String desc;

    RedisPublishMessageChannel(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    
}

