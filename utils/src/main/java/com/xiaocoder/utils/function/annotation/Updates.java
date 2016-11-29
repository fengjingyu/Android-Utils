package com.xiaocoder.utils.function.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @email fengjingyu@foxmail.com
 * @description
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Documented
public @interface Updates {

    Update[] value();

}
