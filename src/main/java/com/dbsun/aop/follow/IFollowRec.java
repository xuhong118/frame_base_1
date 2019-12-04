package com.dbsun.aop.follow;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface IFollowRec {

    /**
     * 跟进内容
     * @return
     */
    String followContent() default "";

    /**
     * 跟进类型
     * @return
     */
    String followType() default "";
}
