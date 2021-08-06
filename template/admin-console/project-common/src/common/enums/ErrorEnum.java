package ${packagePrefix}.common.enums;

import lombok.Getter;

@Getter
public enum ErrorEnum {
	
	
	PARAM_ERROR(101, "参数错误"),
	LOGIN_INVALID(10010006, "请登录后再试！"), 
	NO_LOGIN_GETINFO(10010011, "没有登录"),
	
    SYSTEM_ERROR(1170010001, "系统错误"),
    NOT_FOUND_DATA(1170010002, "数据不存在或已被删除"),
    PARAM_REQUIRE_NOT_EMPTY(1170010003, "参数不允许为空"),
    NOT_OPERATION(1170010004, "不允许操作"),
    PARAM_VALID_ERROR(1170010005, "%s 校验不通过"),
    
    WRONG_ARGS(1170010007, "请求参数错误"),
    INVALID_USER_ID(1170010008, "用户ID参数错误"),
    LOGIN_FAILED(1170010009, "正常的业务规则,导致用户登录受限"),
    REPEAT_BIND_INVITE_CODE(1170010010, "重复绑定邀请码"),
    
    USER_RESET_PASSWORD_TOKEN_INVALID(1170010012, "重置密码验证码失效或错误"),
    USER_SECOND_AUTH_RESET_FAIL(1170010013, "重置二次验证失败"),
    LOGIN_USER_PROFILE_DETAIL_UPDATE_FAIL(1170010014, "用户信息更新失败"),
    OLD_PASSWORD_NOT_CORRECT(1170010015, "旧密码错误"),
    USER_PASSWORD_UPDATE_FAIL(1170010016, "更新用户密码失败"),

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
