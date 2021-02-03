/*
 * @(#)WithApiResult.java	2020-12-29
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ${packagePrefix}.common.model.ResultDO;


@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WithApiResult {

  Class<ResultDO> result() default ResultDO.class;
  
}

