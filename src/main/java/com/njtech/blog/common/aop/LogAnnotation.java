package com.njtech.blog.common.aop;

import java.lang.annotation.*;

// TYPE代表可以放在类上，METHOD放在方法上
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";
    String operator() default "";
}
