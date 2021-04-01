/*
 * @(#)OpenApiAuth.java	2020-04-01
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenApiAuth {

  
}

