package ${packagePrefix}.common.enums;

import lombok.Getter;

@Getter
public enum ErrorEnum {
	
	
	PARAM_ERROR(101, "参数错误"),
    SYSTEM_ERROR(1170010001, "系统错误"),
    NOT_FOUND_DATA(1170010002, "数据不存在或已被删除"),
    PARAM_REQUIRE_NOT_EMPTY(1170010003, "参数不允许为空"),
    NOT_OPERATION(1170010004, "不允许操作"),
    PARAM_VALID_ERROR(1170010005, "%s 校验不通过"),
    LOGIN_INVALID(1170010006, "请登录后再试！"), 
    WRONG_ARGS(1170010007, "请求参数错误"),
    INVALID_USER_ID(1170010008, "用户ID参数错误"),
    
    LOGIN_FAILED(1170010009, "正常的业务规则,导致用户登录受限"),
    REPEAT_BIND_INVITE_CODE(1170010010, "重复绑定邀请码"),
    NO_LOGIN_GETINFO(1170010011, "没有登录"),

    ;
	
    int code;
    String msg;
    

	ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
	
	public String getErrorCode() {
		return String.valueOf(code);
	}
	
}
