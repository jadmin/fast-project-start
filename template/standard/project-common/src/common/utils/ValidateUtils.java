/*
 * @(#)ValidateUtils.java	2020-8-18
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.utils;

import static com.github.javaclub.sword.core.Strings.isBlank;

import java.math.BigDecimal;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.domain.enumtype.BasicMessage;
import ${packagePrefix}.common.enums.ErrorEnum;

/**
 * ValidateUtils
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ValidateUtils.java 2020-8-18 15:59:57 Exp $
 */
public class ValidateUtils {

	public static void requireTrue(Boolean flag, ErrorEnum mc) {
        if (null == flag || !flag) {
            throw new BizException(mc.getCode(), mc.getMsg());
        }
    }
	
	public static void requireTrue(Boolean flag, ErrorEnum mc, String message) {
        if (null == flag || !flag) {
            throw new BizException(mc.getCode(), message);
        }
    }
	
	public static void requireNotEmpty(String str, ErrorEnum mc, String message) {
		if(null == str || 0 >= str.trim().length()) {
			 throw new BizException(mc.getCode(), message);
		}
    }
	
	/**
	 * 数值类型非空且大于0
	 */
	public static Number requireNotNullGtZero(Number number, ErrorEnum mc, String message) {
        if (null == number || new BigDecimal(0).compareTo(new BigDecimal(number.toString())) >= 0) {
            throw new BizException(mc.getCode(), isBlank(message) ? number + " : " + BasicMessage.PARAMS_NUMBER_SHOULD_POSITIVE.getMessage() : message);
        }
        return number;
    }
	
}
