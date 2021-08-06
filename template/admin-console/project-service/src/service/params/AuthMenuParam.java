/*
 * @(#)AuthMenuParam.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.params;

import java.util.List;

import lombok.Data;

/**
 * AuthMenuParam
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: AuthMenuParam.java ${currentTime} Exp $
 */
@Data
public class AuthMenuParam {

	private Long menuId;
	private Integer menuType;
	
	private List<Long> bindResources;

}
