package com.jingyu.tooltest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author  fengjingyu@foxmail.com
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
public @interface Query {

    String methodName();

    String columns() default "null";

    String where();

    String groupBy() default "null";

    String having() default "null";

    String orderBy() default "null";

    String limit() default "null";
}
