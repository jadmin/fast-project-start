/*
 * @(#)RoleUserParam.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.params;

import java.util.Date;

import com.github.javaclub.sword.spring.BeanCopierUtils;
import com.github.javaclub.sword.util.DateUtil;

import ${packagePrefix}.dataobject.UserRoleDO;

/**
 * RoleUserParam
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RoleUserParam.java ${currentTime} Exp $
 */
public class RoleUserParam extends UserRoleDO {
	
	private static final long serialVersionUID = 1L;

	public RoleUserParam() {
	}
	
	public RoleUserParam(UserRoleDO urd) {
		BeanCopierUtils.copyProperties(urd, this);
	}
	
	private Long expireType;

	public Long getExpireType() {
		return expireType;
	}

	public void setExpireType(Long expireType) {
		this.expireType = expireType;
	}
	
	public void doReinitExpiredTime() {
		int type = ( null == expireType ? 9 : expireType.intValue() );
		Date bassTime = (null == getExpiredTime() ? new Date() : getExpiredTime());
		switch (type) {
			case 9:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 100*365));
				break;
			case 1:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 1));		
				break;
			case 2:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 3));	
				break;
			case 3:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 15));	
				break;
			case 4:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 90));	
				break;
			case 5:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 180));	
				break;
			case 6:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 365));	
				break;
			case 7:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 3*365));	
				break;
			case 8:
				// 使用前台提交的时间
				break;
			default:
				this.setExpiredTime(DateUtil.plusDay(bassTime, 100*365));
				break;
		}
	}
	
	public UserRoleDO toUserRoleDO() {
		UserRoleDO urd = new UserRoleDO();
		BeanCopierUtils.copyProperties(this, urd);
		return urd;
	}

}
