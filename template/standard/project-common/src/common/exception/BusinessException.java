package ${packagePrefix}.common.exception;

import ${packagePrefix}.common.enums.ErrorEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String responseCode;

    public BusinessException(String responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public BusinessException(ErrorEnum errorEnum) {
        this(String.valueOf(errorEnum.getErrorCode()), errorEnum.getMsg());
    }
    
    public BusinessException(String message, Throwable cause) {
    	super(message, cause);
    }

}
