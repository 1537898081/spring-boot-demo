package com.llf.springboot.common.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @描述：判断参数飞空注解
 * @创建人：朱晓宇
 * @创建日：2019/8/12
 * @param null
 * @方法返回：
 */
@Target({ElementType.PARAMETER})//参数级别
@Retention(RetentionPolicy.RUNTIME) //注解保留到运行阶段
public @interface ParamsNotNull {
}